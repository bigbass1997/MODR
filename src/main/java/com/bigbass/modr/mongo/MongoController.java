package com.bigbass.modr.mongo;

import com.bigbass.modr.MODRMod;
import com.bigbass.modr.config.Config;
import com.bigbass.modr.config.MongoDBOptions;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * <p>Controller class for MongoDB operations.</p>
 * 
 * <p>Contains a static instance so that only one connection
 * can be made between the MC server and the database.</p>
 */
public class MongoController extends Thread {
	
	private static MongoController instance;
	
	private MongoClient client;
	
	/** Is the thread still running or not */
	private boolean running;
	
	/** Is the MongoClient connected to the server or not */
	private boolean connected = false;
	
	/**
	 * Start the Thread as soon as it is created.
	 */
	private MongoController(){
		running = true;
		
		start();
	}
	
	/**
	 * Gets the MongoController instance.
	 * If instance has not been initialized and assigned yet, do so.
	 * 
	 * @return MongoController instance
	 */
	public static MongoController getInstance(){
		if(instance == null){
			instance = new MongoController();
		}
		
		return instance;
	}
	
	@Override
	public void run(){
		MODRMod.log.info("MongoController Thread started!");
		MongoDBOptions options = Config.getInstance().options.mongodb;
		client = new MongoClient(new MongoClientURI("mongodb://" + options.username + ":" + options.password + "@" + options.hostname + "/?authSource=" + options.authDatabase));
		
		while(running){
			if(client.getDatabase(options.database).listCollectionNames().first() != null){
				connected = true;
			} else {
				connected = false;
			}
		}
		
		client.close();
		MODRMod.log.info("MongoController Thread ended! MongoDB Client connection closed!");
	}
	
	public boolean isConnected(){
		return this.connected;
	}
	
	public void close(){
		this.running = false;
	}
	
	public MongoClient getCli(){
		return client;
	}
}
