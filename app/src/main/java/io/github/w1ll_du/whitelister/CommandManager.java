package io.github.w1ll_du.whitelister;

import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.command.ICommand;
import io.github.w1ll_du.whitelister.command.commands.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.collections4.BidiMap;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        //addCommand(new PingCommand());
        addCommand(new WhitelistCommand());
        addCommand(new LinkCommand());
        addCommand(new UnlinkCommand());
        addCommand(new RelinkCommand());
        addCommand(new RefreshCommand());
        addCommand(new ForceUnlinkCommand());
        //addCommand(new GetConfigCommand()); //(debug)
        //addCommand(new SetConfigCommand());
        //addCommand(new GetPlayerMapCommand()); // (debug)
    }

    @Nullable
    private ICommand getCommand(String name) {
        for (ICommand cmd : commands) {
            if (cmd.getName().equals(name) || cmd.getAliases().contains(name)) {
                return cmd;
            }
        }
        return null;
    }

    private void addCommand(ICommand newCmd) {
        if (commands.stream().anyMatch(cmd -> cmd.getName().equals(newCmd.getName()))) {
            throw new IllegalArgumentException("A command with this name already exists");
        } else {
            commands.add(newCmd);
        }
    }

    void handle(GuildMessageReceivedEvent event, String prefix, BidiMap<String, String> playerMap, Map<String, String> conf) throws IOException {

        String[] split = event.getMessage().getContentRaw()
            .replaceFirst("(?i)" + Pattern.quote(prefix), "")
            .split("\\s+");

        String name = split[0];
        ICommand cmd = getCommand(name);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args, playerMap, conf);

            cmd.handle(ctx);
        }
    }
}
