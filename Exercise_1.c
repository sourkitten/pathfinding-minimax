#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define N 5
#define p 0.2

int* make_board(int board[N*N])
{
	srand(time(NULL));
	for (int x = 0; x < N; x++)
	{
		for (int y = 0; y < N; y++)
		{
			int r = (rand() % (int) (1/p));
			if (r == 0) // randomly occupy slots ( 0 )
				board[N*x + y] = 0; 
    		else		// empty slots get assigned a random value 1-4
    			board[N*x + y] = (rand() % 4) + 1;
    	}
  	}
  	return board;
}

void print_board(int *board, int current[2])
{
	for (int x = 0; x < N; x++)
    {
   		for (int y = 0; y < N; y++)
    	{
			if ((current[0] == x) && (current[1] == y)) {
				printf("X "); // shows current robot position
			} else {
				printf("%d ", *(board + (N*x + y)) );
			}
		}
	printf("\n");
	}

	printf("X value: %d\n\n", *(board + (N*current[0] + current[1])) );
}

float move_robot(int *board, int current[2], int next[2])
{
	int legalmove = 1;

	// Check if move is legal
	if ( (abs(current[0] - next[0]) > 1) || (abs(current[1] - next[1]) > 1) )
		legalmove = 0; // move is longer than one square away in either axis
	else if ( *(board + (N*next[0] + next[1])) == 0 )
		legalmove = 0; // next block is NOT free


	// Check if move is diagonal
	float cost = abs( *(board + (N*current[0] + current[1])) - *(board + (N*next[0] + next[1])) );
	if ((current[0] != next[0]) && (current[1] != next[1]))
		cost += 0.5;
	else
		cost++;
	

	// Move if legal
	if ( legalmove )
	{
		current[0] = next[0];
		current[1] = next[1];
	} else {
		fprintf(stderr, "Move from {%d,%d}:%d to {%d,%d}:%d illegal!\n"
				,current[0] ,current[1] ,*(board + (N*current[0] + current[1]))
				,next[0]	,next[1]	,*(board + (N*next[0] + next[1])) );
		fflush(stderr);
	}

	return cost;
}

int main(int argc, char const *argv[])
{
	int current[] = {0, 0};
	int brd[N*N];

	int* board = make_board(brd);
	print_board(board, current);

	int next[] = {1, 2};
	printf("Cost: %f\n", move_robot(board, current, next));
	print_board(board, current);

	return 0;
}
