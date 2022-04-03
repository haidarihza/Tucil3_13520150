import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Puzzle extends JPanel { 
  // Size of Game of Fifteen instance
  private int size;
  // Number of tiles
  private int nbTiles;
  // Grid UI Dimension
  private int dimension;
  // Foreground Color
  private static final Color FOREGROUND_COLOR = new Color(239, 83, 80); // we use arbitrary color
  // Storing the tiles in a 1D Array of integers
  private int[] tiles;
  // Size of tile on UI
  private int tileSize;
  // Position of the blank tile
  private int blankPos;
  // Margin for the grid on the frame
  private int margin;
  // Grid UI Size
  private int gridSize;
  
  public Puzzle(int size, int dim, int mar, int[] arr, ArrayList<String> step) {
    this.size = size;
    dimension = dim;
    margin = mar;
    
    // init tiles 
    nbTiles = size * size - 1; // -1 because we don't count blank tile
    tiles = new int[size * size];
    
    // calculate grid size and tile size
    gridSize = (dim - 2 * margin);
    tileSize = gridSize / size;
    
    setPreferredSize(new Dimension(dimension, dimension + margin));
    setBackground(Color.WHITE);
    setForeground(FOREGROUND_COLOR);
    setFont(new Font("SansSerif", Font.BOLD, 20));
    for (int i = 0; i < tiles.length; i++) {
        tiles[i] = arr[i];
        if (tiles[i] == 0) {
        	blankPos = i;
        }
      }
    addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
        	if (step.isEmpty()) {
        		//do nothing
        	}
        	else {
        	//untuk menentukan koordinat mana yang akan diganti oleh pos kosong
	        	int r1 = 0;
	        	int c1 = 0;
	            int c2 = blankPos % size;
	            int r2 = blankPos / size;
	        	if (step.get(0).equals("up")){
	        		r1 = r2-1;
	       			c1 = c2;
	       		}
	       		else if (step.get(0).equals("down")) {
	       			r1 = r2+1;
	       			c1 = c2;
	       		}
	        	else if (step.get(0).equals("right")) {
	        		r1 = r2;
	       			c1 = c2+1;
	       		}
	       		else if (step.get(0).equals("left")) {
	       			r1 = r2;
	       			c1 = c2-1;
	       		}
	            // we convert in the 1D coord 
	            int clickPos = r1 * size + c1;
	            int temp = tiles[blankPos];
	            tiles[blankPos] = tiles[clickPos];
	            tiles[clickPos] = temp;
	            blankPos = clickPos;
	            step.remove(0);
	            // we repaint panel
	            repaint();
        	}
        }
      });
  } 
  
  private void drawGrid(Graphics2D g) {
    for (int i = 0; i < tiles.length; i++) {
      // we convert 1D coords to 2D coords given the size of the 2D Array
      int r = i / size;
      int c = i % size;
      // we convert in coords on the UI
      int x = margin + c * tileSize;
      int y = margin + r * tileSize;
      
      // check special case for blank tile
      if(tiles[i] == 0) {
          g.setColor(FOREGROUND_COLOR);
          continue;
      }
      
      // for other tiles
      g.setColor(getForeground());
      g.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
      g.setColor(Color.BLACK);
      g.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
      g.setColor(Color.WHITE);
      
      drawCenteredString(g, String.valueOf(tiles[i]), x , y);
    }
  }
  
  private void drawCenteredString(Graphics2D g, String s, int x, int y) {
    // center string s for the given tile (x,y)
    FontMetrics fm = g.getFontMetrics();
    int asc = fm.getAscent();
    int desc = fm.getDescent();
    g.drawString(s,  x + (tileSize - fm.stringWidth(s)) / 2, 
        y + (asc + (tileSize - (asc + desc)) / 2));
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    drawGrid(g2D);
  }
}