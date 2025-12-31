package lol.plucky.hideScoreboard.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class MainClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hide-scoreboard.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KeyBinding.MISC_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                if (client.player != null) {
                    ScoreboardState.toggleScoreboard();
                    String messageKey = ScoreboardState.isScoreboardVisible() ? "key.hide-scoreboard.shown" : "key.hide-scoreboard.hidden";
                    client.player.sendMessage(Text.translatable(messageKey).formatted(Formatting.YELLOW), true);
                }
            }
        });

        ClientPlayConnectionEvents.JOIN.register((handler, packet, client) -> {
            if (client.player != null && !ScoreboardState.isScoreboardVisible()) {
                client.player.sendMessage(Text.translatable("key.hide-scoreboard.warning").formatted(Formatting.GRAY, Formatting.ITALIC), true);
            }
        });
    }
}
