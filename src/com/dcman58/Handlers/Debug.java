package com.dcman58.Handlers;

import com.dcman58.Entity.Player;
import com.dcman58.TileMap.TileMap;

public final class Debug {
	
	public Debug() {
	}
	
	public static void Log(String message){
		System.out.println(message);
	}
	
	public static void LogPlayerPos(Player player) {
		Log("Player X:"+player.getx()+" Y:"+player.gety());
	}
	
}
