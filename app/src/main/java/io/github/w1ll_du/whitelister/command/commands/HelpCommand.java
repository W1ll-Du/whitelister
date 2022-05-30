package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.command.ICommand;

public class HelpCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        ctx.getChannel().sendMessage("https://github.com/W1ll-Du/whitelister/blob/master/README.md").queue();
    }

    @Override
    public String getName() {
        return "help";
    }
}
