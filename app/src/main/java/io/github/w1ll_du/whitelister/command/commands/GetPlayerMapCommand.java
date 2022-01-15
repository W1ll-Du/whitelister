package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.command.ICommand;

public class GetPlayerMapCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        ctx.getChannel().sendMessage(ctx.getPlayerMap().toString()).queue();
    }

    @Override
    public String getName() {
        return "getPlayerMap";
    }
}
