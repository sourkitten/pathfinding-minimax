package Lab2;
/* BALLOU   IVI	         4739 
 * GKIOULIS KONSTANTINOS 4654
 */


import java.util.Scanner;
import java.util.Arrays;

/**
 * A game of Connect Four using a simple implementation of a MiniMax algorithm.
 * @author ivi ballou
 * @author gkioulis konstantinos
 */
public class ConnectFour
{
	// Storing array as [col][row]
	private static char[][] board;
	private static int M, N, K, depth;
	private static Scanner in = new Scanner(System.in);
	
	
	/**
	 * Creates the board
	 */
	public static void MakeBoard()
	{
		board = new char[N][M];
		for (int x = 0; x < N ; x++)
		{
			Arrays.fill(board[x], 'E');
		}
	}
	
	
	/**
	 * Prints the board to stdout
	 */
	public static void PrintBoard()
	{
		String out = "";
		for (int y = 0; y < M ; y++)
		{
			for (int x = 0; x < N ; x++)
			{
				out += board[x][y] + " ";
			}
			out += "\n";
		}
		for (int x = 0; x < N ; x++)
		{
			out += x + " ";
		}
		out += "\n";
		System.out.println(out);
	}
	
	
	/**
	 * Adds player indicator to
	 * the lowest empty point of a row.
	 * Returns success status.
	 * @param  pIndic the player indicator
	 * @param  col the column index
	 * @return success status
	 */
	public static boolean AddToRow(char pIndic, int col)
	{
		if (col < 0 || col > N-1) {
			System.err.print("Invalid Move! Please input a valid move: ");
			return false;
		}
		// Find lowest empty spot in row
		for (int i = M - 1; i >= 0; i--)
		{
			if (board[col][i] == 'E')
			{
				board[col][i] = pIndic;
				return true;
			}
		}
		System.err.println("Column Filled! Please input another move: ");
		return false;
	}
	
