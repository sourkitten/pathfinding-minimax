package Lab2;
/* BALLOS   EVANGELOS    4739
 * GKIOULIS KONSTANTINOS 4654
 */


/**
 * A simple Class that represents a Node in the MiniMax tree.
 * Here is the true implementation of the MiniMax algorithm.
 * @author cs04739
 * @author cs04654
 */
public class Node
{
	
	private char player;
	private char[][] board;
	private int M, N, K;
	public int lastMove;
	private boolean max;
	private boolean isParent = false;
	
	
	/**
	 * Mutator to the respective variable.
	 * Gets used by the ConnectFour.BestMove() method
	 * to set the parent node status boolean.
	 * @param parentStatus whether the node is a parent or not
	 */
	public void SetParentNode(boolean parentStatus)
	{
		isParent = parentStatus;
	}
	
	
	/**
	 * Adds player indicator to
	 * the lowest empty point of a row.
	 * Returns success status.
	 * @param  pIndic the player indicator
	 * @param  col the column index
	 * @return success status
	 */
	public boolean AddToRow(int col)
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
				board[col][i] = player;
			return true;
			}
		}
		System.err.println("Board Filled! Please input another move: ");
		return false;
	}
	
	
	/**
	 * Get the valid moves from the board
	 * @return array of the valid moves ids (empty column ids)
	 */
	public int[] validMoves()
	{
		boolean[] isValidMove = new boolean[N];
		int validMoveCounter = 0;
		for (int col = 0; col < N; col++) 
		{
			if (board[col][0] == 'E') {
				isValidMove[col] = true;
				validMoveCounter++;
			}
			
		}
		
		int[] validMoves = new int[validMoveCounter];
		validMoveCounter = 0;
		for (int row = 0; row < isValidMove.length; row++) 
		{
			if (isValidMove[row])
			{
				validMoves[validMoveCounter] = row;
				validMoveCounter++;
			}
		}
		return validMoves;
	}
	
	
	/**
	 * Node Constructor.
	 * All variables get passed through from parent Node,
	 * with max boolean reversed.
	 */
	public Node(char[][] board, int m, int n, int k, boolean max)
	{
		if (!max) {
			player = 'O';
		} else {
			player = 'X';
		}
		//this.board = board.clone();
		M = m;
		N = n;
		K = k;
		CopyBoard(board);
		this.max = max;
	}
	
	
	/**
	 * Deep copies the parent board to the current object board
	 * @param oldBoard board to be copied
	 */
	public void CopyBoard(char[][] oldBoard)
	{
		board = new char[N][M];
		for (int y = 0; y < M; y++)
		{
			for (int x = 0; x < N; x++)
			{
				board[x][y] = oldBoard[x][y];
			}
		}
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
	public boolean CheckHorizontal(int row, int depth, int col)
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
	public boolean CheckVertical(int row, int depth, int col)
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
	public boolean CheckDiagonalSlash(int row, int depth, int col)
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
	public boolean CheckDiagonalBackslash(int row, int depth, int col)
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
	 * Checks if there is a victory in the latest move of a column (highest element in column)
	 * Calls the respective Check*Direction* functions
	 * @param  depth how far it should reach
	 * @param  col the column of the player's position
	 * @return true if there is a Victory
	 */
	public boolean CheckVictory(int depth, int col)
	{
		int playerRow = 0;
		// Find highest placed spot in column
		for (int i = 0; i < M; i++)
		{
			if (board[col][i] != 'E')
			{
				playerRow = i;
				break;
			}
		}
		
		if (CheckHorizontal(playerRow, depth, col))
		{
			return true;
		} else if (CheckVertical(playerRow, depth, col))
		{
			return true;
		} else if (CheckDiagonalSlash(playerRow, depth, col))
		{
			return true;
		} else if (CheckDiagonalBackslash(playerRow, depth, col))
		{
			return true;
		} 
		
		return false;
	}

	
	/**
	 * Implementation of the MiniMax algorithm
	 * 
	 * MiniMax values are:
	 * Lose		  : -1
	 * Draw		  :  0
	 * No Outcome :  0
	 * Win		  :  1
	 * @param currentDepth the current depth. Reduced by one after every level
	 * @return the min / max child node value (move number for the parent)
	 */
	public int MiniMax(int currentDepth)
	{
		// Check if move from the !!PARENT NODE!! is victorious
		if (CheckVictory(K, lastMove))
		{
			if (!max) {
				return 1;
			} else {
				return -1;
			}
		}
		
		// Check if there are any valid moves
		int[] children = validMoves();
		if (currentDepth == 0 || children.length == 0)
		{
			return 0;
		}
		
		// Separate valid moves into child nodes - branches and retrieve their scores
		int[] childrenResults = new int[children.length];
		for (int i = 0; i < children.length; i++)
		{
			Node child = new Node(board, M, N, K, !max);
			child.AddToRow(children[i]);
			child.lastMove = children[i];
			childrenResults[i] = child.MiniMax(currentDepth - 1);
			
		}
		
		// Compare children nodes' scores and find the min / max (depending on the player)
		int value, childIndex = 0;
		if (max) {
			value = -10;
		} else {
			value = 10;
		}
		for (int i = 0; i < childrenResults.length; i++)
		{
			if (isParent && (childrenResults[i] > value))
			{
				value = childrenResults[i];
				childIndex = children[i];
			}
			else if (!isParent && max && (childrenResults[i] > value))
			{
				value = childrenResults[i];
				childIndex = children[i];
			}
			else if (!isParent && !max && (childrenResults[i] < value))
			{
				value = childrenResults[i];
				childIndex = children[i];
			}
		}

		
		
		if (isParent)
		{
			return childIndex; // return best move index if parent
		} else {
			return value; // return value if child / branch
		}
	}
	
}
