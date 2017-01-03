package com.bigbass.modr.data;

public class EntityItemDataObject extends EntityDataObject {
	
	public ItemStackDataObject itemStack;
	
	public EntityItemDataObject(){
		super();
		itemStack = new ItemStackDataObject();
	}
}
