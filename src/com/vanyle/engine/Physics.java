package com.vanyle.engine;

public class Physics {
	public static void amplyPhysics(Chunk c,Player p){
		Entity[] entities = c.getEntities();
		for(int i = 0;i < entities.length;i++){
			entities[i].AI(c,p);
		}
		p.AI(c,p);
	}
}
