package io.github.w1ll_du.whitelister.command.commands;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.command.ICommand;
import io.github.w1ll_du.whitelister.utils.Utils;

import java.io.IOException;
import java.nio.file.Paths;

public class UnlinkCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
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
                ctx.getGuild().getRoleById(ctx.getConf().get("whitelist_role_id"))).queue();
    }

    @Override
    public String getName() {
        return "unlink";
    }
}
