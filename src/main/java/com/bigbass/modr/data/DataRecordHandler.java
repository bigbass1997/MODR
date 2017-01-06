package com.bigbass.modr.data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.bigbass.modr.MODRMod;
import com.bigbass.modr.util.PopulationTimeTracker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class DataRecordHandler {
	
	private RecordDataObject record;
	
	private boolean isPopulated = false;
	
	public DataRecordHandler(){
		record = new RecordDataObject();
	}
	
	/**
	 * <p>Populate the record that will be uploaded to the database.</p>
	 * 
	 * <p>
	 * All data is stored in a tree/hierarchy of DataObject's. A RecordDataObject is the highest
	 * parent in this tree. The data is stored this way for easy of converting data to the proper formats,
	 * for both file output and uploading to the database. In both cases the RecordDataObject object can
	 * be easily converted into a JSON format which is can used by both the MongoController and when writing
	 * to a file.
	 * </p>
	 * 
	 * <p>
	 * Also adding or modifying what data is included is made simple by this data structure. Simply create/edit
	 * the appropriate variables in the DataObject classes and assign them a value in this method. Therefore
	 * all the data assignment and creation is in a single location in code.
	 * </p>
	 */
	public void populateRecord(){
		// Attempting to populate again may cause fragmented data for the record, and so is not allowed.
		if(isPopulated){
			return;
		}

		long startTime = System.currentTimeMillis();
		
		for(WorldServer world : FMLCommonHandler.instance().getMinecraftServerInstance().worldServers){
			DimensionDataObject dimData = new DimensionDataObject();
			dimData.name = world.getWorldInfo().getWorldName();
			dimData.dimID = world.provider.dimensionId;
			
			for(Object obj : world.theChunkProviderServer.loadedChunks){
				if(obj instanceof Chunk){
					Chunk chunk = (Chunk) obj;
					ChunkDataObject chunkData = new ChunkDataObject();
					
					chunkData.x = chunk.xPosition;
					chunkData.z = chunk.zPosition;
					
					chunkData.entities = 0;
					for(List<?> list : chunk.entityLists){
						chunkData.entities += list.size();
					}
					
					chunkData.tiles = chunk.chunkTileEntityMap.size();
					
					dimData.loadedChunkList.add(chunkData);
				}
			}
			
			for(Object obj : world.playerEntities){
				if(obj instanceof EntityPlayer){
					EntityPlayer player = (EntityPlayer) obj;
					PlayerDataObject playerData = new PlayerDataObject();
					
					playerData.username = player.getDisplayName();
					playerData.uuid = player.getUniqueID();
					playerData.x = filterInfNaN(player.posX);
					playerData.y = filterInfNaN(player.posY);
					playerData.z = filterInfNaN(player.posZ);
					
					dimData.playerList.add(playerData);
				}
			}
			
			for(Object obj : world.loadedTileEntityList){
				if(obj instanceof TileEntity){
					TileEntity tile = (TileEntity) obj;
					TileDataObject tileData = new TileDataObject();
					
					tileData.name = tile.getClass().toString();
					tileData.x = tile.xCoord;
					tileData.y = tile.yCoord;
					tileData.z = tile.zCoord;
					
					dimData.tileList.add(tileData);
				}
			}
			
			for(Object obj : world.loadedEntityList){
				if(obj instanceof EntityItem){
					EntityItem entityItem = (EntityItem) obj;
					ItemStack stack = entityItem.getEntityItem();
					 
					EntityItemDataObject entityItemData = new EntityItemDataObject();
					
					entityItemData.name = entityItem.getClass().toString();
					entityItemData.x = filterInfNaN(entityItem.posX);
					entityItemData.y = filterInfNaN(entityItem.posY);
					entityItemData.z = filterInfNaN(entityItem.posZ);
					
					entityItemData.itemStack.unlocalizedName = stack.getItem().getUnlocalizedName();
					entityItemData.itemStack.localizedName = stack.getDisplayName();
					entityItemData.itemStack.stackSize = stack.stackSize;
					entityItemData.itemStack.maxStackSize = stack.getMaxStackSize();

					dimData.entityList.add(entityItemData);
				}else if(obj instanceof Entity){
					Entity entity = (Entity) obj;
					EntityDataObject entityData = new EntityDataObject();
					
					entityData.name = entity.getClass().toString();
					entityData.x = filterInfNaN(entity.posX);
					entityData.y = filterInfNaN(entity.posY);
					entityData.z = filterInfNaN(entity.posZ);
					
					dimData.entityList.add(entityData);
				}
			}
			
			record.dimensionList.add(dimData);
		}
		
		//Mark the record with a date and time of UTC timezone.
		
		record.time.dateTime = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss"));
		record.time.timeLag = (System.currentTimeMillis() - startTime);
		
		isPopulated = true;
		
		PopulationTimeTracker.getInstance().timesList.add(record.time);
		
		MODRMod.log.info("DataRecord populated in " + record.time.timeLag + "ms");
	}
	
	private double filterInfNaN(double val){
		if(Double.isFinite(val)){
			return val;
		} else {
			return Double.MAX_VALUE - 1;
		}
	}
	
	public String formatToJson(){
		Gson gson = (new GsonBuilder()).create();
		try {
			return gson.toJson(record);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String formatToJsonPretty(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			return gson.toJson(record);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
