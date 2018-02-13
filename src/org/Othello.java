package org;
/*
 * RG and JO
 * This is the main Othello class
 * It generates all the frames and such necessary to run the game
 * It will eventually probably do much more
 */

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Othello
{
	public static JFrame jf; //Frame on which the game is located
	public static OthelloPanel op; //Panel which handles drawing and input
	public static JLabel jl; //Label to display current turn
	public static char[][] grid = new char[8][8]; //Create grid in which to store pieces

	public static void main(String[] args)
	{
		jf = new JFrame("Othello"); //Initialize components
		op = new OthelloPanel();
		jl = new JLabel("Current player: Black");

		op.setPreferredSize(new Dimension(800, 800)); //Set sizes
		jl.setSize(800, 20);

		jf.add(op, BorderLayout.CENTER); //Add components to frame
		jf.add(jl, BorderLayout.SOUTH);
		jf.revalidate(); //Validate component hierarchy
		jf.pack(); //Size frame to fit components
		jf.setFocusTraversalKeysEnabled(true); //Allow key traversal
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set up close operation
		jf.setVisible(true); //Set visible
		grid[3][3] = 'W';
		grid[3][4] = 'B';
		grid[4][3] = 'B';
		grid[4][4] = 'W';
	}
}
