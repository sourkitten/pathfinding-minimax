import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;

public class Exercise1 {

	static int N = 5;
	static double p = 0.2;

	private static Coords robot = new Coords();
	private static int[][] board = new int[N][N];

	private static Random rand = new Random();
	
	private static void makeBoard()
	{
		for (int x = 0; x < N; x++)
		{
			for (int y = 0; y < N; y++)
			{
				int r = (rand.nextInt(1000) % (int) (1/p));
				if (r == 0) // randomly occupy slots ( 0 )
					board[x][y] = 0; 
	    		else		// empty slots get assigned a random value 1-4
	    			board[x][y] = rand.nextInt(4) + 1;
	    	}
	  	}
	}

	private static int boardGet(int X, int Y)
	{
		return board[X][Y];
	}

	private static void printBoard()
	{
		for (int x = 0; x < N; x++)
	    {
	   		for (int y = 0; y < N; y++)
	    	{
				if ((robot.X == x) && (robot.Y == y)) {
					System.out.print("X "); // shows current robot position
				} else {
					System.out.print(boardGet(x, y) + " ");
				}
			}
		System.out.println();
		
		}

		System.out.println("X value: " + boardGet(robot.X, robot.Y) + "\n");
	}

	
	private static void printBoard(Coords[] finalStates)
	{
		int finalStateValue = 0;
		for (int x = 0; x < N; x++)
	    {
	   		for (int y = 0; y < N; y++)
	    	{
	   			// check if its a final state
	   			boolean finalState = false;
	   			for (int i = 0; i < finalStates.length; i++)
	   			{
	   				if ( (finalStates[i].X == x) && (finalStates[i].Y == y) )
	   				{
	   					finalState = true;
	   					finalStateValue = boardGet(x, y);
	   				}
	   			}
				if ((robot.X == x) && (robot.Y == y)) {
					System.out.print("X "); // shows current robot position
				} else if (finalState) {
					System.out.print("F "); // shows final state position
	    		} else {
					System.out.print(boardGet(x, y) + " ");
				}
			}
		System.out.println();
		
		}

		System.out.println("X value: " + boardGet(robot.X, robot.Y));
		System.out.println("F value: " + finalStateValue + "\n");
	}
	

	/* calculates the move cost from a position to another position {X,Y}
	 * 
	 * WARNING!!! DO NOT USE IN FUNCTION OR IT WON'T BEHAVE PROPERLY
	 * Source: Trust me bro
	 */
	private static float moveCost(int X, int Y)
	{
		float cost = Math.abs( (boardGet(robot.X, robot.Y) - boardGet(X, Y)) );

		//System.out.println(cost);

		if ((robot.X != X) & (robot.Y != Y)){
			cost += 0.5;
		}
		else
			cost++;

		return cost;
	}
	
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

	// moves the robot from current position to next position {X,Y} 
	private static boolean moveRobot(int X, int Y)
	{
		boolean legalmove = true;

		// Check if move is legal
		if ( (Math.abs(robot.X - X) > 1) || (Math.abs(robot.Y - Y) > 1) )
			legalmove = false; // move is longer than one square away in either axis
		else if ( boardGet(X, Y) == 0 )
			legalmove = false; // next block is NOT free

		// Move if legal
		if ( legalmove )
		{
			robot.X = X;
			robot.Y = Y;
		} else {
			System.err.println("Move from {" + robot.X + "," + robot.Y + "}:" + boardGet(robot.X, robot.Y) + " to {" + X + "," + Y + "}:" + boardGet(X, Y) + " illegal!");
		}
		
		return legalmove;
	}

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
	
	private static String printPath(ArrayList<Coords> path)
	{
		String toString = "";
		for (int i = 0; i < path.size(); i++)
		{
			toString += path.get(i).toString() + "\n";
		}
		return toString;
	}
	
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
	
	// UCS search algorithm
	private static ArrayList<Coords> UCS(Coords finalStates[])
	{
		boolean notFinished = true;
		ArrayList<ArrayList<Coords>> activePaths = new ArrayList<ArrayList<Coords>>();
		ArrayList<ArrayList<Coords>> finalPaths = new ArrayList<ArrayList<Coords>>();
		ArrayList<Coords> finalPath = null;
		
		// create initial node
		ArrayList<Coords> path = new ArrayList<Coords>();
		path.add(robot); robot.cost = 0;
		activePaths.add(path);
		
		ArrayList<Coords> visitedNodes = new ArrayList<Coords>();
		
		float lowestCost = 500000;
		int lowestCostIndex = -1;
		
		
		
		while (notFinished)
		{
			// Find cheapest active path
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
			
			// Extend that path to its respective children
			// TODO OutOfBounds Exception
			ArrayList<Coords> currentPath = activePaths.get(lowestCostIndex);
			Coords lastNode = currentPath.get(currentPath.size() - 1);
			ArrayList<Coords> nearestBlocks = getNearestFreeBlocks(lastNode);
			
			for (int j = 0; j < nearestBlocks.size(); j++) {
				if (!isInPathList(visitedNodes, nearestBlocks.get(j)))
				{
					activePaths.add(extendPath(currentPath, nearestBlocks.get(j)));
				
					// If node is a final state
					boolean isFinalState = false;
					for (int k = 0; k < finalStates.length; k++)
					{
						if ( (nearestBlocks.get(j).X == finalStates[k].X) && (nearestBlocks.get(j).Y == finalStates[k].Y) )
						{
							isFinalState = true;
							break;
						}
					}
					
					if (isFinalState)
					{
						finalPaths.add(activePaths.get(activePaths.size() - 1));
					}
				}
			}
			
			visitedNodes.add(lastNode); // Add parent node to visited list
			activePaths.remove(lowestCostIndex); // Remove parent node
			
			if (!finalPaths.isEmpty())
			{
				float finalLowestCost = 500000;
				for (int i = 0; i < finalPaths.size(); i++)
				{
					ArrayList<Coords> currPath = finalPaths.get(i);
					if (currPath.get(currPath.size() - 1).cost < finalLowestCost) 
					{
						finalLowestCost = currPath.get(currPath.size() - 1).cost;
					}
				}
				
				notFinished = false;
				for (int i = 0; i < activePaths.size(); i++)
				{
					ArrayList<Coords> currPath = activePaths.get(i);
					if (currPath.get(currPath.size() - 1).cost < finalLowestCost) 
					{
						notFinished = true;
					}
				}
			}
		}
		
		float finalLowestCost = 500000;
		for (int i = 0; i < finalPaths.size(); i++)
		{
			ArrayList<Coords> currPath = finalPaths.get(i);
			if (currPath.get(currPath.size() - 1).cost < finalLowestCost) 
			{
				finalLowestCost = currPath.get(currPath.size() - 1).cost;
				finalPath = currPath;
			}
		}
		
		return finalPath;
	}

	public static void main(String[] args)
	{
		makeBoard();
		printBoard();
		robot.setCoords(0, 0);
		System.out.println(printPath(getNearestFreeBlocks(robot)));
		System.out.println("Cost: " + moveCost(1, 1) + " | " + moveRobot(1, 1));
		
		Coords[] finalStates = new Coords[2];
		finalStates[0] = new Coords(3, 3, 0);
		finalStates[1] = new Coords(3, 3, 0);
		
		printBoard(finalStates);
		System.out.println(printPath(getNearestFreeBlocks(robot)));
		
		ArrayList<Coords> path = UCS(finalStates);
		
		System.out.println(printPath(path));
		
	}

}
