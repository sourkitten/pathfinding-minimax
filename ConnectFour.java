/* BALLOS   EVANGELOS    4739
 * GKIOULIS KONSTANTINOS 4654
 */

import java.util.Random;
import java.util.Scanner;
import java.lang.Math;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class ConnectFour
{
	private static int M, N;
	// Storing array as [col][row]
	private static char[][] board;
	
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
	 * Prints the board
	 */
	public static void PrintBoard()
	{
		// Hahaha String manipulation go brrr
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
		// Find lowest empty spot in row
		for (int i = M - 1; i >= 0; i--)
		{
			if (board[col][i] == 'E')
			{
				board[col][i] = pIndic;
			return true;
			}
		}
		System.err.println("Invalid Move!!");
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
	 * Checks if there is a victory in latest move
	 * @param  pIndic the player indicator
	 * @param  depth how long should the check be
	 * @param  col the column index
	 * @return true if there is a Victory
	 */
	public static boolean CheckVictory(char pIndic, int depth, int col)
	{
		char playerCheckIndicator = 'n';
		int playerCheckIndicatorIndex = 0;
		// Find last placed spot in row
		for (int i = 0; i < M; i++)
		{
			if (board[col][i] != 'E')
			{
				playerCheckIndicator = board[col][i];
				playerCheckIndicatorIndex = i;
				break;
			}
		}
		
		// TODO check towards left AND right
		
		boolean canGoLeft = depth > col;
		boolean canGoUp = depth > playerCheckIndicatorIndex;
		boolean canGoRight = depth > M - col;
		boolean canGoDown = depth > N - playerCheckIndicatorIndex;
		
		int[][] counter = new int[3][3];
		for (int i = 0; i < 3; i++)
		{
			Arrays.fill(counter[i], 0);
		}
		
		counter[1][1] = 0; // middle space
		for (int i = 0; i < depth; i++) {
			if (canGoLeft)
			{
				if (board[col-i][playerCheckIndicatorIndex] == playerCheckIndicator)
				{
					counter[0][1]++;
				} else {
					counter[0][1]--;
				}
			}
			if (canGoRight)
			{
				if (board[col+i][playerCheckIndicatorIndex] == playerCheckIndicator)
				{
					counter[2][1]++;
				} else {
					counter[2][1]--;
				}
			}
			if (canGoUp)
			{
				if (board[col][playerCheckIndicatorIndex-i] == playerCheckIndicator)
				{
					counter[1][0]++;
				} else {
					counter[1][0]--;
				}
			}
			if (canGoDown)
			{
				if (board[col][playerCheckIndicatorIndex+i] == playerCheckIndicator)
				{
					counter[1][2]++;
				} else {
					counter[1][2]--;
				}
			}
			
			if (canGoLeft && canGoUp)
			{
				if (board[col-i][playerCheckIndicatorIndex-i] == playerCheckIndicator)
				{
					counter[0][0]++;
				} else {
					counter[0][0]--;
				}
			}
			if (canGoRight && canGoUp)
			{
				if (board[col+i][playerCheckIndicatorIndex-i] == playerCheckIndicator)
				{
					counter[2][0]++;
				} else {
					counter[2][0]--;
				}
			}
			if (canGoLeft && canGoDown)
			{
				if (board[col-i][playerCheckIndicatorIndex+i] == playerCheckIndicator)
				{
					counter[0][2]++;
				} else {
					counter[0][2]--;
				}
			}
			if (canGoRight && canGoDown)
			{
				if (board[col+i][playerCheckIndicatorIndex+i] == playerCheckIndicator)
				{
					counter[2][2]++;
				} else {
					counter[2][2]--;
				}
			}
		}
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (counter[i][j] == depth)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void main(String[] Args)
	{

		Scanner in = new Scanner(System.in);
		
		System.out.print("Input the length of the board (N): ");
		N = in.nextInt();
		System.out.print("Input the height of the board (M): ");
		M = in.nextInt();
		System.out.println();
		MakeBoard();

		//board[2][3] = 'O';
		AddToRow('O', 3);
		AddToRow('O', 3);
		AddToRow('X', 3);
		AddToRow('O', 2);
		AddToRow('O', 3);
		AddToRow('O', 4);
		PrintBoard();
		System.out.println(CheckVictory('O', 3, 2));
		//PrintRow(2);
		
		in.close();
	}
}
