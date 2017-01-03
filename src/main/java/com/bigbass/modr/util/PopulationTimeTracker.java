package com.bigbass.modr.util;

import java.util.ArrayList;
import java.util.List;

import com.bigbass.modr.data.PopTime;

public class PopulationTimeTracker {
	
	private static PopulationTimeTracker instance;
	
	public List<PopTime> timesList;
	
	private PopulationTimeTracker(){
		timesList = new ArrayList<PopTime>();
	}
	
	public static PopulationTimeTracker getInstance(){
		if(instance == null){
			instance = new PopulationTimeTracker();
		}
		
		return instance;
	}
}
