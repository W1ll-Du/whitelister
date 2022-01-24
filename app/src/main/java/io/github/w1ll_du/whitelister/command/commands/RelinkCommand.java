package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.Utils;
import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.command.ICommand;

public class RelinkCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getPlayerMap().containsValue(ctx.getMember().getEffectiveName())) {
            Utils.rconCommand("whitelist add " + ctx.getMember().getEffectiveName());
            Utils.rconCommand("whitelist reload");
        }
    }

    @Override
    public String getName() {
        return "relink";
    }
}
