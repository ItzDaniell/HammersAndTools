package com.example.hammersandtools;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HammersAndTools.MOD_ID)
public class HammersAndTools {

    public static final String MOD_ID = "hammersandtools";

    public HammersAndTools() {
        IEventBus modEventBus =
                FMLJavaModLoadingContext.get().getModEventBus();

        // Registrar los objetos del mod
        ModItems.ITEMS.register(modEventBus);
        // Registrar la pestaña creativa
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        ModNetworking.register();
    }
}
