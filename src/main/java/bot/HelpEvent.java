package bot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class HelpEvent extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        String message = event.getMessage().getContentRaw().toLowerCase(Locale.ROOT);

        if (!message.startsWith("!help persephone"))
            return;

        String helpMessage = "The available commands are:\n" +
                "```" +
                "!help\n" +
                "\tinfo: Shows all the available commands\n" +
                "\n" +
                "!ask\n" +
                "\tinfo: Shows informations about any topics\n" +
                "\tparameters:\n" +
                "\t\tThe phrase to be searched\n" +
                "\texample: !ask who is the richest person?\n" +
                "\n" +
                "!get_news\n" +
                "\tinfo: Shows the most recent news of a specific topic\n" +
                "\tparameters:\n" +
                "\t\tThe topic\n" +
                "\t\t--topics (to see all the available topics)\n" +
                "\t\t--page (to specify the page of news to see, default: 1)\n" +
                "\texample: !get_news sport --page 2\n" +
                "\n" +
                "!image\n" +
                "\tinfo: Shows a random image about a specific topic\n" +
                "\tparameters:\n" +
                "\t\tThe topic of the image\n" +
                "\t\t--types (to see all the topics)\n" +
                "\texample: !image fox" +
                "```";

        event.getChannel().sendMessage(helpMessage).queue();
    }
}
