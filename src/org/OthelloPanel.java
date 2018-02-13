package org;
/*
 * This is the panel for the othello game
 * It will handle all inputs and drawing
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class OthelloPanel extends JPanel
{
	//Default constructor
	public OthelloPanel()
	{
		OthelloHandler oHandler = new OthelloHandler(); //Initialize handler
		addKeyListener(oHandler); //Add handlers
		addMouseListener(oHandler);
		setFocusable(true); //Allow focus
	}

	/*
	 * This private class does all the input handling for the Othello Handling 
	 * It will also likely control repaint rate
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
			System.out.println(String.format("Clicked at %d, %d", e.getX(), e.getY()));
		}

		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		//@formatter:on
	}
}
