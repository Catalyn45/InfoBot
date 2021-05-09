package bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {
    private final String token;
    private JDA jda;

    public Bot(String token) {
        this.token = token;
    }

    public void run() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(new NewEvent());
        // jda.awaitReady();
    }
}
