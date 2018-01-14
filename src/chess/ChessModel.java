package chess;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
public class ChessModel extends Observable{
	private Player player1;
	private Player player2;
	private Rectangle2D board;
	
	public ChessModel(double w, double h){
		board = new Rectangle2D.Double(0,0,w,h);
	}
	
	public Rectangle2D getBoard(){
		return board;
	}
	
	public void setBoard(double w, double h){
		//The game board will the largest square that could fit within the window
		double boardWidth = Math.min(w,h);
		board = new Rectangle2D.Double((w - boardWidth)/2, (h - boardWidth)/2, boardWidth, boardWidth);
		setChangedAndNotify();
	}
	//Notify observers of changes
    public void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }
}
