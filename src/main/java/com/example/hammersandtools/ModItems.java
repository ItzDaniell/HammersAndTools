
package com.example.hammersandtools;

import com.example.hammersandtools.item.HammerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registra todos los objetos del mod HammersAndTools.
 */
public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(
                    ForgeRegistries.ITEMS,
                    HammersAndTools.MOD_ID
            );

    // ==========================================
    // NÚCLEOS DE MINERÍA Y ESPECIALIZACIÓN
    // ==========================================

    /** Núcleo NV1 básico (reutilizado) */
    public static final RegistryObject<Item> MINING_CORE =
            ITEMS.register("mining_core", () -> new Item(new Item.Properties()));

    /** Núcleo NV2 reforzado */
    public static final RegistryObject<Item> REINFORCED_CORE =
            ITEMS.register("reinforced_core", () -> new Item(new Item.Properties()));

    /** Núcleo NV3 avanzado */
    public static final RegistryObject<Item> ADVANCED_CORE =
            ITEMS.register("advanced_core", () -> new Item(new Item.Properties()));

    /** Núcleo NV4 superior */
    public static final RegistryObject<Item> SUPERIOR_CORE =
            ITEMS.register("superior_core", () -> new Item(new Item.Properties()));

    /** Núcleos conservados para compatibilidad futura */
    public static final RegistryObject<Item> TREE_CORE =
            ITEMS.register("tree_core", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EXCAVATION_CORE =
            ITEMS.register("excavation_core", () -> new Item(new Item.Properties()));

    // ==========================================
    // MARTILLOS NIVEL 1 (Área 3x3)
    // ==========================================

    public static final RegistryObject<Item> STONE_HAMMER =
            ITEMS.register("stone_hammer", () -> new HammerItem(
                    Tiers.STONE,
                    5.0f, -3.2f, 1,
                    new Item.Properties().durability(260)
            ));

    public static final RegistryObject<Item> IRON_HAMMER =
            ITEMS.register("iron_hammer", () -> new HammerItem(
                    Tiers.IRON,
                    6.0f, -3.1f, 1,
                    new Item.Properties().durability(500)
            ));

    public static final RegistryObject<Item> GOLD_HAMMER =
            ITEMS.register("gold_hammer", () -> new HammerItem(
                    Tiers.GOLD,
                    5.0f, -2.8f, 1,
                    new Item.Properties().durability(64)
            ));

    public static final RegistryObject<Item> DIAMOND_HAMMER =
            ITEMS.register("diamond_hammer", () -> new HammerItem(
                    Tiers.DIAMOND,
                    7.0f, -3.0f, 1,
                    new Item.Properties().durability(3122)
            ));

    public static final RegistryObject<Item> NETHERITE_HAMMER =
            ITEMS.register("netherite_hammer", () -> new HammerItem(
                    Tiers.NETHERITE,
                    8.0f, -3.0f, 1,
                    new Item.Properties().durability(4062).fireResistant()
            ));

    // ==========================================
    // MARTILLOS NIVEL 2 (Volumen 3x3x3)
    // ==========================================

    public static final RegistryObject<Item> STONE_HAMMER_T2 =
            ITEMS.register("stone_hammer_t2", () -> new HammerItem(
                    Tiers.STONE,
                    5.5f, -3.2f, 2,
                    new Item.Properties().durability(650)
            ));

    public static final RegistryObject<Item> IRON_HAMMER_T2 =
            ITEMS.register("iron_hammer_t2", () -> new HammerItem(
                    Tiers.IRON,
                    6.5f, -3.1f, 2,
                    new Item.Properties().durability(1250)
            ));

    public static final RegistryObject<Item> GOLD_HAMMER_T2 =
            ITEMS.register("gold_hammer_t2", () -> new HammerItem(
                    Tiers.GOLD,
                    5.5f, -2.8f, 2,
                    new Item.Properties().durability(160)
            ));

    public static final RegistryObject<Item> DIAMOND_HAMMER_T2 =
            ITEMS.register("diamond_hammer_t2", () -> new HammerItem(
                    Tiers.DIAMOND,
                    7.5f, -3.0f, 2,
                    new Item.Properties().durability(7800)
            ));

    public static final RegistryObject<Item> NETHERITE_HAMMER_T2 =
            ITEMS.register("netherite_hammer_t2", () -> new HammerItem(
                    Tiers.NETHERITE,
                    8.5f, -3.0f, 2,
                    new Item.Properties().durability(10150).fireResistant()
            ));
}
