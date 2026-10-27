
package com.example.hammersandtools;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Escucha eventos relacionados con los jugadores.
 *
 * Esta clase se ejecuta en el bus principal de eventos de Forge.
 */
@Mod.EventBusSubscriber(
        modid = HammersAndTools.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class ToolModeEvents {

    /**
     * Se ejecuta durante los ticks de cada jugador.
     *
     * Un tick es una actualización del juego.
     * Minecraft normalmente procesa 20 ticks por segundo.
     */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        // Solo procesamos la fase END para evitar ejecutar
        // nuestra lógica dos veces por tick.
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        // Solo continuamos si el jugador es una instancia
        // de ServerPlayer, es decir, un jugador en el servidor.
        if (!(event.player instanceof ServerPlayer player)) {
            return;
        }

        /*
         * Aquí conectaremos la recepción del paquete de red
         * enviado cuando el jugador presione la tecla G.
         *
         * No podemos leer KeyMapping directamente aquí,
         * porque las teclas se detectan en el cliente.
         */
    }
}
