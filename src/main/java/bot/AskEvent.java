package bot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import secrets.Tokens;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AskCommand implements Runnable {
    GuildMessageReceivedEvent event;
    static final String apiKey = Tokens.WOLFRAM_KEY;

    public AskCommand(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    @Override
    public void run() {
        String message = event.getMessage().getContentRaw();

        if (!message.startsWith("!ask "))
            return;

        String pattern = "^!ask\\s+(.+)\\s*$";

        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(message);

        if (!matcher.find())
            return;

        event.getChannel().sendMessage("Let me think, hmmm...").queue();
        URL url;
        try {
            url = new URL("http://api.wolframalpha.com/v2/query?input=" +
                    URLEncoder.encode(matcher.group(1), StandardCharsets.UTF_8.toString()) +
                    "&appid=" + apiKey
            );

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(con.getInputStream());

            Element root = doc.getDocumentElement();

            NodeList list = root.getElementsByTagName("pod");

            String text = "";
            int length = list.getLength();
            for(int i = 0; i < length; i++) {
                Element pod = (Element) list.item(i);
                if(pod.getAttribute("title").toLowerCase(Locale.ROOT).equals("input interpretation"))
                    continue;

                Element subpod = (Element)pod.getElementsByTagName("subpod").item(0);
                text += subpod.getElementsByTagName("plaintext").item(0).getTextContent();
                text += "\n\n";
            }

            con.disconnect();

            if(length - 1 > 0)
                event.getChannel().sendMessage(text).queue();
            else
                event.getChannel().sendMessage("idk...").queue();

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}

public class AskEvent extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        Thread thread = new Thread(new AskCommand(event));
        thread.start();
    }
}
