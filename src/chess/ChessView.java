package chess;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class ChessView extends JPanel implements Observer {

	ChessModel model;
	
	//Mouse controller for game board events
	private class MouseController extends MouseAdapter{
		
		//Start dragging 
		public void mousePressed(MouseEvent e){
			
		}
	}
	
	public ChessView(ChessModel m){
		setBackground(Color.WHITE);
		
		//Initialize MVC
		this.model = m;
		this.model.addObserver(this);
		
	}
	
	public void paintComponent(Graphics g) {
   	 	super.paintComponent(g);
   	 	
   	 	final int BOARD_SIZE = this.model.getBoard().BOARD_SIZE;
   	 	final double CELL_SIZE = this.model.getBoardSize().getWidth() / BOARD_SIZE;
   	 	
   	 	final Color SELECTED_COLOUR = Color.RED;
   	 	final Color HIGHLIGHTED_COLOUR = Color.YELLOW;
        Color[] tileColours = new Color[2];
        tileColours[0] = new Color(238,238,210);
        tileColours[1] = new Color(255,255,255);
        
        Graphics2D g2 = (Graphics2D) g; 
        
        //draw board
        g2.setPaint(Color.BLACK);
        g2.fill(model.getBoardSize());
        
        for (int column = 0; column < BOARD_SIZE; column++){
			for (int row = 0; row < BOARD_SIZE; row++){
				Pair<Integer, Integer> pos = new Pair<Integer, Integer>(column, row);
				
				switch(this.model.getBoard().getState(pos)){
					case DEFAULT:
						g2.setPaint(tileColours[(column + row) % 2]);	//Checker board pattern
						break;
					case SELECTED:
						g2.setPaint(SELECTED_COLOUR);
						break;
					case HIGHLIGHTED:
						g2.setPaint(HIGHLIGHTED_COLOUR);
						break;
					default:
						System.err.println("Unexpected state (" + column + ", " + row +"): " + this.model.getBoard().getState(pos));
				}
				g2.fill(new Rectangle2D.Double(CELL_SIZE * column, CELL_SIZE * row, CELL_SIZE,CELL_SIZE));
			}
		}
	}
	
	@Override
    public void update(Observable o, Object arg) {
    	repaint();
    }
}
