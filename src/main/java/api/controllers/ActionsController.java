package api.controllers;

import bot.Bot;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AdminController {
    @PostMapping("/message")
    public String sendMessage(@RequestBody Map<String, String> payload) {
        String server = payload.get("server");
        String channel = payload.get("channel");
        String message = payload.get("message");
        
        Bot bot = Bot.getInstance();
        bot.sendMessage(server, channel, message);
        return "success";
    }

    @GetMapping("/")
    public String index() {
        return "success";
    }
}
