package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.command.ICommand;
import io.github.w1ll_du.whitelister.Utils;

public class WhitelistCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        ctx.getChannel().sendMessage(Utils.rconCommand("/whitelist list")).queue();
    }

    @Override
    public String getName() {
        return "whitelist";
    }
}
