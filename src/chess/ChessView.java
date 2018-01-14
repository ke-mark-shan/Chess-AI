package chess;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
        Graphics2D g2 = (Graphics2D) g; 
        
        //draw background
        g2.setPaint(Color.BLACK);
        g2.fill(model.getBoard());
        
	}
	
	@Override
    public void update(Observable o, Object arg) {
    	repaint();
    }
}
