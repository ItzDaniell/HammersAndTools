package com.example.hammersandtools;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Administra el estado del modo especial de las herramientas.
 * Guarda qué jugadores tienen el modo activado.
 */
public class ToolMode {

    /**
     * Conjunto de identificadores de jugadores con el modo activo.
     * UUID permite identificar a cada jugador de forma única.
     */
    private static final Set<UUID> ACTIVE_PLAYERS = new HashSet<>();

    /**
     * Comprueba si un jugador tiene activado el modo especial.
     *
     * @param playerId UUID del jugador que queremos consultar.
     * @return true si el modo está activo; false en caso contrario.
     */
    public static boolean isActive(UUID playerId) {
        return ACTIVE_PLAYERS.contains(playerId);
    }

    /**
     * Cambia el estado del modo especial de un jugador.
     *
     * Si estaba activo, lo desactiva.
     * Si estaba desactivado, lo activa.
     *
     * @param playerId UUID del jugador cuyo modo queremos cambiar.
     * @return true si queda activado; false si queda desactivado.
     */
    public static boolean toggle(UUID playerId) {
        // Si el jugador estaba en el conjunto, lo quitamos.
        if (ACTIVE_PLAYERS.remove(playerId)) {
            return false;
        }

        // Si no estaba, lo añadimos y activamos su modo.
        ACTIVE_PLAYERS.add(playerId);
        return true;
    }

    /**
     * Elimina el estado del jugador.
     * Puede utilizarse al desconectarse para limpiar datos temporales.
     *
     * @param playerId UUID del jugador que queremos limpiar.
     */
    public static void clear(UUID playerId) {
        ACTIVE_PLAYERS.remove(playerId);
    }
}
