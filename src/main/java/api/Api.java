package api;

import bot.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import secrets.Tokens;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class Api {
    public static void main(String[] args) throws LoginException, InterruptedException {
        SpringApplication.run(Api.class, args);
        Bot bot = Bot.getInstance(Tokens.DISCORD_TOKEN);
        bot.run();
    }
}
