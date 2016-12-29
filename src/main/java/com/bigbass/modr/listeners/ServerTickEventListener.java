package com.bigbass.modr.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class ServerTickEventListener {
	
	@SubscribeEvent
	public static void onServerTickEvent(ServerTickEvent e){
		/*
		 * If enough time has elapsed since last record population, create and populate a new DataRecordHandler.
		 * Then give the DataRecordHandler to the MongoController for the controller to upload the record.
		 * DO NOT WAIT FOR A RESPONSE!
		 * Waiting for a response from the controller will stall the server until either timeout or successful transfer.
		 * 
		 * The controller will take care of any issues that happen. If the upload fails, it will use a
		 * method in the DataRecordHandler to save as a file instead.
		 * */
	}
}
