package com.vanyle.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.vanyle.data.BlockData;
import com.vanyle.data.FontData;
import com.vanyle.graphics.PlayerInputManager;
import com.vanyle.graphics.Renderer;
import com.vanyle.graphics.UserEvents;
import com.vanyle.graphics.Window;
import com.vanyle.main.Explorium;
import com.vanyle.physics.Chunk;
import com.vanyle.physics.Position;
import com.vanyle.physics.World;
import com.vanyle.procedural.ClassicGenerator;
import com.vanyle.procedural.TextureGenerator;

public class RenderMenu implements Renderer{
	
	private World w;
	public static int wbcount = Chunk.CSIZE; // 1 chunk displayable in the screen, increase for debug
	public static int hbcount = (int)(wbcount*((float)Window.HEIGHT/Window.WIDTH)); // about wbcount/1.7778
	
	public static String[] debug = new String[10];
	
	public final static int DEBUG_FontSize = 4;
	public final static Color skycolor = new Color(0.0195f, 0.6015625f, 0.953125f);
	public final int bsize = Window.WIDTH / wbcount;
	
	public static BufferedImage buttonbg = TextureGenerator.blockIdToImage(BlockData.ID_TRUNK, 20, 10, 0.1);
	public static BufferedImage pressedbg = TextureGenerator.blockIdToImage(BlockData.ID_LADDER, 20, 10, 0.1);
	
	public static Button[] buttons = new Button[3];
	
	PlayerInputManager pim;
	
	public RenderMenu(PlayerInputManager pim) {
		this.pim = pim;
		
		w = new World(new ClassicGenerator(new Random().nextLong()));
		w.campos.y += 20;
		w.campos.overflow();
		buttons[0] = new Button("EXPLORIUM",0,Window.HEIGHT/4,12);
		buttons[0].centerX();
		
		buttons[1] = new Button("play",0,Window.HEIGHT/2,10);
		buttons[1].centerX();
		
		buttons[2] = new Button("settings",0,Window.HEIGHT*3/4,10);
		buttons[2].centerX();
		
		pim.setEvents(new UserEvents() {
			@Override public void mouseUp(MouseEvent me) {}
			@Override public void mouseMove(MouseEvent me) {}
			@Override
			public void mouseDown(MouseEvent me) { // onclick
				for(int i = 0;i < buttons.length;i++) {
					if(buttons[i] != null) {
						if(buttons[i].inButton(pim.mouseX, pim.mouseY) && i != 0) {
							// Button action
							if(i == 1) { // Play button.
								Explorium.goGame();
							}
						}
					}
				}
			}
			@Override public void keyup(KeyEvent ke) {}
			@Override public void keydown(KeyEvent ke) {}
		});
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(skycolor);
		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		int i,j,data;
		double exx,exy;
		Position p = new Position(w.campos.x+1
				,((ClassicGenerator)w.getGenerator()).getHeight(w.campos.x + wbcount*w.campos.cx - wbcount/2f) + hbcount + 10,
				w.campos.getCX(),
				0
				);
		p.overflow();
		
		w.campos.converge(p, 0.1, 0, 0);
		Position cpos = w.campos.clone(); // copy campos for rendering;
		cpos.overflow();
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
				data = w.getData(p.clone());
				if(data != BlockData.ID_AIR) {
					g.setColor(BlockData.toColor(data));
					//g.fillRect((int)((i-exx)*bsize),(int)((j-exy)*bsize), bsize, bsize);
					g.drawImage(
							BlockData.toTexture(data,p.getX(), p.getY()),
							
							(int)((i-exx)*bsize),(int)((j-exy)*bsize), // dx1,dy1
							(int)((i-exx)*bsize)+bsize,(int)((j-exy)*bsize)+bsize, // dx2,dy2
							
							0,0, // sx1,sy1
							16,16,null // sx2,sy2
					);
				}
			}
		}
		
		
		g.setColor(Color.BLACK);
		for(i = 0;i < buttons.length;i++) {
			if(buttons[i] != null) {
				if(buttons[i].inButton(pim.mouseX, pim.mouseY) && i != 0) {
					buttons[i].bg = pressedbg;
				}else {
					buttons[i].bg = buttonbg;
				}
				buttons[i].draw(g);
			}
		}
	}
	public static void centerText(String s,int h,Graphics g,int size,BufferedImage bi) {
		backgroundText(s,Window.WIDTH/2 - FontData.length(s, size)/2,h,size,bi,g,20); // 20 : padding
		FontData.drawString(s, Window.WIDTH/2 - FontData.length(s, size)/2, h, g, size);
	}
	public static void backgroundText(String s,int x,int y,int size,BufferedImage bi,Graphics g,int padding) {
		g.drawImage(bi,x-padding,y-padding,FontData.length(s, size)+padding*2,size*6+padding*2, null);
	}
}
