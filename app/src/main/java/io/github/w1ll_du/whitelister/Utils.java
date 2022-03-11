package io.github.w1ll_du.whitelister;

import com.github.t9t.minecraftrconclient.RconClient;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {
    public static Map<String, String> conf;

    public static String rconCommand(String command) {
        try (RconClient client = RconClient.open(conf.get("mc_server_ip"), Integer.parseInt(conf.get("rcon_port")), conf.get("rcon_password"))) {
            return client.sendCommand(command);
        }
    }

    // could not for the life of me get jitpack.io working
    //https://github.com/SparklingComet/java-mojang-api/blob/592fabf3e2fb936a4db665a1ff23ab0ac40c7072/src/main/java/org/shanerx/mojang/Mojang.java#L346
    public static JSONObject getJSONObject(String url)
    {
        JSONObject obj;

        try
        {
            obj = (JSONObject) new JSONParser().parse(Unirest.get(url).asString().getBody());
            String err = (String) (obj.get("error"));
            if (err != null)
            {
                switch (err)
                {
                    case "IllegalArgumentException":
                        throw new IllegalArgumentException((String) obj.get("errorMessage"));
                    default:
                        throw new RuntimeException(err);
                }
            }
        }
        catch (ParseException | UnirestException e)
        {
            throw new RuntimeException(e);
        }

        return obj;
    }

    public static void rconCommands(List<String> commands) {
        try (RconClient client = RconClient.open(conf.get("mc_server_ip"), Integer.parseInt(conf.get("rcon_port")), conf.get("rcon_password"))) {
            for (String command : commands) {
                client.sendCommand(command);
            }
        }
    }
}
