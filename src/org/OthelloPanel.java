package org;
/*
 * This is the panel for the othello game
 * It will handle all inputs and drawing
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class OthelloPanel extends JPanel
{
	private static Color background = new Color(0, 255, 128); //Color of the background
	private static int offset = 5; //Variable for the offset of the pieces from the corner
	public static int tCount = 0; // keeps track of how many turns no player has moved for

	//Default constructor
	public OthelloPanel()
	{
		OthelloHandler oHandler = new OthelloHandler(); //Initialize handler
		addKeyListener(oHandler); //Add handlers
		addMouseListener(oHandler);
		setFocusable(true); //Allow focus
		setBackground(background);
	}

	//Override painting method for unique drawing
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); //Call super to paint on frame
		Graphics2D g2d = (Graphics2D) g; //Establish better graphics system
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Enable antialiasing

		drawGrid(g2d);
		drawPieces(g2d);
	}

	//Draws in circles for the pieces depending on what the grid contains
	private void drawPieces(Graphics2D g2d)
	{
		for (int row = 0; row < 8; row++)
		{
			for (int col = 0; col < 8; col++) //Loop through every piece
			{
				switch (Othello.grid[row][col]) //Switch the current char
				{
					case '\0': //Dummy case / null
						continue; //Ignore and go to the next slot
					case 'W': //White
						g2d.setColor(Color.white);
						break;
					case 'B': //Black
						g2d.setColor(Color.black);
						break;
					case 'R': //Red/faded/whatever we decide
						g2d.setColor(Color.red);
						break;
				}
				g2d.fillOval(100 * col + offset, 100 * row + offset, 100 - (offset << 1), 100 - (offset << 1)); //Draw the circle
			}
		}
	}

	//Draws in the grid to establish that checkerboard pattern
	private void drawGrid(Graphics2D g2d)
	{
		g2d.setColor(Color.black);
		for (int i = 0; i < 8; i++)
		{
			g2d.drawLine(0, 100 * i, 800, 100 * i);
			g2d.drawLine(100 * i, 0, 100 * i, 800);
		}
	}

	/*
	 * This private class does all the input handling for the Othello Handling It
	 * will also likely control repaint rate KeyListening may prove to be pointless
	 */
	private class OthelloHandler implements KeyListener, MouseListener
	{
		//@formatter:off
		// keyListener methods
		public void keyPressed(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		public void keyTyped(KeyEvent e) // Basic printing method
		{
			char c = e.getKeyChar();
			System.out.println("You typed " + c);
		}

		// mouseListener methods
		public void mouseClicked(MouseEvent e)
		{
			if (!Othello.hasValidMove())
			{
				Othello.playerOne = !Othello.playerOne;
				Othello.jl.setText("Current player: " + ((Othello.playerOne) ? "Black because white had no valid moves" : "White because Black had no valid moves"));	//Set label
				Othello.op.repaint();	//Repaint necessary components
				Othello.jl.repaint();
				tCount += 1;
				
			}
//			System.out.println(String.format("Clicked at %d, %d", e.getX(), e.getY()));
//			System.out.println("Click coords - Row: " + (int)(e.getY() / 100) + "\tCol: " + (int)(e.getX() / 100));
			int clickRow = (int)(e.getY() / 100);
			int clickCol = (int)(e.getX() / 100);
			if(Othello.grid[clickRow][clickCol] == 'R')	//If spot is already clicked
			{
				Othello.flip(clickRow, clickCol); //change corresponding pieces
				Othello.grid[clickRow][clickCol] = (Othello.playerOne) ? 'B' : 'W';
				Othello.playerOne = !Othello.playerOne;
				Othello.jl.setText("Current player: " + ((Othello.playerOne) ? "Black" : "White"));	//Set label
				Othello.op.repaint();	//Repaint necessary components
				Othello.jl.repaint();
				tCount = 0;
				return;
			}
			
			for(int z=0; z<8; z++)
			{
				for(int zz=0; zz<8; zz++)
				{
					if(Othello.grid[z][zz] == 'R') 
					{
						Othello.grid[z][zz] = '\0';
					}
				}
			}
			
			if(Othello.grid[clickRow][clickCol] == '\0' && Othello.isValid(clickRow, clickCol))	//If spot is a valid move		
				Othello.grid[clickRow][clickCol] = 'R'; 
				
			//Do nothing if spot is invalid
			Othello.jl.setText("Current player: " + ((Othello.playerOne) ? "Black" : "White"));	//Set label
			Othello.op.repaint();	//Repaint necessary components
			Othello.jl.repaint();
			
			if (tCount == 2 || Othello.isFull())
			{
			Othello.gameOver();
			}
		}
		
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		//@formatter:on
	}
}