package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.Utils;
import io.github.w1ll_du.whitelister.command.AAdminCommand;
import io.github.w1ll_du.whitelister.command.CommandContext;

public class RconCommand extends AAdminCommand  {
    protected void handle2(CommandContext ctx) {
        ctx.getChannel().sendMessage(Utils.rconCommand(Utils.compress(ctx.getArgs()))).queue();
    }

    public String getName() {
        return "rcon";
    }
}
