import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Timer;

public class LifeModel implements ActionListener
{

	/*
	 *  This is the Model component.
	 */

	private static int SIZE = 60;
	private LifeCell[][] grid;
	
	LifeView myView;
	Timer timer;

	/** Construct a new model using a particular file */
	
	
	public LifeModel(LifeView view, String fileName) throws IOException
	{       
		int r, c;
		grid = new LifeCell[SIZE][SIZE];
		for ( r = 0; r < SIZE; r++ )
			for ( c = 0; c < SIZE; c++ )
				grid[r][c] = new LifeCell();

		if ( fileName == null ) //use random population
		{                                           
			for ( r = 0; r < SIZE; r++ )
			{
				for ( c = 0; c < SIZE; c++ )
				{
					if ( Math.random() > 0.85) //15% chance of a cell starting alive
						grid[r][c].setAliveNow(true);
				}
			}
		}
		else
		{                 
			Scanner input = new Scanner(new File(fileName));
			int numInitialCells = input.nextInt();
			for (int count=0; count<numInitialCells; count++)
			{
				r = input.nextInt();
				c = input.nextInt();
				grid[r][c].setAliveNow(true);
			}
			input.close();
		}

		myView = view;
		myView.updateView(grid);

	}
	public LifeCell[][] getGrid() {
		return grid;
	}

	/** Constructor a randomized model */
	public LifeModel(LifeView view) throws IOException
	{
		this(view, null);
	}

	/** pause the simulation (the pause button in the GUI */
	public void pause()
	{
		timer.stop();
	}
	
	public void reset(LifeView view, String fileName) throws FileNotFoundException
	{
		int r, c;
		grid = new LifeCell[SIZE][SIZE];
		for ( r = 0; r < SIZE; r++ )
			for ( c = 0; c < SIZE; c++ )
				grid[r][c] = new LifeCell();

		if ( fileName == null ) //use random population
		{                                           
			for ( r = 0; r < SIZE; r++ )
			{
				for ( c = 0; c < SIZE; c++ )
				{
					if ( Math.random() > 0.85) //15% chance of a cell starting alive
						grid[r][c].setAliveNow(true);
				}
			}
		}
		else
		{                 
			Scanner input = new Scanner(new File(fileName));
			int numInitialCells = input.nextInt();
			for (int count=0; count<numInitialCells; count++)
			{
				r = input.nextInt();
				c = input.nextInt();
				grid[r][c].setAliveNow(true);
			}
			input.close();
		}

		myView = view;
		myView.updateView(grid);
	}
	
	/** resume the simulation (the pause button in the GUI */
	public void resume()
	{
		timer.restart();
	}
	
	/** run the simulation (the pause button in the GUI */
	public void run()
	{
		timer = new Timer(50, this);
		timer.setCoalesce(true);
		timer.start();
	}

	public void colorRandomizer(LifeView view, Boolean pass){
		view.setRandom(pass);
	}
	
	/** called each time timer fires */
	public void actionPerformed(ActionEvent e)
	{
		oneGeneration();
		myView.updateView(grid);
	}

	/** main logic method for updating the state of the grid / simulation */
	private void oneGeneration()
	{
		for(int r =0; r< grid.length;r++) {
			for(int c = 0; c < grid[r].length;c++) {
				
				int counter = 0; 
				
				try {
					if(grid[r-1][c-1].isAliveNow()) {
						counter++; 
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					
				}
				
				try {
					if(grid[r-1][c].isAliveNow()) {
						counter++; 
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					
				}
				try {
					if(grid[r-1][c+1].isAliveNow()) {
						counter++; 
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					
				}
				
				try {
					if(grid[r][c-1].isAliveNow()) {
						counter++; 
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					
				}
				
				try {
					if(grid[r][c+1].isAliveNow()) {
						counter++; 
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					
				}
				try {
					if(grid[r+1][c-1].isAliveNow()) {
						counter++; 
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					
				}
				
				try {
					if(grid[r+1][c].isAliveNow()) {
						counter++; 
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					
				}
				
				try {
					if(grid[r+1][c+1].isAliveNow() ) {
						counter++; 
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					
				}
				
				
				if((counter == 2 || counter == 3) && grid[r][c].isAliveNow()) {
					grid[r][c].setAliveNext(true);
				}
				else {
					grid[r][c].setAliveNext(false);
				}
		
				if(counter == 3 && !(grid[r][c].isAliveNow())) {
					grid[r][c].setAliveNext(true);
				}
				
			}
		}
		
		for(int r = 0; r< SIZE;r++) {
			for(int c = 0; c<SIZE;c++) {
				grid[r][c].setAliveNow(grid[r][c].isAliveNext());
			}
		}
	}
}

