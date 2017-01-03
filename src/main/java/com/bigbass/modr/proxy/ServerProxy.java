package com.bigbass.modr.proxy;

import com.bigbass.modr.commands.CommandForceRecord;
import com.bigbass.modr.commands.CommandPopulationTimes;
import com.bigbass.modr.config.Config;
import com.bigbass.modr.listeners.ServerTickEventListener;
import com.bigbass.modr.mongo.MongoController;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraft.command.CommandHandler;

public class ServerProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent e){
		Config.getInstance();
	}

	@Override
	public void init(FMLInitializationEvent e){
		MongoController.getInstance();
		
		FMLCommonHandler.instance().bus().register(new ServerTickEventListener());
		
		CommandHandler ch = (CommandHandler) FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
		ch.registerCommand(new CommandPopulationTimes());
		ch.registerCommand(new CommandForceRecord());
	}

	@Override
	public void stop(FMLServerStoppingEvent e){
		MongoController.getInstance().close();
	}
}
