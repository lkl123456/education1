package com.adks.admin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.enumeration.Adks_enumeration;
import com.adks.dubbo.api.data.enumeration.Adks_enumeration_value;

public class EnumerationUtil {
	
	static Map<String,List<Adks_enumeration_value>> enValMap=null;
	
	static List<Adks_enumeration_value> enValList=new ArrayList<Adks_enumeration_value>();
	
	static List<Adks_enumeration> enList=new ArrayList<Adks_enumeration>();
	

	public static void init(List<Adks_enumeration_value> enValListTemp,List<Adks_enumeration> enListTemp){
		enValList=enValListTemp;
		enList=enListTemp;
		if(enValList!=null && enList!=null){
			enValMap=new HashMap<String,List<Adks_enumeration_value>>();
			List<Adks_enumeration_value> temp=null;
			for(Adks_enumeration en:enList){
				temp=new ArrayList<Adks_enumeration_value>();
				for(Adks_enumeration_value enVal:enValList){
					if(enVal.getEnId() == en.getEnId() || en.getEnId().equals(enVal.getEnId())){
						temp.add(enVal);
					}
				}
				enValMap.put(en.getEnDisplay(), temp);
			}
		}
	}
	
	public static Map<String, List<Adks_enumeration_value>> getEnValMap() {
		return enValMap;
	}

	public static void setEnValMap(Map<String, List<Adks_enumeration_value>> enValMap) {
		EnumerationUtil.enValMap = enValMap;
	}

	public static List<Adks_enumeration_value> getEnValList() {
		return enValList;
	}

	public static void setEnValList(List<Adks_enumeration_value> enValList) {
		EnumerationUtil.enValList = enValList;
	}

	public static List<Adks_enumeration> getEnList() {
		return enList;
	}

	public static void setEnList(List<Adks_enumeration> enList) {
		EnumerationUtil.enList = enList;
	}

	public static List<Adks_enumeration_value> getEnValByEnName(String name){
		if(enValMap!=null){
			return enValMap.get(name);
		}
		return null;
	}
}
