package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.command.AAdminCommand;
import io.github.w1ll_du.whitelister.command.CommandContext;

public class GetConfigCommand extends AAdminCommand {
    @Override
    protected void handle2(CommandContext ctx) {
        ctx.getChannel().sendMessage(ctx.getConf().toString()).queue();
    }

    @Override
    public String getName() {
        return "getConfig";
    }
}
