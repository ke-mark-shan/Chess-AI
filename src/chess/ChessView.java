package chess;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;
import java.util.Observable;
import java.util.Observer;

public class ChessView extends JPanel implements Observer {

	final static Color SELECTED_COLOUR = new Color(117,221,221);//new Color(132,199,208);
	final static Color HIGHLIGHTED_COLOUR = new Color(117,221,221);
	final static Color INCHECK_COLOUR = new Color(170,62,152);
    final static Color[] tileColours = new Color[2];
    
	GameModel model;
	
	//image[0] is white, image[1] is black
	private BufferedImage[] imageKing = new BufferedImage[2];
	private BufferedImage[] imageQueen = new BufferedImage[2];
	private BufferedImage[] imageBishop = new BufferedImage[2];
	private BufferedImage[] imageKnight = new BufferedImage[2];
	private BufferedImage[] imageRook = new BufferedImage[2];
	private BufferedImage[] imagePawn = new BufferedImage[2];
	
	private class KeyController extends KeyAdapter{
		GameModel model;
		
		public KeyController(GameModel m){
			this.model = m;
		}
		
		public void keyPressed(KeyEvent e) {

		    int key = e.getKeyCode();

		    if (key == KeyEvent.VK_LEFT) {
		        System.out.println("View: Undo");
		        this.model.undoMove();
		    }

		    if (key == KeyEvent.VK_RIGHT) {
		    	System.out.println("View Redo");
		    	this.model.redoMove();
		    }

		}
		
	}
	private class MouseController extends MouseAdapter{
		
		GameModel model;
		
		public MouseController(GameModel m){
			this.model = m;
		}
		
		public void mouseClicked(MouseEvent e) {
			
			double x = e.getX();
			double y = e.getY();
			
			if (0 <= x && x < this.model.getBoardSize().getWidth() &&
					0 <= y && y < this.model.getBoardSize().getHeight()){
				
				int BOARD_SIZE = this.model.getBoard().getBoardSize();
				int col = (int) ( (x * BOARD_SIZE) / this.model.getBoardSize().getWidth());
				int row = BOARD_SIZE - (int) ( (y * BOARD_SIZE) / this.model.getBoardSize().getHeight()) - 1;
				
				System.out.println("Clicked: (" + col + ", " + row + ")");
				
				this.model.selectPosition(new Position(col,row));
			}
	    }
	}
	
	public ChessView(GameModel m){
		
		setBackground(Color.WHITE);
		setFocusable(true);
		
		//Initialize MVC
		this.model = m;
		this.model.addObserver(this);
		this.addMouseListener(new MouseController(m));
		this.addKeyListener(new KeyController(m));
		ChessView.tileColours[0] = new Color(111,115,210);
	    ChessView.tileColours[1] = new Color(157,172,255);
	    
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
	
	private BufferedImage getPieceImage(ChessPieceType type, PlayerColour pc){
		
		int pieceType = 0;
		if (pc == PlayerColour.BLACK){
			pieceType = 1;
		}
		
		switch (type){
			case KING:
				return this.imageKing[pieceType];
			case QUEEN:
				return this.imageQueen[pieceType];
			case ROOK:
				return this.imageRook[pieceType];
			case BISHOP:
				return this.imageBishop[pieceType];
			case KNIGHT:
				return this.imageKnight[pieceType];
			case PAWN:
				return this.imagePawn[pieceType];
			default:
				System.err.println("Unexpected piece type: " + type);
				return null;
		}
	}
	
	private void drawBorderCell(Graphics2D g2, int column, int row, double CELL_SIZE, Color borderColour, Color cellColour){
		
		final double BORDER_WIDTH = 0.08 * CELL_SIZE;
		
		g2.setPaint(borderColour);
		g2.fill(new Rectangle2D.Double(CELL_SIZE * column, CELL_SIZE * row, CELL_SIZE, CELL_SIZE));
		
		g2.setPaint(cellColour);
		g2.fill(new Rectangle2D.Double(CELL_SIZE * column + BORDER_WIDTH, CELL_SIZE * row + BORDER_WIDTH, CELL_SIZE - 2 * BORDER_WIDTH, CELL_SIZE - 2 * BORDER_WIDTH));
		
	}
	
	private void drawCell(Graphics g, int column, int row, int BOARD_SIZE, double CELL_SIZE){
		
		Graphics2D g2 = (Graphics2D) g;
		Position pos = new Position(column, BOARD_SIZE - row - 1);
		Color cellColour = ChessView.tileColours[(column + row) % 2];
		
		//Draw background
		switch (this.model.getBoard().getState(pos)){
			case DEFAULT:
				g2.setPaint(cellColour);
				g2.fill(new Rectangle2D.Double(CELL_SIZE * column, CELL_SIZE * row, CELL_SIZE, CELL_SIZE));
				break;
			case INCHECK:
				this.drawBorderCell(g2, column, row, CELL_SIZE, ChessView.INCHECK_COLOUR, cellColour);
				break;
			case INCHECKMATE:
				g2.setPaint(ChessView.INCHECK_COLOUR);
				g2.fill(new Rectangle2D.Double(CELL_SIZE * column, CELL_SIZE * row, CELL_SIZE, CELL_SIZE));
				break;
			case SELECTED:
				g2.setPaint(ChessView.SELECTED_COLOUR);
				g2.fill(new Rectangle2D.Double(CELL_SIZE * column, CELL_SIZE * row, CELL_SIZE, CELL_SIZE));
				break;
			case HIGHLIGHTED:
				this.drawBorderCell(g2, column, row, CELL_SIZE, ChessView.HIGHLIGHTED_COLOUR, cellColour);
				break;
			default:
				System.err.println("Unexpected state (" + column + ", " + row +"): " + this.model.getBoard().getState(pos));
		}
		
		// Draw chess piece if there is one
		ChessPiece piece = this.model.getBoard().getPiece(pos);
		if (null != piece){
			g.drawImage(this.getPieceImage(piece.getType(), piece.getPlayerColour()), (int) CELL_SIZE * column, (int) CELL_SIZE * row, (int) CELL_SIZE, (int) CELL_SIZE, null);
		}
					
	}
	
	public void paintComponent(Graphics g) {
		System.out.println("Start Drawing");
   	 	super.paintComponent(g);
   	 	
   	 	final int BOARD_SIZE = this.model.getBoard().getBoardSize();
   	 	final double CELL_SIZE = this.model.getBoardSize().getWidth() / BOARD_SIZE;
        
        for (int column = 0; column < BOARD_SIZE; column++){
			for (int row = 0; row < BOARD_SIZE; row++){
				this.drawCell(g, column, row, BOARD_SIZE, CELL_SIZE);
			}
		}
        System.out.println("Finish Drawing");
	}
	
	@Override
    public void update(Observable o, Object arg) {
    	repaint();
    }
}
