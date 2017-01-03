package com.bigbass.modr.util;

import net.minecraft.util.ChatComponentText;

public class ChatUtil {
	
	private ChatUtil(){}
	
	public static ChatComponentText getChatComponent(String s){
		return new ChatComponentText(s);
	}
}
