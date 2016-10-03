package com.andraz_cepic.game_of_life;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener{

	// KEYBOARD
	private static boolean keys[] = new boolean[1024];
	private static boolean keys_p[] = new boolean[1024];
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		keys_p[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	
	public static boolean keyDown(int key){
		return keys[key];
	}
	
	public static boolean keyPressed(int key){
		boolean result = keys[key] && !keys_p[key];
		if(result)
			keys_p[key] = true;
		
		return result;
	}
	
	// MOUSE
	private static int mouseX, mouseY;
	private static int mouseWheel;
	private static boolean buttons[] = new boolean[256];
	private static boolean buttons_p[] = new boolean[256];
	
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
		buttons_p[e.getButton()] = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheel = e.getWheelRotation();
	}
	
	public static void update(){
		mouseWheel = 0;
	}
	
	public static boolean buttonDown(int buttonCode){
		return buttons[buttonCode];
	}
	
	public static boolean buttonPressed(int buttonCode){
		boolean result = buttons[buttonCode] && !buttons_p[buttonCode];
		
		if(result)
			buttons_p[buttonCode] = true;
		
		return result;
	}
	
	public static int getMouseX(){
		return mouseX;
	}
	
	public static int getMouseY(){
		return mouseY;
	}

	public static int getMouseWheelRotation(){
		return mouseWheel;
	}
	
}
