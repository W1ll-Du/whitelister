package io.github.w1ll_du.whitelister.command.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.collections4.BidiMap;

import java.util.Map;

public class fdLinkHandler {
    public static void handle(GuildMessageReceivedEvent event, BidiMap<String, String> playerMap, Map<String, String> conf) {
        if (event.getChannel().getId().equals(conf.get("log_channel_id"))) {
            String[] message = event.getMessage().getContentRaw().split("\\s+");
            if (message.length == 4
                    && message[2].equals("the")
                    && message[3].equals("game")) {
                if (message[1].equals("joined")) {
                    System.out.println(message[0]);
                    System.out.println(message[0].replace("\\", ""));
                    System.out.println(playerMap);
                    event.getGuild().addRoleToMember(playerMap.inverseBidiMap().get(message[0].replace("\\", "")),
                            event.getGuild().getRoleById(conf.get("online_role_id"))).queue();
                } else if (message[1].equals("left")) {
                    event.getGuild().removeRoleFromMember(playerMap.inverseBidiMap().get(message[0].replace("\\", "")),
                            event.getGuild().getRoleById(conf.get("online_role_id"))).queue();
                }
            }
        }
    }
}
