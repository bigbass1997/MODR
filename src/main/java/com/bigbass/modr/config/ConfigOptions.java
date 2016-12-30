package com.bigbass.modr.config;

public class ConfigOptions {

	public long timeBetweenRecords = 3600000;
	
	public MongoDBOptions mongodb;
	
	public ConfigOptions(){
		timeBetweenRecords = 3600000;
		mongodb = new MongoDBOptions();
	}
}
