package chess;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;
import java.util.Observable;
import java.util.Observer;

public class ChessView extends JPanel implements Observer {

	ChessModel model;
	
	//image[0] is white, image[1] is black
	private BufferedImage[] imageKing = new BufferedImage[2];
	private BufferedImage[] imageQueen = new BufferedImage[2];
	private BufferedImage[] imageBishop = new BufferedImage[2];
	private BufferedImage[] imageKnight = new BufferedImage[2];
	private BufferedImage[] imageRook = new BufferedImage[2];
	private BufferedImage[] imagePawn = new BufferedImage[2];
	
	
	private class MouseController extends MouseAdapter{
		
		ChessModel model;
		
		public MouseController(ChessModel m){
			this.model = m;
		}
		
		public void mouseClicked(MouseEvent e) {
			double x = e.getX();
			double y = e.getY();
			
			if (0 <= x && x < this.model.getBoardSize().getWidth() &&
					0 <= y && y < this.model.getBoardSize().getHeight()){
				int BOARD_SIZE = this.model.getBoard().BOARD_SIZE;
				int col = (int) ( (x * BOARD_SIZE) / this.model.getBoardSize().getWidth());
				int row = BOARD_SIZE - (int) ( (y * BOARD_SIZE) / this.model.getBoardSize().getHeight()) - 1;
				System.out.println("Clicked: (" + col + ", " + row + ")");
				this.model.selectPosition(new Pair<Integer, Integer>(col,row));
			}
	    }
	}
	
	public ChessView(ChessModel m){
		
		setBackground(Color.WHITE);
		
		//Initialize MVC
		this.model = m;
		this.model.addObserver(this);
		this.addMouseListener(new MouseController(m));
		//Get the chess piece images
		try {
			imageKing[0] = ImageIO.read(new File("resources/King_White.png"));
			imageKing[1] = ImageIO.read(new File("resources/King_Black.png"));
			imageQueen[0] = ImageIO.read(new File("resources/Queen_White.png"));
			imageQueen[1] = ImageIO.read(new File("resources/Queen_Black.png"));
			imageBishop[0] = ImageIO.read(new File("resources/Bishop_White.png"));
			imageBishop[1] = ImageIO.read(new File("resources/Bishop_Black.png"));
			imageKnight[0] = ImageIO.read(new File("resources/Knight_White.png"));
			imageKnight[1] = ImageIO.read(new File("resources/Knight_Black.png"));
			imageRook[0] = ImageIO.read(new File("resources/Rook_White.png"));
			imageRook[1] = ImageIO.read(new File("resources/Rook_Black.png"));
			imagePawn[0] = ImageIO.read(new File("resources/Pawn_White.png"));
			imagePawn[1] = ImageIO.read(new File("resources/Pawn_Black.png"));
		}
		catch (IOException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace(new PrintStream(System.err));
		}
	}
	
	public void paintComponent(Graphics g) {
   	 	super.paintComponent(g);
   	 	
   	 	final int BOARD_SIZE = this.model.getBoard().BOARD_SIZE;
   	 	final double CELL_SIZE = this.model.getBoardSize().getWidth() / BOARD_SIZE;
 
   	 	final Color SELECTED_COLOUR = Color.BLUE;
   	 	final Color HIGHLIGHTED_COLOUR = new Color(144, 238, 144);//new Color(255, 255, 204);
        Color[] tileColours = new Color[2];
        tileColours[0] = new Color(111,115,210);
        tileColours[1] = new Color(157,172,255);
        
        Graphics2D g2 = (Graphics2D) g; 
        
        //draw board
        g2.setPaint(Color.BLACK);
        g2.fill(model.getBoardSize());
        
        for (int column = 0; column < BOARD_SIZE; column++){
			for (int row = 0; row < BOARD_SIZE; row++){
				Pair<Integer, Integer> pos = new Pair<Integer, Integer>(column, BOARD_SIZE - row - 1);
				
				// Fill in cell background
				g2.setPaint(tileColours[(column + row) % 2]);
				g2.fill(new Rectangle2D.Double(CELL_SIZE * column, CELL_SIZE * row, CELL_SIZE,CELL_SIZE));
				
				// Draw chess piece if there is one
				ChessPiece piece = this.model.getBoard().getPiece(pos);
				if (null != piece){
					int pieceType = 0;
					if (piece.getPlayerColour() == PlayerColour.BLACK){
						pieceType = 1;
					}
					
					BufferedImage pieceImage = this.imageKing[pieceType];
					
					switch (piece.getType()){
						case KING:
							break;
						case QUEEN:
							pieceImage = this.imageQueen[pieceType];
							break;
						case ROOK:
							pieceImage = this.imageRook[pieceType];
							break;
						case BISHOP:
							pieceImage = this.imageBishop[pieceType];
							break;
						case KNIGHT:
							pieceImage = this.imageKnight[pieceType];
							break;
						case PAWN:
							pieceImage = this.imagePawn[pieceType];
							break;
						default:
							System.err.println("Unexpected piece type (" + column + ", " + row +"): " + piece.getType());
				
					}
					
					g.drawImage(pieceImage, (int) CELL_SIZE * column, (int) CELL_SIZE * row, (int) CELL_SIZE, (int) CELL_SIZE, null);
				}
				switch (this.model.getBoard().getState(pos)){
					case DEFAULT:
						break;
					case SELECTED:
						break;
					case HIGHLIGHTED:
						final double CIRCLE_DIAMETER = 0.3 * CELL_SIZE; 
						g2.setPaint(HIGHLIGHTED_COLOUR);
						g2.fillOval((int)(CELL_SIZE * (column + 0.5) - CIRCLE_DIAMETER / 2), (int)(CELL_SIZE * (row + 0.5) - CIRCLE_DIAMETER / 2), (int)CIRCLE_DIAMETER, (int)CIRCLE_DIAMETER);
						break;
					default:
						System.err.println("Unexpected state (" + column + ", " + row +"): " + this.model.getBoard().getState(pos));
				}
							
			}
		}
	}
	
	@Override
    public void update(Observable o, Object arg) {
    	repaint();
    }
}
