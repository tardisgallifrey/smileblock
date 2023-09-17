package com.tardisgallifrey.smileblock.Init;

import com.tardisgallifrey.smileblock.Init.ItemInit.ModCreativeTab;
import com.tardisgallifrey.smileblock.SmileBlockMain;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

//allows us to auto register some things to the existing
//event bus in main class
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SmileBlockMain.MOD_ID);

    public static final RegistryObject<Block> SMILEBLOCK = BLOCKS.register("smileblock",
            () -> new Block(Block.Properties.of(Material.STONE).strength(4f, 1200f).requiresCorrectToolForDrops().lightLevel((state) -> 15)));


    //This method allows you to create the Block Item
    //From a Creative Tab Instance in ItemInit
    //
    //HOWEVER, DO NOT create an item registry
    //in ItemInit if you use this method
    @SubscribeEvent
    public static void onRegisterItems(final @NotNull RegisterEvent event) {
        if (event.getRegistryKey()
                .equals(ForgeRegistries.Keys.ITEMS)){
            BLOCKS.getEntries()
                    .forEach((blockRegistryObject) -> {
                Block block = blockRegistryObject.get();

                Item.Properties properties = new Item.Properties()
                        .tab(ModCreativeTab.instance);

                Supplier<Item> blockItemFactory =
                        () -> new BlockItem(block, properties);
                event.register(ForgeRegistries.Keys.ITEMS,
                        blockRegistryObject.getId(),
                        blockItemFactory);
            });
        }
    }
}