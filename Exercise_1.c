#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define N 5
#define p 0.2

/*
int** make_board()
{
  srand(time(NULL));
  int board [N] [N] ;
  for (int i = 0; i < N; i++)
  {
    for (int j = 0; j < N; j++)
    {
      int r = (rand() % (int) (1/p));
      if (r == 0)
      {
        board[i][j] = 0; 
      }
      else
      {
        board[i][j] = (rand() % 4) + 1;
      }
    }
  }
  return board;
}
*/

int* make_board(int board[N*N])
{
  srand(time(NULL));
  for (int i = 0; i < N; i++)
  {
    for (int j = 0; j < N; j++)
    {
      int r = (rand() % (int) (1/p));
      if (r == 0)
      {
        board[N*i + j] = 0; 
      }
      else
      {
        board[N*i + j] = (rand() % 4) + 1;
      }
    }
  }
  return board;
}

void print_board(int *board)
{
  for (int i = 0; i < N; i++)
  {
    for (int j = 0; j < N; j++)
    {
      printf("%d ", *(board + (N*i + j)) );
    }
    printf("\n");
  }

}

int main(int argc, char const *argv[])
{
  int brd[N*N];
  int* board = make_board(brd);
  print_board(board);
  
  return 0;
}
