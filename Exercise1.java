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
				if (!( ((x == 0) && (y == 0)) || ((absx < 0) || (absy < 0) || (absy > N) || (absy > N)) )) {
					if (boardGet(absx, absy) != 0) {
						nearestBlocks.add(new Coords(absx, absy, moveCost(curr, absx, absy)));
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
	
	// UCS search algorithm
	private static void UCS(Coords finalStates[])
	{
		
		ArrayList<ArrayList<Coords>> activePaths = new ArrayList<ArrayList<Coords>>();
		
		// create initial node
		ArrayList<Coords> path = new ArrayList<Coords>();
		path.add(robot); robot.cost = 0;
		activePaths.add(path);
		
		for (int i = 0; i < activePaths.size(); i++)
		{
			activePaths.get(i);
		}
		
	}

	public static void main(String[] args)
	{
		makeBoard();
		printBoard();
		robot.setCoords(0, 0);
		System.out.println(printPath(getNearestFreeBlocks(robot)));
		System.out.println("Cost: " + moveCost(1, 1) + " | " + moveRobot(1, 1));
		printBoard();
		System.out.println(printPath(getNearestFreeBlocks(robot)));
	}

}
