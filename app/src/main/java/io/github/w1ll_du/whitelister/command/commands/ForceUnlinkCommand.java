package io.github.w1ll_du.whitelister.command.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.w1ll_du.whitelister.Utils;
import io.github.w1ll_du.whitelister.command.AAdminCommand;
import io.github.w1ll_du.whitelister.command.CommandContext;
import net.dv8tion.jda.api.entities.Member;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

public class ForceUnlinkCommand extends AAdminCommand {
    @Override
    protected void handle2(CommandContext ctx) {
        String username = ctx.getArgs().get(0);
        if (! ctx.getPlayerMap().inverseBidiMap().containsKey(username)) {
            ctx.getChannel().sendMessage("There is no linked account").queue();
            return;
        }
        Utils.rconCommand("whitelist remove " + username);
        ctx.getChannel().sendMessage("Unlinked with " + username).queue();
        Utils.rconCommand("whitelist reload");
        ctx.getGuild().removeRoleFromMember(ctx.getPlayerMap().inverseBidiMap().get(username),
                ctx.getGuild().getRoleById(ctx.getConf().get("whitelist_role_id"))).queue();
        ctx.getChannel().sendMessage("Remember to reset the nickname.").queue();
        ctx.getGuild().getMembersByNickname(username, false).forEach(new Consumer<Member>() {
            @Override
            public void accept(Member member) {
                member.modifyNickname(null).queue();
            }
        });
        ctx.getPlayerMap().inverseBidiMap().remove(username);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get("playerMap.json").toFile(), ctx.getPlayerMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "forceUnlink";
    }

    @Override
    public List<String> getAliases() {
        return List.of("fUnlink", "funlink");
    }
}
