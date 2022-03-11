package io.github.w1ll_du.whitelister.command.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.w1ll_du.whitelister.command.ARconCommand;
import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.Utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class UnlinkCommand extends ARconCommand {
    @Override
    protected void handle2(CommandContext ctx) {
        if (! ctx.getPlayerMap().containsKey(ctx.getAuthor().getId())) {
            ctx.getChannel().sendMessage("There is no linked account").queue();
            return;
        }
        Utils.rconCommand("whitelist remove " + ctx.getPlayerMap().get(ctx.getAuthor().getId()));
        ctx.getChannel().sendMessage("Unlinked with " + ctx.getPlayerMap().get(ctx.getAuthor().getId())).queue();
        ctx.getPlayerMap().remove(ctx.getAuthor().getId());
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get("playerMap.json").toFile(), ctx.getPlayerMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.rconCommand("whitelist reload");
        ctx.getMember().modifyNickname(null).queue();
        ctx.getGuild().removeRoleFromMember(ctx.getAuthor().getId(),
                Objects.requireNonNull(
                        ctx.getGuild().getRoleById(ctx.getConf().get("whitelist_role_id")))).queue();
    }

    @Override
    public String getName() {
        return "unlink";
    }
}
