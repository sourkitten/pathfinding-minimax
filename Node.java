
public class Node
{
	private char player;
	private char[][] board;
	private int M, N, K, depth;
	public int lastMove;
	private boolean max;
	
	
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
	
	// TODO KEKW
	//public boolean isWinningMove(int move)
	//{
	//	return Check;
	//}

	public Node(char[][] board, int m, int n, int k, int depth, boolean max)
	{
		if (max) {
			player = 'O';
		} else {
			player = 'X';
		}
		//this.board = board.clone();
		M = m;
		N = n;
		K = k;
		CopyBoard(board);
		this.depth = depth;
		this.max = max;
	}
	
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
	 * Checks if there is a victory in the latest move of a column
	 * Calls the respective Check*Direction* functions
	 * @param  depth how far it should reach
	 * @param  col the column of the player's position
	 * @return true if there is a Victory
	 */
	public boolean CheckVictory(int depth, int col)
	{
		int playerRow = 0;
		// Find highest placed spot in row
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
	 * Runs the MiniMax algorithm
	 * Lose		  : -1
	 * Draw		  :  0
	 * No Outcome :  0
	 * Win		  :  1
	 * @param currentDepth the current depth. Reduced by one after every branch
	 * @return the best / worst child node (depending on the player)
	 */
	public int MiniMax(int currentDepth)
	{
		// Check if player is victorious
		if (CheckVictory(depth, lastMove))
		{
			if (max) {
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
		
		// Get children nodes' scores
		int[] childrenResults = new int[children.length];
		for (int i = 0; i < children.length; i++)
		{
			Node child = new Node(board, M, N, K, depth, !max);
			child.AddToRow(children[i]);
			child.lastMove = children[i];
			childrenResults[i] = child.MiniMax(currentDepth - 1);
			
		}
		
		// Compare children nodes' scores and return the best (depending on the player)
		int value;
		if (max) {
			value = -10;
		} else {
			value = 10;
		}
		for (int i = 0; i < childrenResults.length; i++)
		{
			if (max && childrenResults[i] > value)
			{
				value = childrenResults[i];
			} else if ((!max) && childrenResults[i] < value) {
				value = childrenResults[i];
			}
		}
		
		// TODO return branch NOT VALUE
		return value;
	}
	
}
