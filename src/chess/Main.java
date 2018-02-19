/*
 * Chess piece images retrieved from: https://en.wikipedia.org/wiki/User:Cburnett
 */

package chess;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class Main extends JPanel{
	
	GameModel model;
	ChessView chessView;
	
	private class ResizeListener extends ComponentAdapter {
	    public void componentResized(ComponentEvent e) {
	       	// Resize view when window resized
	    	Dimension newSize = e.getComponent().getBounds().getSize(); 
	    	model.setBoardSize(newSize.getWidth(),newSize.getHeight());
	    	
	    }
	}
	
	public Main() {
		
        // Set up MVC
		model = new GameModel(new Player(PlayerColour.WHITE), new Player(PlayerColour.BLACK));
		chessView = new ChessView(model);
				
        // layout the views
        setLayout(new BorderLayout());
        
        this.addComponentListener(new ResizeListener());
        this.add(chessView);
        
    }
	
	public static void main(String[] args) {
		
        // create the window
        JFrame f = new JFrame("Chess"); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1100, 800); 
        f.setContentPane(new Main()); 
        f.setVisible(true);
        f.setResizable(false);
    }
}
