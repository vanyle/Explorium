package com.vanyle.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.vanyle.blocks.Blocks;
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
	
	public static BufferedImage buttonbg = TextureGenerator.generateButtonTexture(Blocks.BlockTrunk.b().mainColor,32,16,(double)Explorium.GLOBAL_SEED/100d);
	public static BufferedImage pressedbg = TextureGenerator.generateButtonTexture(
			TextureGenerator.trick(Blocks.BlockTrunk.b().mainColor,-30)
	,32,16,(double)Explorium.GLOBAL_SEED/100d);
	
	public static Widget[] elements = new Widget[30];
	
	PlayerInputManager pim;
	
	public RenderMenu(PlayerInputManager pim) {
		this.pim = pim;
		
		w = new World(new ClassicGenerator(new Random().nextLong()));
		w.campos.y += 20;
		w.campos.overflow();
		
		setMainScreen();
	}
	private void setMainScreen() {
		elements = new Widget[30];
		elements[0] = new Button("EXPLORIUM",0,Window.HEIGHT/6,12);
		elements[0].centerX();
		
		elements[1] = new Button("Create a new world",0,(int)getButtonX(0,2),6);
		elements[1].centerX();
		
		elements[2] = new Button("Settings",0,(int)getButtonX(1,2),6);
		elements[2].centerX();
		
		elements[3] = new Button("Quit",0,(int)getButtonX(2,2),6);
		elements[3].centerX();
		
		
		pim.setEvents(new UserEvents() {
			@Override public void mouseUp(MouseEvent me) {}
			@Override public void mouseMove(MouseEvent me) {}
			@Override
			public void mouseDown(MouseEvent me) { // onclick
				for(int i = 0;i < elements.length;i++) {
					if(elements[i] != null) {
						if(elements[i].in(pim.mouseX, pim.mouseY) && i != 0) {
							if(elements[i] instanceof Button) {
								// Button action
								if(i == 1) { // Create new world button.
									Explorium.goGame();
								}
								if(i == 2) {
									setSettingsScreen();
								}
								if(i == 3) { // Quit button
									System.exit(0);
								}
							}
						}
					}
				}
			}
			@Override public void keyup(KeyEvent ke) {}
			@Override public void keydown(KeyEvent ke) {
				for(int i = 0;i < elements.length;i++) {
					if(elements[i] != null && elements[i] instanceof TextArea && ((TextArea)elements[i]).isFocus()) {
						if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE && elements[i].getText().length() > 0)
							elements[i].setText(elements[i].getText().substring(0, elements[i].getText().length()-1));
						else
							if(ke.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
								elements[i].setText(elements[i].getText() + ke.getKeyChar());
					}
				}
			}
		});
	}
	private void setSettingsScreen() {
		elements = new Widget[30];
		 
		elements[0] = new Button("Settings",0,Window.HEIGHT/6,12);
		elements[0].centerX();
		
		elements[1] = new Button("Back",100,100,6);		
		elements[2] = new Button("Sound",100,(int)getButtonX(1,3),6);
		((Button)elements[2]).pressable = false;
		elements[6] = new Slider(200 + elements[2].width, (int)getButtonX(1,3), 400,40);
		
		elements[3] = new Button("Music",100,(int)getButtonX(2,3),6);
		((Button)elements[3]).pressable = false;
		
		elements[7] = new Slider(200 + elements[3].width, (int)getButtonX(2,3), 400,40);
		
		//elements[4] = new Button("Keys",100,(int)getButtonX(3,3),6);
		
		elements[5] = new Button("Global Seed: "+Explorium.GLOBAL_SEED,100,(int)getButtonX(0, 3),6);
		((Button)elements[5]).pressable = false;
		
		pim.setEvents(new UserEvents() {
			@Override public void mouseUp(MouseEvent me) {}
			@Override public void mouseMove(MouseEvent me) {}
			@Override
			public void mouseDown(MouseEvent me) { // onclick
				for(int i = 0;i < elements.length;i++) {
					if(elements[i] != null) {
						if(elements[i].in(pim.mouseX, pim.mouseY) && i != 0) {
							if(elements[i] instanceof Button) {
								// Button action
								if(i == 1) { // Create new world button.
									setMainScreen();
								}
								if(i == 2) { // Sound button
									
								}
								if(i == 4) { // Keys button
									
								}
							}
						}
					}
				}
			}
			@Override public void keyup(KeyEvent ke) {}
			@Override public void keydown(KeyEvent ke) {
				for(int i = 0;i < elements.length;i++) {
					if(elements[i] != null && elements[i] instanceof TextArea && ((TextArea)elements[i]).isFocus()) {
						if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE && elements[i].getText().length() > 0)
							elements[i].setText(elements[i].getText().substring(0, elements[i].getText().length()-1));
						else
							if(ke.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
								elements[i].setText(elements[i].getText() + ke.getKeyChar());
					}
				}
			}
		});
	}
	private double getButtonX(int id,int total) {
		return Window.HEIGHT*.45 + (double)id/(double)(total+1) * (Window.HEIGHT*1./2);
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
		
		w.campos.converge(p, 0.3, 0, 0);
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
				if(data != Blocks.BlockAir.id()) {
					g.drawImage(
							Blocks.block(data).b().texture,
							
							(int)((i-exx)*bsize),(int)((j-exy)*bsize), // dx1,dy1
							(int)((i-exx)*bsize)+bsize,(int)((j-exy)*bsize)+bsize, // dx2,dy2
							
							p.getX()%16 * 16,p.getY()%16 * 16, // sx1,sy1
							p.getX()%16 * 16 + 16,p.getY()%16 * 16 + 16,null // sx2,sy2
					);
				}
				data = w.getData(p.clone());
				if(data != Blocks.BlockAir.id()) {
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
		
		
		g.setColor(Color.BLACK);
		g.fillRect(pim.mouseX - 5, pim.mouseY - 5, 10, 10);
		for(i = 0;i < elements.length;i++) {
			if(elements[i] != null) {
				if(elements[i] instanceof Button && ((Button)elements[i]).pressable) {
					Button b = (Button)elements[i];
					if(elements[i].in(pim.mouseX, pim.mouseY) && i != 0) {
						b.bg = pressedbg;
					}else {
						b.bg = buttonbg;
					}
				}else if(elements[i] instanceof Slider && elements[i].in(pim.mouseX, pim.mouseY) && pim.isMouseDown) {
					Slider s = (Slider)elements[i];
					s.setValue((pim.mouseX - s.x) / (double)s.width);
				}
				elements[i].draw(g);
			}
		}
		g.setColor(Color.BLACK);
		g.fillRect(pim.mouseX - 5, pim.mouseY - 5, 10, 10);
	}
	public static void centerText(String s,int h,Graphics g,int size,BufferedImage bi) {
		backgroundText(s,Window.WIDTH/2 - FontData.length(s, size)/2,h,size,bi,g,20); // 20 : padding
		FontData.drawString(s, Window.WIDTH/2 - FontData.length(s, size)/2, h, g, size);
	}
	public static void backgroundText(String s,int x,int y,int size,BufferedImage bi,Graphics g,int padding) {
		g.drawImage(bi,x-padding,y-padding,FontData.length(s, size)+padding*2,size*6+padding*2, null);
	}
}
