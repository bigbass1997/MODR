package com.bigbass.modr.proxy;

import com.bigbass.modr.MODRMod;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e){
		MODRMod.log.warn("This is a serverside only mod!");
	}
	
	public void init(FMLInitializationEvent e){
		
	}
	
	public void stop(FMLServerStoppingEvent e){
		
	}
}
