package com.phantantai.discordlog;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhook {
    public static void send(String webhookUrl, String content) {
        new Thread(() -> {
            try {
                URL url = new URL(webhookUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.setDoOutput(true);

                String jsonPayload = "{\"content\": \"" + content.replace("\"", "\\\"").replace("\n", "\\n") + "\"}";

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                conn.getResponseCode();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
