package org.l2x9.l2x9core.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.l2x9.l2x9core.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GithubStatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length > 0) {
            String link = "https://api.github.com/users/" + args[0];
            try {
                URL url = new URL(link);
                InputStream stream = url.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                JsonParser parser = new JsonParser();
                JsonElement parsed = parser.parse(reader);
                JsonObject jsonObject = parsed.getAsJsonObject();
                String repos = jsonObject.get("public_repos").getAsString();
                int repoAmount = jsonObject.get("public_repos").getAsInt();


                reader.close();
                stream.close();
            } catch (IOException exception) {
                Utils.sendMessage(commandSender, "&4Error:&r&c The provided user " + args[0] + " is not a valid github user");
            }

        }
        return true;
    }
}
