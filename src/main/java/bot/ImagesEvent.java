package bot;
import com.google.gson.Gson;
import javafx.util.Pair;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class ImagesCommand implements Runnable {
    GuildMessageReceivedEvent event;

    public ImagesCommand(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    private static final Map<String, Pair<String, String>> images = new HashMap<String, Pair<String, String>>() {{
        put("dog", new Pair<>("https://dog.ceo/api/breeds/image/random", "message"));
        put("anime", new Pair<>("https://api.waifu.pics/sfw/waifu", "url"));
        put("fox", new Pair<>("https://randomfox.ca/floof", "image"));
        put("cat", new Pair<>("http://aws.random.cat//meow", "file"));
    }};

    @Override
    public void run() {
        String message = event.getMessage().getContentRaw().toLowerCase(Locale.ROOT);

        if (!message.startsWith("!image "))
            return;

        String pattern = "^!image\\s+([a-zA-Z0-9-]+)\\s*$";

        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(message);

        if (!matcher.find())
            return;

        String key = matcher.group(1);

        if(key.equals("--types")) {
            String text = "Supported image types:\n";

            for(String name : images.keySet()) {
                text += "- " + name + "\n";
            }

            event.getChannel().sendMessage(text).queue();
            return;
        }

        if(!images.containsKey(key)) {
            event.getChannel().sendMessage("Can't find what are u looking for... try `!image --types` to see" +
                                            "the available images!").queue();
            return;
        }

        try {
            URL url = new URL(images.get(key).getKey());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:12.0) Gecko/20100101 Firefox/12.0");
            InputStream in = connection.getInputStream();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in));

            String json = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
            in.close();

            Map<String, Object> response = new HashMap<>();

            Gson gson = new Gson();

            response = (Map<String, Object>)gson.fromJson(json, response.getClass());

            String field = images.get(key).getValue();

            if (!response.containsKey(field)) {
                event.getChannel().sendMessage("Can't get the image...").queue();
                return;
            }
            url = new URL((String) response.get(field));

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:12.0) Gecko/20100101 Firefox/12.0");
            String format = FilenameUtils.getExtension((String)response.get(field));
            event.getChannel().sendFile(connection.getInputStream(), "image." + format).queue();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class ImagesEvent extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        Thread thread = new Thread(new ImagesCommand(event));
        thread.start();
    }
}
