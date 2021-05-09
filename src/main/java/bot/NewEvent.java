package bot;

import com.rometools.rome.feed.synd.SyndEntry;
import database.entities.LinksEntity;
import database.jpaRepositories.JPAFactory;
import database.repositories.LinksRepository;
import database.repositories.RepositoryFactory;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import rss.FeedReader;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewEvent extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        String message = event.getMessage().getContentRaw();

        String text = "";

        if(message.toLowerCase(Locale.ROOT).startsWith("!get_news")) {

            String pattern = "^!get_news (\\w+)$";

            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(message);

            if(matcher.find()) {
                String topic = matcher.group(1);

                RepositoryFactory factory = JPAFactory.getInstance();
                LinksRepository repository = factory.createLinksRepository();

                List<LinksEntity> links = repository.findByTopic(topic);

                if(links == null || links.isEmpty()) {
                    text = "Ne pare rau, dar nu avem stiri despre " + topic;
                } else {
                    List<SyndEntry> entries = new FeedReader().run(links.get(0).getUrl());

                    for (int i = 0; (i < 4) && (i < entries.size()) ; i++) {
                        text += entries.get(i).getTitle() + "\n" + entries.get(i).getLink() + "\n\n\n";
                    }
                }

                event.getChannel().sendMessage(text).queue();
            }
        }
    }
}
