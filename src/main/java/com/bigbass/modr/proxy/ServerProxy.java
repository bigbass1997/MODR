package com.bigbass.modr.proxy;

import com.bigbass.modr.config.Config;
import com.bigbass.modr.listeners.ServerTickEventListener;
import com.bigbass.modr.mongo.MongoController;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent e){
		Config.getInstance();
	}

	@Override
	public void init(FMLInitializationEvent e){
		MongoController.getInstance();
		
		MinecraftForge.EVENT_BUS.register(new ServerTickEventListener());
	}

	@Override
	public void stop(FMLServerStoppingEvent e){
		
	}
}
