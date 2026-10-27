
package com.example.hammersandtools;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Administra la comunicación de red de HammersAndTools.
 *
 * Permite enviar información del cliente al servidor,
 * por ejemplo, cuando el jugador presiona la tecla G.
 */
public class ModNetworking {

    // Versión del protocolo de comunicación del mod.
    private static final String PROTOCOL_VERSION = "1";

    /**
     * Canal de red utilizado para enviar paquetes del mod.
     *
     * El protocolo debe coincidir entre cliente y servidor.
     */
    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                    new ResourceLocation(HammersAndTools.MOD_ID, "main"),
                    () -> PROTOCOL_VERSION,
                    PROTOCOL_VERSION::equals,
                    PROTOCOL_VERSION::equals
            );

    /**
     * Registra los paquetes que el mod puede enviar.
     *
     * Se llamará durante la inicialización del mod.
     */
    public static void register() {
        CHANNEL.messageBuilder(
                        ToggleToolModePacket.class,
                        0,
                        net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER
                )
                .encoder(ToggleToolModePacket::encode)
                .decoder(ToggleToolModePacket::decode)
                .consumerMainThread(ToggleToolModePacket::handle)
                .add();
    }
}
