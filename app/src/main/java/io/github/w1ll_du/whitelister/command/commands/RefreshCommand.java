package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.Utils;
import io.github.w1ll_du.whitelister.command.ARconCommand;
import io.github.w1ll_du.whitelister.command.CommandContext;

import java.util.ArrayList;

public class RefreshCommand extends ARconCommand {
    @Override
    protected void handle2(CommandContext ctx) {
        ArrayList<String> commands = new ArrayList<>();
        for (String name : ctx.getPlayerMap().values()) {
            commands.add("whitelist add " + name);
        }
        commands.add("whitelist reload");
        Utils.rconCommands(commands);
    }

    @Override
    public String getName() {
        return "refresh";
    }
}
