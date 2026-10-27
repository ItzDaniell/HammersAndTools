package com.example.hammersandtools;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registra la pestaña en el menú de modo creativo para agrupar los objetos del mod.
 */
public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HammersAndTools.MOD_ID);

    public static final RegistryObject<CreativeModeTab> HAMMERS_TAB =
            CREATIVE_MODE_TABS.register("hammers_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.hammersandtools"))
                    .icon(() -> new ItemStack(ModItems.DIAMOND_HAMMER.get()))
                    .displayItems((parameters, output) -> {
                        // Núcleos
                        output.accept(ModItems.MINING_CORE.get());
                        output.accept(ModItems.REINFORCED_CORE.get());
                        output.accept(ModItems.ADVANCED_CORE.get());
                        output.accept(ModItems.SUPERIOR_CORE.get());
                        output.accept(ModItems.TREE_CORE.get());
                        output.accept(ModItems.EXCAVATION_CORE.get());

                        // Martillos Nivel 1
                        output.accept(ModItems.STONE_HAMMER.get());
                        output.accept(ModItems.IRON_HAMMER.get());
                        output.accept(ModItems.GOLD_HAMMER.get());
                        output.accept(ModItems.DIAMOND_HAMMER.get());
                        output.accept(ModItems.NETHERITE_HAMMER.get());

                        // Martillos Nivel 2
                        output.accept(ModItems.STONE_HAMMER_T2.get());
                        output.accept(ModItems.IRON_HAMMER_T2.get());
                        output.accept(ModItems.GOLD_HAMMER_T2.get());
                        output.accept(ModItems.DIAMOND_HAMMER_T2.get());
                        output.accept(ModItems.NETHERITE_HAMMER_T2.get());
                    })
                    .build()
            );
}
