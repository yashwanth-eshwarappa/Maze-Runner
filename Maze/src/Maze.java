
// Name:Hemanth Eshwarappa
// USC loginid:eshwarap
// CS 455 PA3
// Fall 2017

import java.util.LinkedList;

/**
 * Maze class
 * 
 * Stores information about a maze and can find a path through the maze (if
 * there is one).
 * 
 * Assumptions about structure of the maze, as given in mazeData, startLoc, and
 * endLoc (parameters to constructor), and the path: -- no outer walls given in
 * mazeData -- search assumes there is a virtual border around the maze (i.e.,
 * the maze path can't go outside of the maze boundaries) -- start location for
 * a path is maze coordinate startLoc -- exit location is maze coordinate
 * exitLoc -- mazeData input is a 2D array of booleans, where true means there
 * is a wall at that location, and false means there isn't (see public FREE /
 * WALL constants below) -- in mazeData the first index indicates the row. e.g.,
 * mazeData[row][col] -- only travel in 4 compass directions (no diagonal paths)
 * -- can't travel through walls
 * 
 */

public class Maze {

	public static final boolean FREE = false;
	public static final boolean WALL = true;

	private static boolean[][] mazeCoord; // maze values are saved in here
	private static MazeCoord start = null;
	private static MazeCoord end = null;
	private LinkedList<MazeCoord> path = new LinkedList<MazeCoord>(); // List to
																		// save
																		// the
																		// path
	private boolean[][] visited; // To keep track of visited components
	private boolean[][] newMaze;
	private static boolean isSearched = false; // To check if path is already
												// found

	/**
	 * Constructs a maze.
	 * 
	 * @param mazeData
	 *            the maze to search. See general Maze comments above for what
	 *            goes in this array.
	 * @param startLoc
	 *            the location in maze to start the search (not necessarily on
	 *            an edge)
	 * @param exitLoc
	 *            the "exit" location of the maze (not necessarily on an edge)
	 *            PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <=
	 *            startLoc.getCol() < mazeData[0].length and 0 <=
	 *            endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() <
	 *            mazeData[0].length
	 * 
	 */
   /**
    */
	//Representation Invariant
	//mazeData is at max 25 as it goes beyond frame after wards
	//startloc and endloc must be within the maze
	 
	public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {
		mazeCoord = new boolean[mazeData.length][mazeData[0].length];
		// save the maze coordinates in our mazeCoord array
		for (int i = 0; i < mazeData.length; i++)
			for (int j = 0; j < mazeData[0].length; j++) {
				mazeCoord[i][j] = mazeData[i][j];
			}

		start = startLoc;
		end = exitLoc;
		// 2 added to length of mazecoord to accomodate pseudo walls in the
		// outline
		visited = new boolean[mazeCoord.length + 2][mazeCoord[0].length + 2];
		newMaze = new boolean[mazeCoord.length + 2][mazeCoord[0].length + 2];
		// draw pseudo outline using 1's in the border
		for (int j = 0; j < newMaze[0].length; j++)
			newMaze[0][j] = true;
		for (int j = 0; j < newMaze.length; j++)
			newMaze[j][0] = true;
		for (int j = 0; j < newMaze.length; j++)
			newMaze[j][newMaze[0].length - 1] = true;
		for (int j = 0; j < newMaze[0].length; j++)
			newMaze[newMaze.length - 1][j] = true;
		// save all other values in new maze
		for (int i = 1; i < newMaze.length - 1; i++) {
			for (int j = 1; j < newMaze[0].length - 1; j++) {
				if (mazeCoord[i - 1][j - 1])
					newMaze[i][j] = true;

			}
		}

	}

	/**
	 * Returns the number of rows in the maze
	 * 
	 * @return number of rows
	 */
	public int numRows() {
		return mazeCoord.length;
	}

	/**
	 * Returns the number of columns in the maze
	 * 
	 * @return number of columns
	 */
	public int numCols() {
		return mazeCoord[0].length;
	}

	/**
	 * Returns true iff there is a wall at this location
	 * 
	 * @param loc
	 *            the location in maze coordinates
	 * @return whether there is a wall here PRE: 0 <= loc.getRow() < numRows()
	 *         and 0 <= loc.getCol() < numCols()
	 */
	public boolean hasWallAt(MazeCoord loc) {

		int row = loc.getRow();
		int col = loc.getCol();
		// if the wall is present then returns true
		if (mazeCoord[row][col] == true)
			return true;
		else
			return false;
	}

	/**
	 * Returns the entry location of this maze.
	 */
	public MazeCoord getEntryLoc() {
		return new MazeCoord(start.getRow(), start.getCol());
	}

	/**
	 * Returns the exit location of this maze.
	 */
	public MazeCoord getExitLoc() {
		return new MazeCoord(end.getRow(), end.getCol());
	}

	/**
	 * Returns the path through the maze. First element is start location, and
	 * last element is exit location. If there was not path, or if this is
	 * called before a call to search, returns empty list.
	 * 
	 * @return the maze path
	 */
	public LinkedList<MazeCoord> getPath() {

		// returns the path which is already searched or empty if not yet
		// searched
		return path;

	}

	/**
	 * Find a path from start location to the exit location (see Maze
	 * constructor parameters, startLoc and exitLoc) if there is one. Client can
	 * access the path found via getPath method.
	 * 
	 * @return whether a path was found.
	 */
	public boolean search() {
		// 1 added as the pseudo wall is present
		MazeCoord startnew = new MazeCoord(start.getRow() + 1, start.getCol() + 1);
		// 1 added as the pseudo wall is present
		MazeCoord endnew = new MazeCoord(end.getRow() + 1, end.getCol() + 1);
		//end coordinate is a wall
		if(newMaze[end.getRow()+1][end.getCol()+1]==true)
			return false;
		
		if (isSearched == false) {
			if (SearchHelper(startnew, endnew) == true) {
				isSearched = true;
				return true;
			}
		} else {
			return isSearched;
		}

		return false;

	}

	private boolean SearchHelper(MazeCoord start, MazeCoord end) {
		// output true if the start==end
		if (start.equals(end)) {

			return true;
		}
		// if the path is already visited then return false
		if (visited[start.getRow()][start.getCol()] == true) {

			path.remove(start);
			return false;
		}
		// if the path has walls return false
		if (newMaze[start.getRow()][start.getCol()] == true)
			return false;
		
		
		// set the current path coordinate to visited
		visited[start.getRow()][start.getCol()] = true;
		// search for all the possible directions.ie first down, then up, then
		// right and left recursively
		// one of the below recursion will give the output path if present
		if (SearchHelper(new MazeCoord(start.getRow() + 1, start.getCol()), end)) {
			path.add(new MazeCoord(start.getRow() - 1, start.getCol() - 1));
			return true;
		}
		if (SearchHelper(new MazeCoord(start.getRow(), start.getCol() + 1), end)) {
			path.add(new MazeCoord(start.getRow() - 1, start.getCol() - 1));
			return true;
		}
		if (SearchHelper(new MazeCoord(start.getRow() - 1, start.getCol()), end)) {
			path.add(new MazeCoord(start.getRow() - 1, start.getCol() - 1));
			return true;
		}

		if (SearchHelper(new MazeCoord(start.getRow(), start.getCol() - 1), end)) {
			path.add(new MazeCoord(start.getRow() - 1, start.getCol() - 1));
			return true;
		}
		return false;
	}

}