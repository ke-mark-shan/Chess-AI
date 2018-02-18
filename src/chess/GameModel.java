package chess;

import java.awt.geom.Rectangle2D;

public class GameModel extends ChessModel {

	private Rectangle2D boardDimensions;
	
	public GameModel(Player p1, Player p2){
		super(p1,p2);
		this.boardDimensions = new Rectangle2D.Double(0,0,0,0);
	}
	
	// Getters and Setters
	public Rectangle2D getBoardSize(){
		return this.boardDimensions;
	}
	
	public void setBoardSize(double w, double h){
		
		// The game board will the largest square that could fit within the window
		double boardWidth = Math.min(w,h);
		boardDimensions = new Rectangle2D.Double(0, 0, boardWidth, boardWidth);
		setChangedAndNotify();
	}
}
