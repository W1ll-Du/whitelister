package io.github.w1ll_du.whitelister.command.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.w1ll_du.whitelister.command.CommandContext;
import io.github.w1ll_du.whitelister.command.ARconCommand;
import io.github.w1ll_du.whitelister.Utils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Paths;

public class LinkCommand extends ARconCommand {
    @Override
    protected void handle2(CommandContext ctx) {
        if (ctx.getArgs().size() == 0) {
            new UnlinkCommand().handle(ctx);
            return;
        }
        String name = ctx.getArgs().get(0);
        if (! isValidUsername(name)) {
            ctx.getChannel().sendMessage("Username invalid")
                    .queue();
            return;
        }
        // already linked with another disc account
        if (ctx.getPlayerMap().containsValue(name)) {
            ctx.getChannel().sendMessage(name + " is already linked.")
                    .queue();
            return;
        }
        // already linked with another mc account
        if (ctx.getPlayerMap().containsKey(ctx.getAuthor().getId())) {
            new UnlinkCommand().handle(ctx);
        }
        // https://github.com/SparklingComet/java-mojang-api
        try {
            // checks validity of name
            JSONObject resp = Utils.getJSONObject("https://api.mojang.com/users/profiles/minecraft/" + name);
            // enforce the case
            String username = (String) resp.get("name");
            // check case matching
            if (!username.equals(name)) {
                ctx.getChannel().sendMessage("The case does not match, please try again.").queue();
                ctx.getChannel().sendMessage("The proper name is " + username + ".").queue();
                return;
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
