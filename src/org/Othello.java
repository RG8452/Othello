package org;
/*
 * RG and JO
 * This is the main Othello class
 * It generates all the frames and such necessary to run the game
 * It will eventually probably do much more
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Othello
{
	public static JFrame jf; //Frame on which the game is located
	public static OthelloPanel op; //Panel which handles drawing and input
	public static JLabel jl; //Label to display current turn
	public static JButton reset; //Button for resetting game
	public static char[][] grid = new char[8][8]; //Create grid in which to store pieces
	public static boolean playerOne; //True if current player is black

	public static void main(String[] args)
	{
		jf = new JFrame("Othello"); //Initialize components
		op = new OthelloPanel();
		jl = new JLabel("Current player: Black");
		reset = new JButton("Reset");
		playerOne = true;

		op.setPreferredSize(new Dimension(800, 800)); //Set sizes
		jl.setSize(800, 20);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				reset();
			}
		});

		jf.add(op, BorderLayout.NORTH); //Add components to frame
		jf.add(jl, BorderLayout.CENTER);
		jf.add(reset, BorderLayout.SOUTH);

		jf.revalidate(); //Validate component hierarchy
		jf.pack(); //Size frame to fit components
		jf.setFocusTraversalKeysEnabled(true); //Allow key traversal
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set up close operation
		reset();	//Reset initial variables
		jf.setVisible(true); //Set visible
	}

	//Method which returns true if a spot is valid for the current player
	public static boolean isValid(int r, int c)
	{
		boolean output = false;
		output |= checkSpot(r, c + 1, 0, 1, 0); //Checks all directions unit-circle-wise
		if (output) return true; //Fail-fast checks
		output |= checkSpot(r - 1, c + 1, -1, 1, 0);
		if (output) return true;
		output |= checkSpot(r - 1, c, -1, 0, 0);
		if (output) return true;
		output |= checkSpot(r - 1, c - 1, -1, -1, 0);
		if (output) return true;
		output |= checkSpot(r, c - 1, 0, -1, 0);
		if (output) return true;
		output |= checkSpot(r + 1, c - 1, 1, -1, 0);
		if (output) return true;
		output |= checkSpot(r + 1, c, 1, 0, 0);
		if (output) return true;
		output |= checkSpot(r + 1, c + 1, 1, 1, 0);
		return output;
	}

	//Recursive method which checks in a given direction to see if a spot is valid
	public static boolean checkSpot(int r, int c, int dR, int dC, int steps)
	{
		if (r < 0 || c < 0 || r > 7 || c > 7) //Out of bounds case
			return false;
		else if (grid[r][c] == ((playerOne) ? 'W' : 'B')) //If current spot is opposite team
			return checkSpot(r + dR, c + dC, dR, dC, steps + 1);
		else if (grid[r][c] == ((playerOne) ? 'B' : 'W')) //Same team case
			return (steps > 0); //True if you've hit the same team after hitting the other team
		return false; //If empty, return false
	}

	//This method will flip all pieces during a move
	public static void flip(int r, int c)
	{
		//check for pieces to flip in all directions
		flipSpot(r, c + 1, 0, 1); 
		flipSpot(r - 1, c + 1, -1, 1);
		flipSpot(r - 1, c, -1, 0);
		flipSpot(r - 1, c - 1, -1, -1);
		flipSpot(r, c - 1, 0, -1);
		flipSpot(r + 1, c - 1, 1, -1);
		flipSpot(r + 1, c, 1, 0);
		flipSpot(r + 1, c + 1, 1, 1);		
	}
	
	//this method will find and flip all possible pieces on a move
	public static void flipSpot(int r, int c, int dR, int dC)
	{
		if (r < 0 || c < 0 || r > 7 || c > 7) //Out of bounds case
			return; // do nothing
		else if (grid[r][c] == ((playerOne) ? 'W' : 'B')) //If current spot is opposite team and is valid
		{
			if (playerOne) grid[r][c] = 'B'; //Change piece to opposite team
			else grid[r][c] = 'W'; 
			flipSpot(r + dR, c + dC, dR, dC);
		}
		else if (grid[r][c] == ((playerOne) ? 'B' : 'W')) //Same team case
			return;	//do nothing
	}
	
	//This method resets the whole board
	public static void reset()
	{
		playerOne = true; //Reset Turn
		grid = new char[8][8]; //Reset grid
		grid[3][3] = 'W'; //Reset basic layout
		grid[3][4] = 'B';
		grid[4][3] = 'B';
		grid[4][4] = 'W';
		jl.setText("Current player: " + ((playerOne) ? "Black" : "White"));
		jf.repaint();
	}
}
