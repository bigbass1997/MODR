package com.bigbass.modr.listeners;

import com.bigbass.modr.MODRMod;
import com.bigbass.modr.config.Config;
import com.bigbass.modr.data.DataRecordHandler;
import com.bigbass.modr.mongo.MongoController;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class ServerTickEventListener {
	
	private long lastTime = 0;
	private long interval = Long.MAX_VALUE;
	
	public ServerTickEventListener(){
		lastTime = System.currentTimeMillis();
		interval = Config.getInstance().options.timeBetweenRecords;
	}
	
	@SubscribeEvent
	public void onServerTickEvent(ServerTickEvent e){
		if(System.currentTimeMillis() - lastTime > interval){
			//TODO surround this block with a try-catch block to prevent server crashes in case something does go wrong.
			try {
				MODRMod.log.info("Starting DataRecord population process..");
				
				DataRecordHandler dataHand = new DataRecordHandler();
				
				dataHand.populateRecord();
				
				MongoController.getInstance().uploadRecord(dataHand);
			} catch(Exception ex) {
				MODRMod.log.error("An unexpected exception has occured when trying to populate data record!");
				ex.printStackTrace();
			}
			
			lastTime = System.currentTimeMillis(); // This is outside the try-catch block, to prevent the above from failing over and over.
		}
		
		/*
		 * If enough time has elapsed since last record population, create and populate a new DataRecordHandler.
		 * Then give the DataRecordHandler to the MongoController for the controller to upload the record.
		 * DO NOT WAIT FOR A RESPONSE!
		 * Waiting for a response from the controller will stall the server until either timeout or successful transfer.
		 * 
		 * The controller will take care of any issues that happen. If the upload fails, it will save record as a file instead.
		 * */
	}
}
