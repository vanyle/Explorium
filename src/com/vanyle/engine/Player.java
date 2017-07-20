package com.vanyle.engine;

public class Player extends Humanoid{
	protected byte AI = Entity.AI_control;
	
	public void jump(){
		velocityY = -1.2;
	}
	public void moveRight(){
		velocityX = -0.6;
	}
	public void moveLeft(){
		velocityX = 0.6;
	}
}
