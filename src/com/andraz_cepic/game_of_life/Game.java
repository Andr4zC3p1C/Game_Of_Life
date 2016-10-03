package com.andraz_cepic.game_of_life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Game {
	public static final int MIN_GPM = 1;
	public static final int START_GPM = 10;
	public static final int MAX_GPM = 40;
	
	enum State{
		PLAY,
		PAUSE,
		STEP
	}
	
	private Grid grid;
	private State state;
	private long timer;
	private int GPM = START_GPM;
	private float cameraX, cameraY;
	
	// graphics
	private JPanel panel;
	private JButton quitButton;
	private JButton startButton;
	private JButton pauseButton;
	private JButton stepButton;
	private JButton clearButton;
	private JSlider upsSlider;
	private JLabel sliderLabel;
	private JLabel genLabel;
	
	public Game(){
		grid = new Grid(Main.C_WIDTH, Main.C_HEIGHT);
		
		initGraphics();
		
		timer = System.nanoTime();
		cameraX = 0;
		cameraY = 0;
		
		state = State.PAUSE;
	}
	
	public void update(){
		// the game of life loop
		if(System.nanoTime() - timer >= 1000000000 / GPM){
			timer = System.nanoTime();
			
			if(state != State.PAUSE){
				grid.update();
				
				if(state == State.STEP)
					state = State.PAUSE;
			}
		}
		
		gridUpdate();
	}
	
	public void render(Graphics g){
		grid.render(g);
	}
	
	private void initGraphics(){
		// graphics
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT - Main.C_HEIGHT));
		
		upsSlider = new JSlider(MIN_GPM, MAX_GPM, START_GPM);
		upsSlider.setMajorTickSpacing(1);
		upsSlider.setPaintTicks(true);
		sliderLabel = new JLabel("Generations per second: " + START_GPM, SwingConstants.CENTER);
		
		genLabel = new JLabel("Generations passed: " + grid.getGencounter());
		
		quitButton = new JButton("QUIT");
		quitButton.setFocusPainted(false);;
		quitButton.setBackground(Color.GRAY);
		quitButton.setForeground(Color.WHITE);
		
		startButton = new JButton("START");
		startButton.setFocusPainted(false);
		startButton.setBackground(Color.GRAY);
		startButton.setForeground(Color.WHITE);
		
		pauseButton = new JButton("PAUSE");
		pauseButton.setFocusPainted(false);;
		pauseButton.setBackground(Color.GRAY);
		pauseButton.setForeground(Color.WHITE);
		
		stepButton = new JButton("STEP");
		stepButton.setFocusPainted(false);
		stepButton.setBackground(Color.GRAY);
		stepButton.setForeground(Color.WHITE);
		
		clearButton = new JButton("CLEAR");
		clearButton.setFocusPainted(false);;
		clearButton.setBackground(Color.GRAY);
		clearButton.setForeground(Color.WHITE);
		
		setEventHandling();
		
		JPanel sliderPanel = new JPanel();
		sliderPanel.setPreferredSize(new Dimension(200, 30));
		sliderPanel.add(upsSlider);
		
		JPanel labelPanelGenCounter = new JPanel();
		labelPanelGenCounter.setLayout(new BoxLayout(labelPanelGenCounter, BoxLayout.X_AXIS));
		labelPanelGenCounter.add(Box.createHorizontalGlue());
		labelPanelGenCounter.add(genLabel);
		labelPanelGenCounter.add(Box.createHorizontalGlue());
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		labelPanel.add(Box.createHorizontalGlue());
		labelPanel.add(sliderLabel);
		labelPanel.add(Box.createHorizontalGlue());
		
		JPanel vPanel = new JPanel();
		vPanel.add(startButton);
		vPanel.add(pauseButton);
		vPanel.add(stepButton);
		vPanel.add(clearButton);
		vPanel.add(quitButton);
		panel.add(vPanel);
		panel.add(labelPanelGenCounter);
		panel.add(labelPanel);
		panel.add(sliderPanel);
	}
	
	private void setEventHandling(){
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = State.PLAY;
			}
		});
		
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = State.PAUSE;
			}
		});
		
		stepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = State.STEP;
				timer = 1000000000 / GPM;
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.clear();
			}
		});
		
		upsSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int sliderValue = (int)upsSlider.getValue();
				sliderLabel.setText("Generations per second: " + sliderValue);
				GPM = sliderValue;
			}
		});
	}
	
	private void gridUpdate(){
		// input
		if(Input.buttonDown(MouseEvent.BUTTON1)){
			grid.change(Input.getMouseX() / (Grid.CELL_SIZE + Grid.LINE_WIDTH), Input.getMouseY() / (Grid.CELL_SIZE + Grid.LINE_WIDTH));
		}else{
			grid.resetChange();
		}
		
		genLabel.setText("Generations passed: " + grid.getGencounter());
	}
	
	public JPanel getPanel(){
		return panel;
	}
}
