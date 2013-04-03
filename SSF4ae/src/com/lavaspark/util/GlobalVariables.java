package com.lavaspark.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import com.lavaspark.adapter.Move_attr;

import android.app.Application;

public class GlobalVariables extends Application {

	public ArrayList<String> nameList ;
	public ArrayList<String> frameKeyList ;
	public List<HashMap<String, String>> allFrameList = new ArrayList<HashMap<String,String>>();
	public ArrayList<Move_attr> arraymoveList = new ArrayList<Move_attr>();
	public HashMap<String, ArrayList<String>>  allcharacters  = new HashMap<String, ArrayList<String>>();
	public ArrayList<String>  deliverycharacter;
	public String[]  character_zhaoshi;
	public JSONArray jsonarray = new JSONArray();
	
	
//	public ArrayList<HashMap<String, ArrayList<String>>>
	
	public JSONArray getJsonarray() {
		return jsonarray;
	}
	public void setJsonarray(JSONArray jsonarray) {
		this.jsonarray = jsonarray;
	}
	public String[] getCharacter_zhaoshi() {
		return character_zhaoshi;
	}
	public void setCharacter_zhaoshi(String[] character_zhaoshi) {
		this.character_zhaoshi = character_zhaoshi;
	}
	public ArrayList<String> getDeliverycharacter() {
		return deliverycharacter;
	}
	public void setDeliverycharacter(ArrayList<String> deliverycharacter) {
		this.deliverycharacter = deliverycharacter;
	}
	public HashMap<String, ArrayList<String>> getAllcharacters() {
		return allcharacters;
	}
	public void setAllcharacters(
			HashMap<String, ArrayList<String>> allcharacters) {
		this.allcharacters = allcharacters;
	}
	public String[] characters;
	
	public String[] getcharacters() {
		return characters;
	}
	public void setcharacters(String[] gcharacters) {
		characters = gcharacters;
	}
	public ArrayList<String> getNameList() {
		return nameList;
	}
	public void setNameList(ArrayList<String> nameList) {
		this.nameList = nameList;
	}
	public ArrayList<String> getFrameKeyList() {
		return frameKeyList;
	}
	public void setFrameKeyList(ArrayList<String> frameKeyList) {
		this.frameKeyList = frameKeyList;
	}
	public List<HashMap<String, String>> getAllFrameList() {
		return allFrameList;
	}
	public void setAllFrameList(List<HashMap<String, String>> allFrameList) {
		this.allFrameList = allFrameList;
	}
	public ArrayList<Move_attr> getArraymoveList() {
		return arraymoveList;
	}
	public void setArraymoveList(ArrayList<Move_attr> arraymoveList) {
		this.arraymoveList = arraymoveList;
	}
	
	
}
