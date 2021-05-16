package api.controllers;

import bot.Bot;
import org.springframework.web.bind.annotation.*;

class Payload {
    private String server;
    private String channel;
    private String message;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

@RestController
public class ActionsController {
    @PostMapping("/message")
    public String sendMessage(@RequestBody Payload payload) {
        String server = payload.getServer();
        String channel = payload.getChannel();
        String message = payload.getMessage();

        Bot bot = Bot.getInstance();
        bot.sendMessage(server, channel, message);

        return "success";
    }
}
