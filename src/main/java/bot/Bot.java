package bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {
    private final JDA jda;

    private static Bot instance = null;

    private Bot(String token) throws LoginException {
        jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(new NewsEvent());
        jda.addEventListener(new AskEvent());
        jda.addEventListener(new ImagesEvent());
        jda.addEventListener(new HelpEvent());
    }

    public static Bot getInstance(String token) throws LoginException {
        if(instance == null) {
            instance = new Bot(token);

            try {
                instance.jda.awaitReady();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static Bot getInstance() {
        if(instance != null) {
            try {
                instance.jda.awaitReady();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public void sendMessage(String server, String channel, String message) {
        jda.getGuildsByName(server, true)
                .get(0)
                .getTextChannelsByName(channel, true)
                .get(0)
                .sendMessage(message)
                .queue();
    }
}
