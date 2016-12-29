package com.bigbass.modr.data;

import java.util.ArrayList;
import java.util.List;

public class DimensionDataObject {
	
	public String name;
	public int dimID;
	public int loadedChunks; //TODO Maybe change this to a List of chunks instead. Similar to the lists below.
	
	public List<ChunkDataObject> loadedChunkList; //list of ALL loaded Chunks
	public List<PlayerDataObject> playerList; //list of ALL Players
	public List<TileDataObject> tileList; //list of ALL TileEntities
	public List<EntityDataObject> entityList; //list of ALL Entities 
	
	
	public DimensionDataObject(){
		loadedChunkList = new ArrayList<ChunkDataObject>();
		playerList = new ArrayList<PlayerDataObject>();
		tileList = new ArrayList<TileDataObject>();
		entityList = new ArrayList<EntityDataObject>();
	}
}
