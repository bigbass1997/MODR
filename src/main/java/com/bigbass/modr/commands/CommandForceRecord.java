package com.bigbass.modr.commands;

import com.bigbass.modr.MODRMod;
import com.bigbass.modr.data.DataRecordHandler;
import com.bigbass.modr.mongo.MongoController;
import com.bigbass.modr.util.ChatUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandForceRecord extends CommandBase {

	@Override
	public String getCommandName() {
		return "modrforcerecord";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/modrforcerecord";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		sender.addChatMessage(ChatUtil.getChatComponent("Attempting to manually populate/send data record..."));
		try {
			MODRMod.log.info("Starting DataRecord population process..");
			sender.addChatMessage(ChatUtil.getChatComponent("Starting DataRecord population process.."));
			
			DataRecordHandler dataHand = new DataRecordHandler();
			
			dataHand.populateRecord();
			
			MongoController.getInstance().uploadRecord(dataHand);
		} catch(Exception ex) {
			MODRMod.log.error("An unexpected exception has occured when trying to populate data record!");
			sender.addChatMessage(ChatUtil.getChatComponent("An unexpected exception has occured when trying to populate data record! Check server log/console for stacktrace."));
			ex.printStackTrace();
		}
	}
}
