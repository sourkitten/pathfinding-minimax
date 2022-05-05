import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;

public class Exercise1 {

	static int N = 5;
	static double p = 0.2;

	Coords robot;
	int[][] board;

	Random rand = new Random();
	
	void makeBoard()
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

	int boardGet(int X, int Y)
	{
		return board[X][Y];
	}

	void printBoard()
	{
		for (int x = 0; x < N; x++)
	    {
	   		for (int y = 0; y < N; y++)
	    	{
				if ((robot.X == x) && (robot.Y == y)) {
					System.out.println("X "); // shows current robot position
				} else {
					System.out.println(boardGet(x, y) );
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
	float moveCost(int X, int Y)
	{
		float cost = Math.abs( (boardGet(robot.X, robot.Y) - boardGet(X, Y)) );

		System.out.println(cost);

		if ((robot.X != X) & (robot.Y != Y)){
			cost += 0.5;
		}
		else
			cost++;

		return cost;
	}

	// moves the robot from current position to next position {X,Y} 
	boolean moveRobot(int X, int Y)
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

	void addElement(int activePaths)
	{
		;
	}

	// UCS search algorithm
	void UCS(Coords finalStates[])
	{
		ArrayList<Coords> path = new ArrayList<Coords>();
		ArrayList<ArrayList<Coords>> activePaths = new ArrayList<ArrayList<Coords>>();
		//int path = (int *) malloc(N*N * sizeof(struct Coords));
		//int** activePaths = (int **) malloc(5 * sizeof(int *));

	}

	int main(int argc, char argv[])
	{
		makeBoard();
		printBoard();

		float cost = moveCost(1, 1);
		System.out.println("Cost: " + cost + " | " + moveRobot(1, 1));
		printBoard();

		return 0;
	}

}
