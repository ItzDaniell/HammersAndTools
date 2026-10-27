
package com.example.hammersandtools.client;

import com.example.hammersandtools.HammersAndTools;
import com.example.hammersandtools.ModNetworking;
import com.example.hammersandtools.ToggleToolModePacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Gestiona los eventos de las teclas durante el juego.
 *
 * A diferencia de RegisterKeyMappingsEvent, los ticks del cliente
 * pertenecen al bus principal de eventos de Forge.
 */
@Mod.EventBusSubscriber(
        modid = HammersAndTools.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public class ClientKeyEvents {

    /**
     * Comprueba si el jugador presionó la tecla configurada.
     *
     * @param event evento de actualización del cliente.
     */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        // Ejecutamos la comprobación una sola vez por tick.
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        // Procesamos cada pulsación registrada de la tecla G.
        while (ModKeyMappings.TOGGLE_TOOL_MODE.consumeClick()) {

            // Enviamos al servidor la solicitud para cambiar el modo.
            ModNetworking.CHANNEL.sendToServer(
                    new ToggleToolModePacket()
            );
        }
    }
}
