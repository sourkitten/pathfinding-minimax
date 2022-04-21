#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

int  main()
{
    double p = 0.8;
    srand(time(NULL));
    double r = fmod(rand(), 1/p);
    printf("%f\n", r);
    int res = (r == 0.0);
    printf("%d\n", res);
}