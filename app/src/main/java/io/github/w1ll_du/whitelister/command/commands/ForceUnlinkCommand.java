package io.github.w1ll_du.whitelister.command.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.w1ll_du.whitelister.Utils;
import io.github.w1ll_du.whitelister.command.AAdminCommand;
import io.github.w1ll_du.whitelister.command.CommandContext;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

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
        String id = ctx.getPlayerMap().inverseBidiMap().get(username);
        ctx.getPlayerMap().inverseBidiMap().remove(username);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get("playerMap.json").toFile(), ctx.getPlayerMap());
        } catch (IOException e) {
            e.printStackTrace();
        }



        ctx.getGuild().removeRoleFromMember(id,
                Objects.requireNonNull(ctx.getGuild().getRoleById(ctx.getConf().get("whitelist_role_id")))).queue();
        System.out.println("removed role");
        ctx.getChannel().sendMessage("Remember to reset the nickname.").queue();
        System.out.println(id);
        System.out.println(ctx.getGuild().getMemberById(id).toString());
        Objects.requireNonNull(ctx.getGuild().getMemberById(id)).modifyNickname(null).queue();
        System.out.println("reset nickname");
    }

    @Override
    public String getName() {
        return "forceUnlink";
    }

    @Override
    public List<String> getAliases() {
        return List.of("fUnlink", "funlink", "forceunlink");
    }
}
