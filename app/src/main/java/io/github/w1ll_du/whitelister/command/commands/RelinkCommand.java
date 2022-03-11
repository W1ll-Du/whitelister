package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.Utils;
import io.github.w1ll_du.whitelister.command.ARconCommand;
import io.github.w1ll_du.whitelister.command.CommandContext;

public class RelinkCommand extends ARconCommand {
    @Override
    protected void handle2(CommandContext ctx) {
        if (ctx.getPlayerMap().containsValue(ctx.getMember().getEffectiveName())) {
            Utils.rconCommand("whitelist add " + ctx.getMember().getEffectiveName());
            Utils.rconCommand("whitelist reload");
            ctx.getChannel().sendMessage("Relinked account").queue();
        } else {
            ctx.getChannel().sendMessage("You were never linked to begin with.").queue();
        }
    }

    @Override
    public String getName() {
        return "relink";
    }
}
