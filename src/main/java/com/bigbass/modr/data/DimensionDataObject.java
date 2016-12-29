package com.bigbass.modr.data;

import java.util.ArrayList;
import java.util.List;

public class DimensionDataObject {
	
	public String name;
	public int dimID;
	public int loadedChunks; //TODO Maybe change this to a List of chunks instead. Similar to the lists below.
	
	public List<PlayerDataObject> playerList; //list of ALL players
	public List<TileDataObject> tileList; // list of ALL TileEntities
	//TODO public List etc. //list of ALL entities
	
	
	public DimensionDataObject(){
		playerList = new ArrayList<PlayerDataObject>();
		tileList = new ArrayList<TileDataObject>();
	}
}
