package com.tardisgallifrey.smileblock; //our mod java package

//Imports needed for classes used below
import com.tardisgallifrey.smileblock.Init.BlockInit;
import com.tardisgallifrey.smileblock.Init.ItemInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


//Class annotator to set this class
//as a Forge Modded class
@Mod(SmileBlockMain.MOD_ID)
public class SmileBlockMain {

    //We should declare a logger here.
    //The base one doesn't work right, so we'll do it later.

    //set MOD_ID variable for above.
    //Must be same as what is in TOML and Build.Gradle
    public static final String MOD_ID = "smileblock";

    //Constructor establishes event bus from the
    //Java Mod Loader
    //then adds an event listener, and
    //adds our ITEMS created in ItemInit to the
    //event bus
    public SmileBlockMain() {
        //establish event bus from Forge Java Mod Loader
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Add listener to this class
        modEventBus.addListener(this::setup);

        //register our ITEMS to the event bus
        ItemInit.ITEMS.register(modEventBus);

        //Register our event bus?? to MinecraftForge
        //Event bus class
        MinecraftForge.EVENT_BUS.register(this);

        //Register blocks to the event bus
        BlockInit.BLOCKS.register(modEventBus);
    }

    //call setup method with param of
    //event of type FMLCommonSetupEvent
    private void setup(final FMLCommonSetupEvent event) {

    }
}