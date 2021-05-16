package bot;

import com.rometools.rome.feed.synd.SyndEntry;
import database.entities.LinksEntity;
import database.entities.TopicsEntity;
import database.jpaRepositories.JPAFactory;
import database.repositories.LinksRepository;
import database.repositories.RepositoryFactory;
import database.repositories.TopicsRepository;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import rss.FeedReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class NewsCommand implements Runnable {
    GuildMessageReceivedEvent event;

    public NewsCommand(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    @Override
    public void run() {
        String message = event.getMessage().getContentRaw().toLowerCase(Locale.ROOT);

        String text = "";

        if (!message.startsWith("!get_news "))
            return;

        RepositoryFactory factory = JPAFactory.getInstance();

        Map<String, String> args = NewsEvent.parseParameters(message);

        message = args.get("__newString__");

        if(args.containsKey("topics")) {
            TopicsRepository topicsRepository = factory.createTopicsRepository();
            List<TopicsEntity> topics = topicsRepository.all();

            String names = "";

            for(TopicsEntity topic : topics) {
                names += topic.getName();
                names += "\n";
            }

            event.getChannel().sendMessage(names).queue();
        }

        int pageSize = 4;
        int pageNr = 0;

        if(args.containsKey("page")) {
            pageNr = Integer.parseInt(args.get("page")) - 1;
        }

        String pattern = "^!get_news\\s+(\\w+)$";

        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(message);

        if (!matcher.find())
            return;

        String topic = matcher.group(1);

        LinksRepository repository = factory.createLinksRepository();

        List<LinksEntity> links = repository.findByTopic(topic);

        if (links == null || links.isEmpty()) {
            text = "We do not have news about `" + topic + "`, try `!get_news --topics` to see the available topics.";
        } else {
            List<SyndEntry> entries = new FeedReader().run(links.get(0).getUrl());

            for (int i = pageNr * pageSize ; (i < (pageNr+1)*pageSize) && (i < entries.size()); i++) {
                text += entries.get(i).getTitle() + "\n" + entries.get(i).getLink() + "\n\n";
            }

            if(text.isEmpty()) {
                text = "Nothing to show!";
            }
        }

        event.getChannel().sendMessage(text).queue();
    }
}

public class NewsEvent extends ListenerAdapter {
    public static Map<String, String> parseParameters(String commandLine) {
        Map<String, Integer> commands = new HashMap<String, Integer>(){
            {
                put("topics", 0);
                put("page", 1);
            }
        };

        Map<String, String> args = new HashMap<>();

        for(String command : commands.keySet()) {
            String regex = "\\s*--" + command;

            for(int i = 0; i < commands.get(command); i++)
                regex += "\\s+(\\w+)";

            Pattern p = Pattern.compile(regex);
            Matcher matcher = p.matcher(commandLine);


            if(matcher.find()) {
                System.out.println(matcher.groupCount());
                String arg = "";
                for(int i = 1; i <= matcher.groupCount(); i++) {
                    if (i > 2)
                        arg += ",";

                    arg += matcher.group(i);
                }

                args.put(command, arg);
                commandLine = commandLine.replace(matcher.group(0), "");
            }
        }

        args.put("__newString__", commandLine);
        return args;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        Thread thread = new Thread(new NewsCommand(event));
        thread.start();
    }
}