	/**
	 * Prints a Row to System.out
	 * @param  row the row index
	 */
	public static void PrintRow(int row)
	{
		String out = "";
		for (int i = 0; i < M ; i++)
		{
			out += board[row][i] + "\n";
		}
		out += "\n";
		System.out.println(out);
	}
	
	
	/**
	 * Checks whether there is a victory horizontally
	 * from the player spot
	 * @param player the player indicator
	 * @param row the row of the player's position
	 * @param depth how far it should search
	 * @param col the column of the player's position
	 * @return true if there is a Victory
	 */
	public static boolean CheckHorizontal(char player, int row, int depth, int col)
	{
		
		int totalFound = 1;
		// check towards the left
		for (int i = col-1; i > col-depth-1; i--)
		{
			if (i < 0) { // stop if out of bounds
				break;
			}
			
			if (board[i][row] == player) {
				totalFound++;
			} else {
				break;
			}
			
			if (totalFound >= depth) {
				return true;
			}
		}
		// check towards the right
		for (int i = col+1; i < col+depth; i++)
		{
			if (i > N-1) { // stop if out of bounds
				break;
			}
			
			if (board[i][row] == player) {
				totalFound++;
			} else {
				break;
			}
			
			if (totalFound >= depth) {
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * Checks whether there is a victory vertically
	 * from the player spot
	 * @param player the player indicator
	 * @param row the row of the player's position
	 * @param depth how far it should search
	 * @param col the column of the player's position
	 * @return true if there is a Victory
	 */
	public static boolean CheckVertical(char player, int row, int depth, int col)
	{

		int totalFound = 1;
		// check upwards
		for (int i = row-1; i > row-depth-1; i--)
		{
			if (i < 0) { // stop if out of bounds
				break;
			}
				
			if (board[col][i] == player) {
				totalFound++;
			} else {
				break;
			}
			
			if (totalFound >= depth) {
				return true;
			}
		}
		// check downwards
		for (int i = row+1; i < row+depth; i++)
		{
			if (i > M-1) { // stop if out of bounds
				break;
			}
			
			if (board[col][i] == player) {
				totalFound++;
			} else {
				break;
			}
			
			if (totalFound >= depth) {
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * Checks whether there is a victory diagonally (slash)
	 * from the player spot. Goes from left-up to right-down
	 * @param player the player indicator
	 * @param row the row of the player's position
	 * @param depth how far it should search
	 * @param col the column of the player's position
	 * @return true if there is a Victory
	 */
	public static boolean CheckDiagonalSlash(char player, int row, int depth, int col)
	{

		int totalFound = 1;
		// check left - upwards
		for (int i = 1; i < depth; i++)
		{
			if (col-i < 0 || row-i < 0) { // stop if out of bounds
				break;
			}
				
			if (board[col-i][row-i] == player) {
				totalFound++;
			} else {
				break;
			}
			
			if (totalFound >= depth) {
				return true;
			}
		}
		// check right - downwards
		for (int i = 1; i < depth; i++)
		{
			if (col+i > N-1 || row+i > M-1) { // stop if out of bounds
				break;
			}
			
			if (board[col+i][row+i] == player) {
				totalFound++;
			} else {
				break;
			}
			
			if (totalFound >= depth) {
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * Checks whether there is a victory diagonally (backslash)
	 * from the player spot. Goes from left-down to right-up
	 * @param player the player indicator
	 * @param row the row of the player's position
	 * @param depth how far it should search
	 * @param col the column of the player's position
	 * @return true if there is a Victory
	 */
	public static boolean CheckDiagonalBackslash(char player, int row, int depth, int col)
	{
		int totalFound = 1;
		// check right - upwards
		for (int i = 1; i < depth; i++)
		{
			if (col+i > N-1 || row-i < 0) { // stop if out of bounds
				break;
			}
				
			if (board[col+i][row-i] == player) {
				totalFound++;
			} else {
				break;
			}
			
			if (totalFound >= depth) {
				return true;
			}
		}
		// check left - downwards
		for (int i = 1; i < depth; i++)
		{
			if (col-i < 0 || row+i > M-1) { // stop if out of bounds
				break;
			}
			
			if (board[col-i][row+i] == player) {
				totalFound++;
			} else {
				break;
			}
			
			if (totalFound >= depth) {
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * Checks if there is a victory in the latest move of a column
	 * Calls the respective Check*Direction* functions
	 * @param  depth how far it should reach
	 * @param  col the column of the player's position
	 * @return true if there is a Victory
	 */
	public static boolean CheckVictory(int depth, int col)
	{
		char playerIndicator = 'n';
		int playerRow = 0;
		// Find highest placed spot in row
		for (int i = 0; i < M; i++)
		{
			if (board[col][i] != 'E')
			{
				playerIndicator = board[col][i];
				playerRow = i;
				break;
			}
		}
		
		if (CheckHorizontal(playerIndicator, playerRow, depth, col))
		{
			return true;
		} else if (CheckVertical(playerIndicator, playerRow, depth, col))
		{
			return true;
		} else if (CheckDiagonalSlash(playerIndicator, playerRow, depth, col))
		{
			return true;
		} else if (CheckDiagonalBackslash(playerIndicator, playerRow, depth, col))
		{
			return true;
		} 
		
		return false;
	}
	
	
	/**
	 * Checks if the board is filled.
	 * Essentially it checks the first row only.
	 * @return whether the board is filled
	 */
	public static boolean isBoardFilled()
	{
		// Only the uppermost row needs to be checked
		for (int col = 0; col < M; col++) 
		{
			if (board[col][0] == 'E') {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Calculates the best move for the Robot (MiniMax algorithm).
	 * With the help of the sponsor of this video, MINIMAX!!!
	 * @return the best available move there is for the robot.
	 */
	public static int BestMove()
	{
		long startTime = System.nanoTime();
		Node parent = new Node(board, M, N, K, true);
		parent.SetParentNode(true); 
		int bestMove = parent.MiniMax(depth); // grab best move using minimax
		long endTime = System.nanoTime();
	    System.out.println("Move Calculation Cost: " + ((endTime - startTime) / 1000000) + " ms\n");
		return bestMove;
	}
	
	
	/**
	 * Plays the game, taking turns between the MiniMax (O) and Player (X).
	 */
	public static void PlayGame()
	{
		int nextMove;
		while (true)
		{
			// Human's turn
			System.out.print("Input the column you want to fill: ");
			nextMove = in.nextInt();
			while (!AddToRow('X', nextMove)) {
				nextMove = in.nextInt();
			}
			if (CheckVictory(K, nextMove)) {
				System.out.println("You are victorious");
				break;
			} else if (isBoardFilled()) {
				System.out.println("It's a draw :/");
				break;
			}
			PrintBoard();

			// Robot's turn
			nextMove = BestMove();
			AddToRow('O', nextMove);
			if (CheckVictory(K, nextMove)) {
				System.out.println("You are NOT victorious. The Cyborg wins");
				break;
			} else if (isBoardFilled()) {
				System.out.println("It's a draw :/");
				break;
			}
			PrintBoard();
		}
	}
	
	
	/**
	 * The main game loop
	 */
	public static void main(String[] Args)
	{	
		depth = 7;
		System.out.print("Input the length of the board (N): ");
		N = in.nextInt();
		System.out.print("Input the height of the board (M): ");
		M = in.nextInt();
		System.out.print("Input the amount required to win (K): ");
		K = in.nextInt();
		System.out.println("Current MiniMax depth: " + depth + ".  Cost is: " + M + "^" + depth + ".\n");
		MakeBoard();
		PrintBoard();
		PlayGame();
		
		AddToRow('X', 5);
		AddToRow('O', 3);
		AddToRow('X', 3);
		PrintBoard();
		
		in.close();
	}
}
