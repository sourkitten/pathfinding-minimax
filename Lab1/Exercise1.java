package Lab1;
/* BALLOS   EVANGELOS    4739
 * GKIOULIS KONSTANTINOS 4654
 */


import java.util.Random;
import java.util.Scanner;
import java.lang.Math;
import java.util.ArrayList;

/**
 * An implementation of the UCS and A* search algorithms.
 * @author cs04739
 * @author cs04654
 */
public class Exercise1 {

	private static int N;
	private static int p;

	private static Coords robot = new Coords();
	private static int[][] board;

	private static Random rand = new Random();
	
	
	/**
	 * Creates the board with each block having a p% chance of being a wall (0)
	 * and if not a wall they have a value ranging from 1 to 4. Think of it like height
	 * @param initialState the robot's initial state
	 * @param finalStates the final - goal states
	 */
	private static void makeBoard(Coords initialState, Coords[] finalStates)
	{
		board = new int[N][N];
		for (int x = 0; x < N; x++)
		{
			for (int y = 0; y < N; y++)
			{
				// Check if tile is initial or final state
				// !! This ensures that the initial and final state tiles WON'T be occupied
				boolean isInitialOrFinalState = (initialState.X == x) && (initialState.Y == y);
				for (int i = 0; i < finalStates.length; i++)
				{
					if ( (finalStates[i].X == x) && (finalStates[i].Y == y) )
					{
						isInitialOrFinalState = true;
					}
				}

				int r = (rand.nextInt(100));
				if (r > p || isInitialOrFinalState) // empty slots get assigned a random value 1-4
					board[x][y] = rand.nextInt(4) + 1;
	    		else								 // randomly occupy slots ( 0 )
	    			board[x][y] = 0; 
	    	}
	  	}
	}
	
	
	/**
	 * This method has remained from when the project was written in C,
	 * and the 2d array was set with malloc().
	 * Some other methods still use it, so why change it?
	 */
	private static int boardGet(int X, int Y)
	{
		return board[X][Y];
	}
	
	
	/**
	 * Prints the board layout, including the robot and final states
	 * @param finalStates the final - goal states
	 */
	private static void printBoard(Coords[] finalStates)
	{
		int finalStateValues[] = new int[finalStates.length];
		int finalStateCounter  = 0;
		
		for (int y = 0; y < N; y++)
	    {
	   		for (int x = 0; x < N; x++)
	    	{
	   			// check if its a final state
	   			boolean finalState = false;
	   			for (int i = 0; i < finalStates.length; i++)
	   			{
	   				if ( (finalStates[i].X == x) && (finalStates[i].Y == y) )
	   				{
	   					finalState = true;
	   					finalStateValues[finalStateCounter] = boardGet(x, y);
	   					finalStateCounter++;
	   				}
	   			}
				if ((robot.X == x) && (robot.Y == y)) {
					System.out.print("X  "); // shows current robot position
				} else if (finalState) {
					System.out.print("G" + (finalStateCounter) + " "); // shows final state position
	    		} else {
					System.out.print(boardGet(x, y) + "  ");
				}
			}
		System.out.println();
		
		}

		String out = "X val: " + boardGet(robot.X, robot.Y) + ", ";
		for (int i = 0; i < finalStateValues.length; i++)
		{
			out += "G" + (i+1) + " val: " + finalStateValues[i] + "";
			if ( i == (finalStateValues.length - 2) ) {
				out += ", ";
			}
		}
		System.out.println(out);
		
	}
	
	
	/**
	 * Calculates the move cost between two positions
	 * @param initial the initial position
	 * @param X of the next move's position
	 * @param Y of the next move's position
	 * @return the cost of the move
	 */
	private static float moveCost(Coords initial, int X, int Y)
	{
		float cost = Math.abs( (boardGet(initial.X, initial.Y) - boardGet(X, Y)) );

		//System.out.println(cost);

		if ((initial.X != X) & (initial.Y != Y)){
			cost += 0.5;
		}
		else
			cost++;

		return cost;
	}

	
	/**
	 * Gets the neighboring free blocks (not walls) from a set of coordinates
	 * @param curr the coordinates that the neighboring free blocks derive from
	 * @return an ArrayList with the neighboring blocks (as Coords objects)
	 */
	private static ArrayList<Coords> getNearestFreeBlocks(Coords curr)
	{
		ArrayList<Coords> nearestBlocks = new ArrayList<Coords>();
		for (int x = -1; x < 2; x++)
		{
			for (int y = -1; y < 2; y++)
			{
				// Node not ( "self" or "obstacle" or "out of board" )
				int absx = curr.X + x; int absy = curr.Y + y;
				if (!( ((x == 0) && (y == 0)) || ((absx < 0) || (absy < 0) || (absx >= N) || (absy >= N)) )) {
					if (boardGet(absx, absy) != 0) {
						// Add previous node's cost to new nodes
						nearestBlocks.add(new Coords(absx, absy, curr.cost + moveCost(curr, absx, absy)));
					}
				}
			}
		}
		
		return nearestBlocks;
	}
	
	
	/**
	 * Prints a given path, used to print the path that UCS and A* return
	 * @param path the path to be printed
	 * @param isAStar whether heuristic cost is returned as well or not
	 * @return a string with the path. Each node/step is separated by "\n"
	 */
	private static String printPath(ArrayList<Coords> path, boolean isAStar)
	{
		String toString = "";
		if (isAStar) {
			toString = "\n{X, Y, cost, heuristic}";
		} else {
			toString = "\n{X, Y, cost}";
		}
		
		for (int i = 0; i < path.size(); i++)
		{
			toString += "\n" + path.get(i).toString(isAStar);
		}
		return toString;
	}
	
	
	/**
	 * Checks if a set of Coordinates is in an ArrayList.
	 * Used to find whether a node has been visited or not.
	 * @param list ArrayList of Coordinates to check against.
	 * @param toCheck the set that needs to be checked.
	 * @return whether the node has been visited or not.
	 */
	private static boolean isInPathList(ArrayList<Coords> list, Coords toCheck)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if ( (list.get(i).X == toCheck.X) && (list.get(i).Y == toCheck.Y) )
			{
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Returns a deep copy of a path, with the inclusion of a new Node.
	 * Used to extend a path to multiple new paths (for multiple free blocks).
	 * @param oldPath the path to be deep copied.
	 * @param newNode the node that will be added to the copied path.
	 * @return the deep copy of the old path, with the new node added.
	 */
	private static ArrayList<Coords> extendPath(ArrayList<Coords> oldPath, Coords newNode)
	{
		ArrayList<Coords> newPath = new ArrayList<Coords>();
		// Add Nodes from old path to new path
		for (int i = 0; i < oldPath.size(); i++)
		{
			newPath.add(oldPath.get(i));
		}
		newPath.add(newNode);
		return newPath;
	}
	
	
	/**
	 * Implementation of the UCS search algorithm
	 * @param finalStates the final - goal states
	 * @return the best path that can be followed
	 */
	private static ArrayList<Coords> UCS(Coords finalStates[])
	{
		ArrayList<ArrayList<Coords>> activePaths = new ArrayList<ArrayList<Coords>>();
		
		// create initial node
		ArrayList<Coords> path = new ArrayList<Coords>();
		path.add(robot); robot.cost = 0;
		activePaths.add(path);
		
		ArrayList<Coords> visitedNodes = new ArrayList<Coords>();
		
		
		while (true)
		{
			// Check if there are no active paths
			if (activePaths.size() == 0)
			{
				System.err.println("No path to final states!!");
				return new ArrayList<Coords>();
			}

			// Find cheapest active path
			float lowestCost = N*N*5;
			int lowestCostIndex = -1;
			for (int i = 0; i < activePaths.size(); i++)
			{
				ArrayList<Coords> currentPath = activePaths.get(i);
				Coords lastNode = currentPath.get(currentPath.size() - 1);
				
				if (lastNode.cost < lowestCost)
				{
					lowestCost = lastNode.cost;
					lowestCostIndex = i;
				}
			}
			ArrayList<Coords> currentPath = activePaths.get(lowestCostIndex); // cheapest path
			
			// check if last node is final state
			Coords lastNode = currentPath.get(currentPath.size() - 1);
			for (int k = 0; k < finalStates.length; k++)
			{
				if ( (lastNode.X == finalStates[k].X) && (lastNode.Y == finalStates[k].Y) )
				{
					return currentPath;
				}
			}
			
			// check if last node has been visited
			if (!isInPathList(visitedNodes, lastNode))
			{
				// extend last node to its children
				ArrayList<Coords> nearestBlocks = getNearestFreeBlocks(lastNode);
				for (int j = 0; j < nearestBlocks.size(); j++)
				{
					activePaths.add(extendPath(currentPath, nearestBlocks.get(j)));
				}
				
				visitedNodes.add(lastNode); // Add parent node to visited list
			}
			
			activePaths.remove(lowestCostIndex); // Remove parent node
		}
	}

	
	/**
	 * Calculates the heuristic cost from a set of coords to the final state.
	 * The cost derives from the function horizontalMoves + diagonalMoves/2.
	 * @param initial node that the heuristic cost should be calculated from.
	 * @param finalStates the final - goal states.
	 * @return the heuristic cost of the node in question.
	 */
	public static float heuristicCost(Coords initial, Coords[] finalStates)
	{
		// get lowest heuristic cost
		float lowestCost = N*N;
		for (int i = 0; i < finalStates.length; i++)
		{
			int diffX = Math.abs(finalStates[i].X - initial.X);
			int diffY = Math.abs(finalStates[i].Y - initial.Y);
			int diagonalMove;
			
			if (diffX < diffY) {
				diagonalMove = diffX;
			} else if (diffX > diffY) {
				diagonalMove = diffY;
			} else {
				diagonalMove = diffX;
			}
			
			float currentCost = diagonalMove*0.5f + Math.abs(diffX - diffY);
			
			if (currentCost < lowestCost)
			{
				lowestCost = currentCost;
			}
		}
		
		return lowestCost;
	}
	

	/**
	 * Implementation of the A* search algorithm.
	 * @param finalStates the final - goal states
	 * @return the best path that can be followed
	 */
	public static ArrayList<Coords> AStar(Coords[] finalStates)
	{
		ArrayList<ArrayList<Coords>> activePaths = new ArrayList<ArrayList<Coords>>();
		
		// create initial node
		ArrayList<Coords> path = new ArrayList<Coords>();
		path.add(robot); robot.cost = 0;
		robot.heuristicCost = heuristicCost(robot, finalStates);
		activePaths.add(path);
		
		ArrayList<Coords> visitedNodes = new ArrayList<Coords>();
		
		while (true)
		{
			// Check if there are no active paths
			if (activePaths.size() == 0)
			{
				System.err.println("No path to final states!!");
				return new ArrayList<Coords>();
			}
			
			// Find cheapest AND closest to final state active path
			float lowestTotalCost = N*N*5;
			int lowestTotalCostIndex = -1;
			for (int i = 0; i < activePaths.size(); i++)
			{
				ArrayList<Coords> currentPath = activePaths.get(i);
				Coords lastNode = currentPath.get(currentPath.size() - 1);
				
				if ( (lastNode.cost + lastNode.heuristicCost) < lowestTotalCost )
				{
					lowestTotalCost = lastNode.cost + lastNode.heuristicCost;
					lowestTotalCostIndex = i;
				}
			}
			ArrayList<Coords> currentPath = activePaths.get(lowestTotalCostIndex);
			
			// check if last node is final state
			Coords lastNode = currentPath.get(currentPath.size() - 1);
			for (int k = 0; k < finalStates.length; k++)
			{
				if ( (lastNode.X == finalStates[k].X) && (lastNode.Y == finalStates[k].Y) )
				{
					return currentPath;
				}
			}
			
			if (!isInPathList(visitedNodes, lastNode))
			{
				// extend last node to its children
				ArrayList<Coords> nearestBlocks = getNearestFreeBlocks(lastNode);
				for (int j = 0; j < nearestBlocks.size(); j++)
				{
					activePaths.add(extendPath(currentPath, nearestBlocks.get(j)));
				}
				
				visitedNodes.add(lastNode); // Add parent node to visited list
			}
			
			activePaths.remove(lowestTotalCostIndex); // Remove parent node
		}
	}
	
	
	/**
	 * The main game loop
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		System.out.print("Input the size of the array: ");
		N = in.nextInt();
		System.out.print("Input the cell occupation possibility (%): ");
		p = in.nextInt();
		
		System.out.print("Input the robot's position (X): ");
		int X = in.nextInt();
		System.out.print("Input the robot's position (Y): ");
		int Y = in.nextInt();
		robot.setCoords(X, Y);
		
		Coords[] finalStates = new Coords[2];
		System.out.print("Input the first  final state position (X): ");
		X = in.nextInt();
		System.out.print("Input the first  final state position (Y): ");
		Y = in.nextInt();
		finalStates[0] = new Coords(X, Y, 0);
		
		System.out.print("Input the second final state position (X): ");
		X = in.nextInt();
		System.out.print("Input the second final state position (Y): ");
		Y = in.nextInt();
		finalStates[1] = new Coords(X, Y, 0);
		System.out.println();
		
		makeBoard(robot, finalStates);
		printBoard(finalStates);
		
		long startTime = System.nanoTime(); 
		ArrayList<Coords> UCSPath = UCS(finalStates);
		System.out.println(printPath(UCSPath, false));
	    long endTime = System.nanoTime(); 
	    System.out.println("UCS method execution time: " + ((endTime - startTime) / 1000000) + " ms");
	    
		startTime = System.nanoTime(); 
	    ArrayList<Coords> AStarPath = AStar(finalStates);
		System.out.println(printPath(AStarPath, true));
	    endTime = System.nanoTime(); 
	    System.out.println("A* method execution time: " + ((endTime - startTime) / 1000000) + " ms");
		
		in.close(); // close the scanner
	}

}
