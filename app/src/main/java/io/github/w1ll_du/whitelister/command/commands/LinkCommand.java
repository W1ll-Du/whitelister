package io.github.w1ll_du.whitelister.command.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.command.ICommand;
import io.github.w1ll_du.whitelister.Utils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Paths;

public class LinkCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getArgs().size() == 0) {
            new UnlinkCommand().handle(ctx);
            return;
        }
        String username = ctx.getArgs().get(0);
        if (ctx.getPlayerMap().containsKey(ctx.getAuthor().getId())) {
            new UnlinkCommand().handle(ctx);
        }
        if (! isValidUsername(username)) {
            ctx.getChannel().sendMessage("Username invalid").queue();
            return;
        }
        if (ctx.getPlayerMap().containsValue(username)) {
            ctx.getChannel().sendMessage(username
                    + " is already linked with "
                    + ctx.getGuild().getMemberById(ctx.getPlayerMap().inverseBidiMap().get(username)).getEffectiveName()
                    + ".")
                .queue();
        }
        // https://github.com/SparklingComet/java-mojang-api
        else {
            try {
                // checks validity of name
                JSONObject resp = Utils.getJSONObject("https://api.mojang.com/users/profiles/minecraft/" + username);
                // makes sure the case is right
                username = (String) resp.get("name");
                if (ctx.getPlayerMap().containsKey(ctx.getAuthor().getId())) {
                    String oldName = ctx.getPlayerMap().get(ctx.getAuthor().getId());
                    Utils.rconCommand("whitelist remove " + oldName);
                    ctx.getChannel().sendMessage("Unliked with " + oldName).queue();
                }
                ctx.getPlayerMap().put(ctx.getAuthor().getId(), username);
                Utils.rconCommand("whitelist add " + username);
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(Paths.get("playerMap.json").toFile(), ctx.getPlayerMap());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Utils.rconCommand("whitelist reload");
                ctx.getChannel().sendMessage("Linked with " + username).queue();
                ctx.getMember().modifyNickname(username).queue();
                ctx.getGuild().addRoleToMember(ctx.getMember(),
                        ctx.getGuild().getRoleById(ctx.getConf().get("whitelist_role_id"))).queue();
            } catch (Exception e) {
                ctx.getChannel().sendMessage("The account does not exist.").queue();
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return "link";
    }

    private static boolean isValidUsername(String name) {
        // https://help.mojang.com/customer/portal/articles/928638-minecraft-usernames
        String allowed_chars = "abcdefghijklmnopqrstuvwxyz1234567890_";
        name = name.toLowerCase();
        if (name.length() < 3 || name.length() > 16) {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            if (! allowed_chars.contains(name.substring(i, i + 1))) {
                return false;
            }
        }
        return true;
    }
}
