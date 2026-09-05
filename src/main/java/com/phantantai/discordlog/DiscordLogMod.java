package com.phantantai.discordlog;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscordLogMod implements ModInitializer {
    public static final String MOD_ID = "discordlog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // 🔥 WEBHOOK CỦA BẠN
    public static final String WEBHOOK_URL = "https://discord.com/api/webhooks/1545859821562237130/MoyyGG_f3bIzGGLsxcTtgTgKi86iMSLJyHKutGwlnL1mHvdSXz7gsf5moMvfptnrA1k5";

    public static DiscordWebhook webhook;

    @Override
    public void onInitialize() {
        LOGGER.info("Discord Log Mod khoi dong!");
        LOGGER.info("Webhook URL da duoc cau hinh!");

        webhook = new DiscordWebhook(WEBHOOK_URL);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            webhook.send("🚀 **Server da khoi dong!**");
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            webhook.send("🛑 **Server dang tat...**");
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            String name = handler.getPlayer().getName().getString();
            webhook.send("✅ **" + name + "** da vao server!");
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            String name = handler.getPlayer().getName().getString();
            webhook.send("❌ **" + name + "** da roi server!");
        });
    }
}
