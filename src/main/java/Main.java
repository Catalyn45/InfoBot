import bot.Bot;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {
        String token = "ODMzMjgxMzY1MzAyNTc1MTQ0.YHwDvw.3fdshzSCPfkZUEYqDtl-4GmVe5Y";
        Bot bot = new Bot(token);
        bot.run();
    }
}