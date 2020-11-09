
// Name:Hemanth Eshwarappa	
// USC loginid:eshwarap
// CS 455 PA3
// Fall 2017

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

/**
 * MazeComponent class
 * 
 * A component that displays the maze and path through it if one has been found.
 */
public class MazeComponent extends JComponent {

	private static final int START_X = 10; // top left of corner of maze in
											// frame
	private static final int START_Y = 10;
	private static final int BOX_WIDTH = 20; // width and height of one maze
												// "location"
	private static final int BOX_HEIGHT = 20;
	private static final int INSET = 2;
	// how much smaller on each side to make entry/exit inner box
	private static Maze newMaze; //to save the maze in the temp maze
	private MazeCoord start;     
	private MazeCoord end;
	private LinkedList<MazeCoord> pathofResult; //To save path from maze
	private LinkedList<MazeCoord> pathforLine;  // To save the path for output line
	private boolean isPathFound = false;
	private ArrayList<MazeCoord> pathTemp;  // To save the line path
	

	/**
	 * Constructs the component.
	 * 
	 * @param maze
	 *            the maze to display
	 */
	public MazeComponent(Maze maze) {
		newMaze = maze;
		start = newMaze.getEntryLoc();
		end = newMaze.getExitLoc();
		pathofResult = new LinkedList<MazeCoord>();
		pathofResult = maze.getPath();  //get the path from maze
       
       }

	/**
	 * Draws the current state of maze including the path through it if one has
	 * been found.
	 * 
	 * @param g
	 *            the graphics context
	 */
	public void paintComponent(Graphics g) {
		pathforLine = new LinkedList<MazeCoord>();
		//Check if the path is calculated or not
		if (pathofResult.size() != 0) {
			for (MazeCoord x : pathofResult) {
                  pathforLine.add(new MazeCoord((BOX_WIDTH * x.getCol()+ START_Y + BOX_WIDTH / 2),
						(BOX_HEIGHT * x.getRow()+START_X + BOX_HEIGHT / 2)));
			}
		}
        // Initial config for maze
		int countDown = START_Y;  //variables to use in x and y coordinates settting
		int countRight = START_X;

		for (int i = 0; i < newMaze.numRows(); i++) {
			countRight = START_X;

			for (int j = 0; j < newMaze.numCols(); j++) {
				MazeCoord loc = new MazeCoord(i, j);
                //if maze draw wall at that coordinate
				if (newMaze.hasWallAt(loc)) {
					g.setColor(Color.BLACK);
					g.fillRect(countRight, countDown, BOX_HEIGHT, BOX_WIDTH);

				} else {

					g.setColor(Color.WHITE);
					g.fillRect(countRight, countDown, BOX_HEIGHT, BOX_WIDTH);

				}
				countRight += BOX_WIDTH;
			}
			countDown += BOX_HEIGHT;
		}
        
		//pathforLine.addLast(new MazeCoord((BOX_WIDTH * end.getCol() + 10), (BOX_HEIGHT * end.getRow() + 10)));
        //draw start and end boxes
		g.setColor(Color.YELLOW);
		g.fillRect(BOX_WIDTH * start.getCol()+START_Y+INSET, BOX_HEIGHT * start.getRow()+START_X+INSET, BOX_HEIGHT-INSET,BOX_WIDTH -INSET);

		g.setColor(Color.GREEN);
		g.fillRect(BOX_WIDTH * end.getCol()+START_Y+INSET, BOX_HEIGHT * end.getRow()+START_Y+INSET, BOX_HEIGHT - INSET, BOX_WIDTH-INSET);
        //save path for line in arraylist so that its easily accessible while drawing
		 pathTemp = new ArrayList<MazeCoord>(pathforLine);
		 
		for (int i = 0; i < pathTemp.size() - 1; i++) {

			g.setColor(Color.BLUE);
			//only for initial line
			if (i == 0)
				g.drawLine((BOX_WIDTH * end.getCol()+START_Y + (BOX_WIDTH / 2)), (BOX_HEIGHT * end.getRow()+START_X + (BOX_HEIGHT / 2)),
						pathTemp.get(0).getRow(), pathTemp.get(0).getCol());
			//rest of the lines in the path
			g.drawLine(pathTemp.get(i).getRow(), pathTemp.get(i).getCol(), pathTemp.get(i + 1).getRow(),
					pathTemp.get(i + 1).getCol());

		}

	}

}
