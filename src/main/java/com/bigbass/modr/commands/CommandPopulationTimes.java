package com.bigbass.modr.commands;

import com.bigbass.modr.data.PopTime;
import com.bigbass.modr.util.ChatUtil;
import com.bigbass.modr.util.PopulationTimeTracker;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandPopulationTimes extends CommandBase {

	@Override
	public String getCommandName() {
		return "modrtimes";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/modrtimes";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		sender.addChatMessage(ChatUtil.getChatComponent("List of population times since server startup:"));
		for(PopTime time : PopulationTimeTracker.getInstance().timesList){
			sender.addChatMessage(ChatUtil.getChatComponent(time.dateTime + ": " + time.timeLag + "ms"));
		}
	}
}
