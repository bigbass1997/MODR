package com.bigbass.modr;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bigbass.modr.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = MODRMod.MODID, version = MODRMod.VERSION, acceptableRemoteVersions = "*")
public class MODRMod {
	
    public static final String MODID = "modr";
    public static final String VERSION = "0.0";
    
    public static final Logger log = LogManager.getLogger("MODR");
    
    @Mod.Instance(MODID)
    public static MODRMod instance;
    
    @SidedProxy(clientSide = "com.bigbass.modr.proxy.CommonProxy", serverSide = "com.bigbass.modr.proxy.ServerProxy")
    public static CommonProxy proxy;
    
    public static File modConfigurationDirectory;
    
    @Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e){
    	modConfigurationDirectory = e.getModConfigurationDirectory();
    	
		proxy.preInit(e);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
		proxy.init(e);
    }
    
    @Mod.EventHandler
	public void serverStopping(FMLServerStoppingEvent e){
		proxy.stop(e);
    }
}
