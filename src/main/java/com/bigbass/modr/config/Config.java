package com.bigbass.modr.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.bigbass.modr.MODRMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Config {
	
	private static Config instance;
	
	public File configFile;
	
	public ConfigOptions options;
	
	private Config(){
		File file = MODRMod.modConfigurationDirectory;
		if(!file.exists()){
			file.mkdir();
		}
		
		if(instance != null){
			MODRMod.log.info("Attempted to load config file more than once! Report to mod author.");
			return;
		}
		init(file);
	}
	
	private void init(File rootDir){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		configFile = new File(rootDir.getPath() + "/config.json");
		
		if(!configFile.exists()){ // If config file doesn't exist, attempt to create default config file
			try {
				MODRMod.log.info("Attempting to create default configuration file...");
				configFile.createNewFile();
			} catch (IOException e) {
				MODRMod.log.info("Exception when attempting to create configuration file!");
				e.printStackTrace();
			}
			
			if(configFile.exists()){ // Write default config to newly created file
				options = new ConfigOptions();
				String json = gson.toJson(options);
				
				boolean wroteJson = false;
				try {
					FileWriter writer = new FileWriter(configFile);
					writer.write(json);
					writer.close();
					
					wroteJson = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(wroteJson){
					MODRMod.log.info("Successfully created default configuration file!");
				}
				
			} else { // If configFile still doesn't exist, initialize default options and log error
				options = new ConfigOptions();
				MODRMod.log.warn("Config file creation has failed! Loading default config options. Server connection will most certainly fail. :(");
			}
		} else { // If config file already exists, read and load the options from it
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(configFile));
			} catch (FileNotFoundException e) {
				MODRMod.log.warn("Configuration file not found when attempting to read!");
				e.printStackTrace();
			}
			
			if(br != null){
				options = gson.fromJson(br, ConfigOptions.class);
				
				MODRMod.log.info("Existent configuration file has been loaded.");
			}
		}
	}
	
	public static Config getInstance(){
		if(instance == null){
			instance = new Config();
		}
		
		return instance;
	}
}
