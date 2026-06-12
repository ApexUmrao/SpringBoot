package com.newgen.iforms.user.cache;


import java.util.HashMap;
import java.util.List;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.LOS_EG;
import com.newgen.omni.wf.util.excp.NGException;


public class RLOSMappingCache {

	
	private HashMap<String, String> callAPIUrlMap = new HashMap<String, String>();
	private static RLOSMappingCache instance = new RLOSMappingCache();

	public static RLOSMappingCache getInstance() {
		return instance;
	}
	
	public HashMap<String, String> getAPIUrlMasterMap(IFormReference ifr) {
		LOS_EG.mLogger.info("[getAPIUrlMasterMap]" + "call Name is ");
		if (callAPIUrlMap.isEmpty()) {
			LOS_EG.mLogger.info("[getAPIUrlMasterMap]" + "call Name is empty ");
			try {
				callAPIUrlMap = createAPIUrlCallMap(ifr);
				} catch (NGException e) {
				
				LOS_EG.mLogger.info("[getAPIUrlMasterMap]" + " errror " + e);
			}
		}
		return callAPIUrlMap;
	}
	

	
private HashMap<String,String> createAPIUrlCallMap(IFormReference ifr) throws NGException {
	
		LOS_EG.mLogger.info("[createAPIUrlCallMap]" + " inside createAPIUrlCallMap once ");
		try{
			callAPIUrlMap = new HashMap<String,String>();
			String sQuery = "SELECT FUNCTION_ID, IS_NEW_API, API_URL FROM RLOS_API_URL_MASTER";
			
			List < List < String >> resultList = ifr.getDataFromDB(sQuery);

			 for (List<String> row : resultList) {

			        if (row == null || row.size() < 3) {
			            continue;
			        }

			        String functionID = row.get(0);
			        String isNew = row.get(1);
			        String apiURL = row.get(2);

			        String key = functionID + "#" + isNew;
			        String value = apiURL;

			        LOS_EG.mLogger.info("[createAPIUrlCallMap] key is " + key);
			        LOS_EG.mLogger.info("[createAPIUrlCallMap] value is " + value);

			        if(!callAPIUrlMap.containsKey(key)){
			        	callAPIUrlMap.put(key, value);
			        }
			  }
		} catch (Exception e) {	
			LOS_EG.mLogger.error("[createAPIUrlCallMap]" + " errror " + e);
		}
		return callAPIUrlMap;
 }

}
