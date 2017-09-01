package com.vanyle.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import com.vanyle.blocks.Blocks;
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
				if(Blocks.block(data) != Blocks.BlockAir) {
					g.drawImage(
							Blocks.block(data).b().texture,
							
							(int)((i-exx)*bsize),(int)((j-exy)*bsize), // dx1,dy1
							(int)((i-exx)*bsize)+bsize,(int)((j-exy)*bsize)+bsize, // dx2,dy2
							
							p.getX()%16 * 16,p.getY()%16 * 16, // sx1,sy1
							p.getX()%16 * 16 + 16,p.getY()%16 * 16 + 16,null // sx2,sy2
					);
				}
				data = w.getData(p);
				if(Blocks.block(data) != Blocks.BlockAir) {
					g.drawImage(
							Blocks.block(data).b().texture,
							
							(int)((i-exx)*bsize),(int)((j-exy)*bsize), // dx1,dy1
							(int)((i-exx)*bsize)+bsize,(int)((j-exy)*bsize)+bsize, // dx2,dy2
							
							p.getX()%16 * 16,p.getY()%16 * 16, // sx1,sy1
							p.getX()%16 * 16 + 16,p.getY()%16 * 16 + 16,null // sx2,sy2
					);
				}
			}
		}
		// Render entities
		Iterator<Entity> ite = w.entitylist.iterator();
		Entity entity;
		while(ite.hasNext()) {
			try {
				entity = ite.next();
				Position tempp = new Position(entity.p.x,entity.p.y,entity.p.getCX(),entity.p.getCY());
				tempp.substract(cpos);
	
				g.drawImage(entity.texture,
						(int)(tempp.x()*bsize),(int)(tempp.y()*bsize),(int)(bsize*entity.w),(int)(bsize*entity.h),null);
			}catch(ConcurrentModificationException ex) {
				
			}
		}
		
		g.setColor(Color.BLACK);
		if(Explorium.DEBUG_INFO)
			for(i = 0;i < debug.length;i++) {
				if(debug[i] != null) {
					FontData.drawString(debug[i], 10, DEBUG_FontSize*7*i + 10,g,DEBUG_FontSize);
				}
			}
	}

}
