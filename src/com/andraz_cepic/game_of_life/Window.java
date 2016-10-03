package com.andraz_cepic.game_of_life;

import java.awt.Component;

import javax.swing.JFrame;

public class Window {

	private JFrame frame;
	
	public Window(String title){
		frame = new JFrame(title);
		frame.setResizable(false);
		frame.setUndecorated(true);
	}
	
	public void add(Component c){
		frame.add(c);
	}
	
	public void setVisible(){
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}
	
}
