package io.github.w1ll_du.whitelister.command.commands;

import io.github.w1ll_du.whitelister.command.AAdminCommand;
import io.github.w1ll_du.whitelister.command.CommandContext;

public class SoftbanCommand extends AAdminCommand {
    @Override
    protected void handle2(CommandContext ctx) {
        new ForceUnlinkCommand().handle2(ctx);
        ctx.getGuild()
                .addRoleToMember(ctx.getPlayerMap().inverseBidiMap().get(ctx.getArgs().get(0)),
                        ctx.getGuild().getRoleById(ctx.getConf().get("banned_role_id"))).queue();
    }

    @Override
    public String getName() {
        return "softban";
    }
}
