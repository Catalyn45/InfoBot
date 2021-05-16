package bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {
    private final String token;
    private JDA jda;

    private static Bot instance = null;

    private Bot(String token) {
        this.token = token;
    }

    public static Bot getInstance(String token) {
        if(instance == null)
            instance = new Bot(token);

        return instance;
    }

    public static Bot getInstance() {
        return instance;
    }

    public void run() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(new NewsEvent());
        jda.addEventListener(new AskEvent());
        jda.addEventListener(new ImagesEvent());
        jda.addEventListener(new HelpEvent());
        jda.awaitReady();
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
