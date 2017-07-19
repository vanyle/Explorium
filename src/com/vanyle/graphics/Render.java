package com.vanyle.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

import com.vanyle.data.BlockData;
import com.vanyle.life.Entity;
import com.vanyle.main.Explorium2;
import com.vanyle.physics.Chunk;
import com.vanyle.physics.Position;
import com.vanyle.physics.World;

public class Render implements Renderer{

	private World w;
	
	public Position campos = new Position(0,0,0,1);
	
	public static int wbcount = Chunk.CSIZE; // 1 chunk displayable in the screen, increase for debug
	public static int hbcount = (int)(wbcount*((float)Window.HEIGHT/Window.WIDTH)); // about wbcount/1.7778
	
	public static String[] debug = new String[5];
	
	public final static int fontsize = 20;
	public final static Font f = new Font("Arial", Font.PLAIN, fontsize);
	public final static Color skycolor = new Color(0.0195f, 0.6015625f, 0.953125f);
	
	public final int bsize = Window.WIDTH / wbcount;
	
	static {
		if(Explorium2.WIDE_FOV) {
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
		Position cpos = campos.clone(); // copy campos for rendering;
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
		g.setFont(f);

		for(i = 0;i < debug.length;i++) {
			if(debug[i] != null) {
				g.drawString(debug[i], 10, (fontsize+10)*(i+1));
			}
		}
	}

}
