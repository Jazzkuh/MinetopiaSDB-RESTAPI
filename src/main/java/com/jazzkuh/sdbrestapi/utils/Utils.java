package com.jazzkuh.sdbrestapi.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jazzkuh.sdbrestapi.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    Main plugin;

    public Utils(Main plugin) {
        this.plugin = plugin;
    }

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
    public static void sendMessage(Player player, String input) {
        player.sendMessage(Utils.color(input));
    }
    public static void sendMessage(CommandSender sender, String input) {
        sender.sendMessage(Utils.color(input));
    }

    public static String getServerIP() {
        JsonObject root = getJSON("https://verify.minetopiasdb.nl/reqip.php", "POST");
        return root == null ? "-1" : root.get("message").getAsString();
    }

    private static JsonObject getJSON(String url, String method) {
        try {
            HttpURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "MTWAPENS");
            connection.setRequestProperty("Version", Main.getInstance().getDescription().getVersion());
            connection.connect();

            return new JsonParser().parse(new InputStreamReader((InputStream) connection.getContent())).getAsJsonObject();
        } catch (IOException ignored) {
        }

        return null;
    }
}

