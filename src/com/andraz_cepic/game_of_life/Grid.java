package com.andraz_cepic.game_of_life;

import java.awt.Color;
import java.awt.Graphics;

public class Grid {

	public static final int CELL_SIZE = 7;
	public static final int LINE_WIDTH = 1;
	
	private int width, height;
	private boolean[][] grid;
	private boolean[][] change_grid;
	private boolean[][] tmpGrid;
	private int generationCounter = 0;
	
	public Grid(int width, int height){
		this.width = width / (CELL_SIZE + LINE_WIDTH);
		this.height = height / (CELL_SIZE + LINE_WIDTH);
		grid = new boolean[this.height][this.width];
		change_grid = new boolean[this.height][this.width];
		tmpGrid = new boolean[this.height][this.width];
		
		boolean alive = true;
		for(int i = this.height / 2 - 26; i < this.height / 2 + 25; i++){
			for(int j=this.width / 2 - 26; j < this.width / 2 + 25; j++){
				grid[i][j] = alive;
				alive = !alive;
			}
		}
	}
	
	
	public void update(){
		generationCounter++;
		int lifeCounter;
		int nX, nY;
		for(int y=0; y < height; y++){
			for(int x=0; x < width; x++){
				lifeCounter = 0;
				
				// 1. neighbor
				nX = x % width;
				nY = (y - 1) % height;
				if(nX < 0){
					nX = width + nX;
				}
				if(nY < 0){
					nY = height + nY;
				}
				if(grid[nY][nX])
					lifeCounter++;
				
				// 2. neighbor
				nX = (x + 1) % width;
				nY = (y - 1) % height;
				if(nX < 0){
					nX = width + nX;
				}
				if(nY < 0){
					nY = height + nY;
				}
				if(grid[nY][nX])
					lifeCounter++;
				
				// 3. neighbor
				nX = (x + 1) % width;
				nY = y % height;
				if(nX < 0){
					nX = width + nX;
				}
				if(nY < 0){
					nY = height + nY;
				}
				if(grid[nY][nX])
					lifeCounter++;
				
				// 4. neighbor
				nX = (x + 1) % width;
				nY = (y + 1) % height;
				if(nX < 0){
					nX = width + nX;
				}
				if(nY < 0){
					nY = height + nY;
				}
				if(grid[nY][nX])
					lifeCounter++;
				
				// 5. neighbor
				nX = x % width;
				nY = (y + 1) % height;
				if(nX < 0){
					nX = width + nX;
				}
				if(nY < 0){
					nY = height + nY;
				}
				if(grid[nY][nX])
					lifeCounter++;
				
				// 6. neighbor
				nX = (x - 1) % width;
				nY = (y + 1) % height;
				if(nX < 0){
					nX = width + nX;
				}
				if(nY < 0){
					nY = height + nY;
				}
				if(grid[nY][nX])
					lifeCounter++;
				
				// 7. neighbor
				nX = (x - 1) % width;
				nY = y % height;
				if(nX < 0){
					nX = width + nX;
				}
				if(nY < 0){
					nY = height + nY;
				}
				if(grid[nY][nX])
					lifeCounter++;
				
				// 8. neighbor
				nX = (x - 1) % width;
				nY = (y - 1) % height;
				if(nX < 0){
					nX = width + nX;
				}
				if(nY < 0){
					nY = height + nY;
				}
				if(grid[nY][nX])
					lifeCounter++;
				
				if(grid[y][x]){
					if(lifeCounter < 2)
						tmpGrid[y][x] = false;
					else if(lifeCounter < 4)
						tmpGrid[y][x] = true;
					else
						tmpGrid[y][x] = false;
				}else{
					if(lifeCounter == 3)
						tmpGrid[y][x] = true;
				}
			}
		}
		
		for(int i=0; i < height; i++)
			for(int j=0; j < width; j++)
				grid[i][j] = tmpGrid[i][j];
	}
	
	public void render(Graphics g){
		// rendering the grid
		g.setColor(Color.GRAY);
		for(int y = 0; y <= height; y++){
			g.fillRect(0, (LINE_WIDTH + CELL_SIZE) * y, Main.C_WIDTH, 1);
		}
		
		for(int x = 0; x <= width; x++){
			g.fillRect((LINE_WIDTH + CELL_SIZE) * x, 0, 1, Main.C_HEIGHT);
		}
		
		// rendering the cells
		g.setColor(Color.DARK_GRAY);
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(grid[y][x])
					g.fillRect(LINE_WIDTH + (LINE_WIDTH + CELL_SIZE) * x, LINE_WIDTH + (LINE_WIDTH + CELL_SIZE) * y, CELL_SIZE, CELL_SIZE);
			}
		}
	}
	
	public void clear(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				grid[y][x] = false;
				tmpGrid[y][x] = false;
				change_grid[y][x] = false;
			}
		}
		
		generationCounter = 0;
	}
	
	public boolean getCell(int x, int y){
		return grid[y][x];
	}
	
	public void setCell(boolean state, int x, int y){
		grid[y][x] = state;
	}
	
	public void change(int x, int y){
		if(y < height && !change_grid[y][x]){
			grid[y][x] = !grid[y][x];
			change_grid[y][x] = true;
		}
	}
	
	public void resetChange(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				change_grid[y][x] = false;
			}
		}
	}
	
	public int getGencounter(){
		return generationCounter;
	}
	
}
