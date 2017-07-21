package com.vanyle.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import com.vanyle.data.BlockData;
import com.vanyle.data.FontData;
import com.vanyle.life.Entity;
import com.vanyle.main.Explorium;
import com.vanyle.physics.Chunk;
import com.vanyle.physics.Position;
import com.vanyle.physics.World;

public class Render implements Renderer{

	private World w;
	
	public static int wbcount = Chunk.CSIZE; // 1 chunk displayable in the screen, increase for debug
	public static int hbcount = (int)(wbcount*((float)Window.HEIGHT/Window.WIDTH)); // about wbcount/1.7778
	
	public static String[] debug = new String[10];
	
	public final static int DEBUG_FontSize = 4;
	public final static Color skycolor = new Color(0.0195f, 0.6015625f, 0.953125f);
	
	public final int bsize = Window.WIDTH / wbcount;
	
	static {
		debug[0] = "Greetings young explorer !";
		if(Explorium.WIDE_FOV) {
			wbcount *= 2;
			hbcount = (int)(wbcount*((float)Window.HEIGHT/Window.WIDTH));
		}
	}
	
	public Render(World w) {
		this.w = w;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(skycolor);
		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		int i,j,data;
		double exx,exy;
		Position p;
		Position cpos = w.campos.clone(); // copy campos for rendering;
		w.loadChunk(cpos);
		
		for(i = 0;i < wbcount+1;i++) {
			for(j = 0;j < hbcount+1;j++) {
				// Get block color
				p = new Position(i,j,0,0);
				exx = (cpos.x - Math.floor(cpos.x));
				exy = (cpos.y - Math.floor(cpos.y));
				p.add(cpos);
				
				data = w.getBackgroundData(p.clone());
				if(data != BlockData.ID_AIR) {
					g.setColor(BlockData.toColor(data));
					g.fillRect((int)((i-exx)*bsize),(int)((j-exy)*bsize), bsize, bsize);
				}
				data = w.getData(p);
				if(data != BlockData.ID_AIR) {
					g.setColor(BlockData.toColor(data));
					g.fillRect((int)((i-exx)*bsize),(int)((j-exy)*bsize), bsize, bsize);
				}
			}
		}
		// Render entities
		Iterator<Entity> ite = w.entitylist.iterator();
		Entity entity;
		while(ite.hasNext()) {
			entity = ite.next();
			Position tempp = new Position(entity.p.x,entity.p.y,entity.p.getCX(),entity.p.getCY());
			tempp.substract(cpos);
			//System.out.println(entity.w);
			g.setColor(entity.c);
			g.fillRect((int)(tempp.x()*bsize),(int)(tempp.y()*bsize),(int)(bsize*entity.w),(int)(bsize*entity.h));
		}
		
		g.setColor(Color.BLACK);
		
		for(i = 0;i < debug.length;i++) {
			if(debug[i] != null) {
				FontData.drawString(debug[i], 10, DEBUG_FontSize*7*i + 10,g,DEBUG_FontSize);
			}
		}
	}

}
