package com.phantantai.discordlog;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;

public class DiscordLogMod implements ClientModInitializer {
    private static final String WEBHOOK_URL = "https://discord.com/api/webhooks/1545859821562237130/MoyyGG_f3bIzGGLsxcTtgTgKi86iMSLJyHKutGwlnL1mHvdSXz7gsf5moMvfptnrA1k5";
    private boolean hasLoggedJoin = false;
    private String lastServer = "";

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.world != null && mc.getCurrentServerEntry() != null) {
                String currentServer = mc.getCurrentServerEntry().address;
                String playerName = mc.getSession().getUsername();
                
                if (!lastServer.equals(currentServer)) {
                    lastServer = currentServer;
                    hasLoggedJoin = false;
                }

                if (!hasLoggedJoin) {
                    String screenName = mc.currentScreen != null ? mc.currentScreen.getTitle().getString() : "None";
                    String msg = "🟢 **Đã vào server:** " + currentServer + "\n👤 **Tên nhân vật:** " + playerName + "\n🖼️ **GUI hiện tại:** " + screenName;
                    DiscordWebhook.send(WEBHOOK_URL, msg);
                    hasLoggedJoin = true;
                }
            } else {
                hasLoggedJoin = false;
                lastServer = "";
            }
        });

        ClientSendMessageEvents.COMMAND.register(command -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            String playerName = mc.getSession().getUsername();
            String server = mc.getCurrentServerEntry() != null ? mc.getCurrentServerEntry().address : "Singleplayer";
            String msg = "⌨️ **Player:** " + playerName + "\n🌐 **Server:** " + server + "\n💻 **Lệnh:** `" + command + "`";
            DiscordWebhook.send(WEBHOOK_URL, msg);
        });
    }
}
