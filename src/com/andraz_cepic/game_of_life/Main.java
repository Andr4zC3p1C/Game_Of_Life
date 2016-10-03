package com.andraz_cepic.game_of_life;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Main implements Runnable{

	public static final String TITLE = "Conway's Game of Life";
	public static int WIDTH;
	public static int HEIGHT;
	public static int C_WIDTH;
	public static int C_HEIGHT;
	private static boolean Running = true;
	
	// graphics
	private Window win;
	private Canvas canvas;
	private JPanel panel;
	
	private Thread mainThread;
	private Game game;
	private static long skipTime;
	
	public Main(){
		start();
	}
	
	private void start(){
		// GETTING SCREEN RESOLUTION
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		WIDTH = gd.getDisplayMode().getWidth();
		HEIGHT = gd.getDisplayMode().getHeight();
		
		// initializing the graphics
		canvas = new Canvas();
		C_WIDTH = WIDTH;
		C_HEIGHT = HEIGHT - 105;
		canvas.setPreferredSize(new Dimension(C_WIDTH, C_HEIGHT));
		
		// input
		Input in = new Input();
		canvas.addKeyListener(in);
		canvas.addMouseListener(in);
		canvas.addMouseMotionListener(in);
		canvas.addMouseWheelListener(in);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(canvas);
		
		game = new Game();
		panel.add(game.getPanel());
		
		win = new Window(TITLE);
		win.add(panel);
		win.setVisible();
		
		
		mainThread = new Thread(this, "main_thread");
		mainThread.start();
	}
	
	public void run(){
		long updateTimer = System.nanoTime();
		int maxLoops = 10;
		skipTime = 1000000000 / 60;
		int loops;
		while(Running){
			loops = 0;
			while(System.nanoTime() > updateTimer && loops < maxLoops){
				updateTimer += skipTime;
				loops++;
				update();
			}
			
			render();
		}
	}
	
	private void update(){
		canvas.requestFocus();
		game.update();
		Input.update();
	}
	
	private void render(){
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null){
			canvas.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, C_WIDTH, C_HEIGHT);
		
		game.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void setRunning(boolean running){
		Running = running;
	}
	
	public static void main(String[] args){
		new Main();
	}	
}
