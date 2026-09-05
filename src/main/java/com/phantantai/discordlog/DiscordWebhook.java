package com.phantantai.discordlog;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordWebhook {
    private final String url;

    public DiscordWebhook(String url) {
        this.url = url;
    }

    public void send(String message) {
        try {
            URL webhookUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) webhookUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = "{\"content\": \"" + message.replace("\"", "\\\"") + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            int response = conn.getResponseCode();
            if (response != 204) {
                DiscordLogMod.LOGGER.warn("Gui webhook that bai: " + response);
            }
            conn.disconnect();
        } catch (Exception e) {
            DiscordLogMod.LOGGER.error("Loi gui webhook: " + e.getMessage());
        }
    }
}
