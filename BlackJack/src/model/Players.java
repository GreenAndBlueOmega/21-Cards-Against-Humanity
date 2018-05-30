package model;

public abstract class Players {

	
	public static String name;
	public static Players player;
	
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		
		Players.name = name;
	}
	public static Players getPlayer() {
		return player;
	}
	public static void setPlayer(Players player) {
		Players.player = player;
	}
	
	

	
	
}
