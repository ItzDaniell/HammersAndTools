
package com.example.hammersandtools.client;

import com.example.hammersandtools.HammersAndTools;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * Registra las teclas configurables de HammersAndTools.
 */
@Mod.EventBusSubscriber(
        modid = HammersAndTools.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ModKeyMappings {

    /**
     * Tecla G para activar o desactivar el modo de herramienta.
     * El jugador puede cambiarla desde los controles de Minecraft.
     */
    public static final KeyMapping TOGGLE_TOOL_MODE =
            new KeyMapping(
                    "key.hammersandtools.toggle_tool_mode",
                    GLFW.GLFW_KEY_G,
                    "key.categories.hammersandtools"
            );

    /**
     * Registra la tecla en las opciones de controles de Minecraft.
     */
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_TOOL_MODE);
    }
}
