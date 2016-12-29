package com.bigbass.modr.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the list of each DimensionDataObject and should be the highest level parent of any stored DataObject's.
 * (There should not be any RecordDataObject's inside other DataObject's)
 */
public class RecordDataObject {
	
	public List<DimensionDataObject> dimensionList;
	
	public RecordDataObject(){
		dimensionList = new ArrayList<DimensionDataObject>();
	}
}
