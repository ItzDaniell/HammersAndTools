package com.example.hammersandtools;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Mensaje enviado al servidor para cambiar el modo de herramienta.
 *
 * No necesita datos adicionales: recibir el mensaje significa
 * que el jugador quiere activar o desactivar el modo.
 */
public class ToggleToolModePacket {

    /**
     * Escribe los datos del paquete.
     * Este paquete está vacío porque no necesita transportar datos.
     */
    public static void encode(
            ToggleToolModePacket packet,
            FriendlyByteBuf buffer
    ) {
        // No hay datos que escribir.
    }

    /**
     * Reconstruye el paquete al recibirlo.
     *
     * @param buffer Datos recibidos desde la red.
     * @return Una nueva instancia del paquete.
     */
    public static ToggleToolModePacket decode(FriendlyByteBuf buffer) {
        return new ToggleToolModePacket();
    }

    /**
     * Procesa el mensaje en el servidor.
     *
     * Cambia el modo del jugador que envió el paquete
     * y muestra un mensaje de confirmación en el chat.
     */
    public static void handle(
            ToggleToolModePacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context = contextSupplier.get();

        // Ejecutamos la lógica en el hilo principal del juego.
        context.enqueueWork(() -> {
            // Identificamos al jugador que envió el mensaje.
            ServerPlayer player = context.getSender();

            // Ignoramos el mensaje si no hay un jugador asociado.
            if (player == null) {
                return;
            }

            // Cambiamos el estado y obtenemos el nuevo valor.
            boolean active = ToolMode.toggle(player.getUUID());

            // Informamos al jugador del estado actual.
            player.sendSystemMessage(
                    Component.literal(
                            active
                                    ? "HammersAndTools: Modo Activado"
                                    : "HammersAndTools: Modo Desactivado"
                    )
            );
        });

        // Indicamos a Forge que el paquete fue procesado.
        context.setPacketHandled(true);
    }
}
