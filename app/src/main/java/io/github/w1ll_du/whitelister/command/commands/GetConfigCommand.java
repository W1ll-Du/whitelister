package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.command.ICommand;

public class GetConfigCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        ctx.getChannel().sendMessage(ctx.getConf().toString()).queue();
    }

    @Override
    public String getName() {
        return "getConfig";
    }
}
