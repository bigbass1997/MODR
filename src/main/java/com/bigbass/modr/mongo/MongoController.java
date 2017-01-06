package com.bigbass.modr.mongo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.bson.Document;

import com.bigbass.modr.MODRMod;
import com.bigbass.modr.config.Config;
import com.bigbass.modr.config.MongoDBOptions;
import com.bigbass.modr.data.DataRecordHandler;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

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
	
	private DataRecordHandler handlerQueue;
	
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
			
			if(handlerQueue != null){
				if(!connected){
					MODRMod.log.error("Mod is not connected to the MongoDB server!");
					writeRecordToFile();
				}
				try {
					client.getDatabase(options.database).getCollection(options.collection).insertOne(Document.parse(handlerQueue.formatToJson()));
				} catch(MongoException e){
					MODRMod.log.error("Uploading data record to database has failed!");
					e.printStackTrace();
					writeRecordToFile();
				} catch(NullPointerException e) {
					MODRMod.log.error("Uploading data record to database has failed!");
					e.printStackTrace();
					writeRecordToFile();
				}
				
				handlerQueue = null;
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
	
	public void uploadRecord(DataRecordHandler handler){
		handlerQueue = handler;
	}
	
	private void writeRecordToFile(){
		MODRMod.log.error("Attempting to write data record to file...");
		String dateTime = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss.json"));
		File file = new File(MODRMod.serverConfigurationDirectory.getParent() + "/MODR-Records/" + dateTime);
		
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(handlerQueue.formatToJsonPretty());
			writer.close();
			
			MODRMod.log.info("Successfully wrote data record to file.");
		} catch (IOException e) {
			MODRMod.log.warn("Exception occured, data record may not have been written to a file!");
			e.printStackTrace();
		} catch (NullPointerException e){
			MODRMod.log.warn("Exception occured, data record may not have been written to a file!");
			e.printStackTrace();
		}
	}
}
