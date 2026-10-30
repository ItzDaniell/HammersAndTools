package com.example.hammersandtools.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase base para todos los martillos del mod.
 * Extiende DiggerItem usando la etiqueta BlockTags.MINEABLE_WITH_PICKAXE
 * para comportarse de forma idéntica a un pico vanilla respecto a qué bloques
 * puede minar.
 */
public class HammerItem extends DiggerItem {

    /**
     * Nivel del martillo:
     * 1 = Minería de área plana 3x3 orientada a la cara minada (radio 1, profundidad 1).
     * 2 = Minería volumétrica 3x3x3 con profundidad coherente (radio 1, profundidad 3).
     * 3 = Minería de área plana 5x5 orientada a la cara minada (radio 2, profundidad 1).
     * 4 = Minería volumétrica 5x5x5 con profundidad coherente (radio 2, profundidad 5).
     */
    private final int miningLevel;

    /**
     * Bandera para evitar recursión si la rotura de bloques adicionales
     * volviera a disparar eventos o llamadas en cadena en el mismo hilo.
     */
    private static final ThreadLocal<Boolean> IS_AREA_MINING = ThreadLocal.withInitial(() -> false);

    public HammerItem(Tier tier, float attackDamage, float attackSpeed, int miningLevel, Properties properties) {
        super(attackDamage, attackSpeed, tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
        this.miningLevel = miningLevel;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    /**
     * Permite explícitamente aplicar encantamientos compatibles con herramientas,
     * picos y daño a objetos (como Irrompibilidad / Unbreaking y Reparación / Mending)
     * en el yunque o mesa de encantamientos.
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.UNBREAKING || enchantment == Enchantments.MENDING) {
            return true;
        }
        if (enchantment.category == EnchantmentCategory.DIGGER || enchantment.category == EnchantmentCategory.BREAKABLE) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    /**
     * Se ejecuta cuando un bloque es roto por el jugador usando este martillo.
     * Aquí se gestiona la minería de bloques adicionales en el servidor.
     */
    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        // Ejecución estándar de durabilidad para el bloque objetivo principal
        boolean result = super.mineBlock(stack, level, state, pos, entityLiving);

        // La minería de área solo se procesa en el servidor y si quien mina es un
        // ServerPlayer
        if (!level.isClientSide() && entityLiving instanceof ServerPlayer player && !IS_AREA_MINING.get()) {
            // Comprobamos si el jugador tiene activo el modo de herramienta con la tecla G.
            // Si está desactivado, el martillo mina en modo normal 1x1.
            if (!com.example.hammersandtools.ToolMode.isActive(player.getUUID())) {
                return result;
            }

            // Solo romper en área si el bloque objetivo es apto para ser minado por esta
            // herramienta
            if (state.getDestroySpeed(level, pos) > 0 && isCorrectToolForDrops(state)) {
                executeAreaMining(stack, (ServerLevel) level, pos, player);
            }
        }

        return result;
    }

    /**
     * Realiza el minado en área calculando la orientación de la cara que el jugador
     * golpeó.
     */
    private void executeAreaMining(ItemStack stack, ServerLevel level, BlockPos originPos, ServerPlayer player) {
        IS_AREA_MINING.set(true);
        try {
            Direction hitFace = getTargetDirection(player);
            List<BlockPos> blocksToBreak = calculateAreaPositions(originPos, hitFace, this.miningLevel);

            for (BlockPos targetPos : blocksToBreak) {
                // Si el martillo se destruye o se queda sin durabilidad restante, detenemos la
                // minería de área
                if (stack.isEmpty() || (stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage())) {
                    break;
                }

                // Ignoramos el bloque de origen, ya que fue minado en el flujo normal
                if (targetPos.equals(originPos)) {
                    continue;
                }

                BlockState targetState = level.getBlockState(targetPos);

                // Verificaciones de seguridad:
                // 1. Dureza > 0 (evita romper bedrock u objetos indestructibles)
                // 2. Comprueba que el martillo sea herramienta válida para recolectar drops de
                // este bloque
                // 3. Verifica que el bloque no sea aire
                if (targetState.isAir() || targetState.getDestroySpeed(level, targetPos) < 0
                        || !isCorrectToolForDrops(targetState)) {
                    continue;
                }

                // destroyBlock ejecuta la rotura de bloque simulando la acción del jugador,
                // respetando protecciones y descontando automáticamente 1 punto de durabilidad
                // a la herramienta.
                player.gameMode.destroyBlock(targetPos);
            }
        } finally {
            IS_AREA_MINING.set(false);
        }
    }

    /**
     * Determina la cara del bloque a la que el jugador estaba apuntando mediante un
     * trazado de rayos.
     */
    private static Direction getTargetDirection(ServerPlayer player) {
        double reach = player.getBlockReach();
        Vec3 eyePos = player.getEyePosition(1.0f);
        Vec3 lookVec = player.getViewVector(1.0f);
        Vec3 endPos = eyePos.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);

        BlockHitResult hitResult = player.level().clip(new ClipContext(
                eyePos,
                endPos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player));

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            return hitResult.getDirection();
        }

        // Si no detecta una cara directamente, deducir la dirección según la
        // inclinación de la vista del jugador
        return Direction.getNearest(lookVec.x, lookVec.y, lookVec.z).getOpposite();
    }

    /**
     * Calcula la lista de posiciones de bloques que deben ser minados.
     * Nivel 1: plano 3x3 perpendicular a la cara impactada (radio 1, profundidad 1 -> 9 bloques).
     * Nivel 2: cubo 3x3x3 adentrándose en el túnel (radio 1, profundidad 3 -> 27 bloques).
     * Nivel 3: plano 5x5 perpendicular a la cara impactada (radio 2, profundidad 1 -> 25 bloques).
     * Nivel 4: cubo 5x5x5 adentrándose en el túnel (radio 2, profundidad 5 -> 125 bloques).
     */
    public static List<BlockPos> calculateAreaPositions(BlockPos origin, Direction hitFace, int level) {
        List<BlockPos> positions = new ArrayList<>();

        int radius = (level >= 3) ? 2 : 1;
        int depthCount = 1;
        if (level == 2) {
            depthCount = 3;
        } else if (level >= 4) {
            depthCount = 5;
        }

        Direction depthDirection = hitFace.getOpposite();
        Direction.Axis axis = hitFace.getAxis();

        for (int depth = 0; depth < depthCount; depth++) {
            BlockPos layerOrigin = origin.relative(depthDirection, depth);

            if (axis == Direction.Axis.Y) {
                // Minando hacia arriba o hacia abajo: el área está en el plano XZ (suelo/techo)
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        positions.add(layerOrigin.offset(x, 0, z));
                    }
                }
            } else if (axis == Direction.Axis.X) {
                // Minando hacia el Este o Oeste: el área está en el plano YZ
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        positions.add(layerOrigin.offset(0, y, z));
                    }
                }
            } else if (axis == Direction.Axis.Z) {
                // Minando hacia el Norte o Sur: el área está en el plano XY
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        positions.add(layerOrigin.offset(x, y, 0));
                    }
                }
            }
        }

        return positions;
    }
}
