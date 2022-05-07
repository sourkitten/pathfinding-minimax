public class Coords
{
	public int X;
	public int Y;
	public float cost = 0;
	public float heuristicCost = 0;
	
	public Coords(int X, int Y, float moveCost) {
		this.X = X;
		this.Y = Y;
		this.cost = moveCost;
	}

	public Coords(int X, int Y, float moveCost, float heuristicCost) {
		this.X = X;
		this.Y = Y;
		this.cost = moveCost;
		this.heuristicCost = heuristicCost;
	}
	
	public Coords() {}

	public Coords(int X, int Y)
	{
		this.X = X;
		this.Y = Y;
	}
	
	public void setCoords(int X, int Y)
	{
		this.X = X;
		this.Y = Y;
	}
	
	public String toString(boolean isAStar)
	{
		if (isAStar)
		{
			return "{" + X + ", " + Y + ", " + cost + ", " + heuristicCost + "}";
		} else {
			return "{" + X + ", " + Y + ", " + cost + "}";
		}
		
	}
}


/*  1  1  1  1  1  
	4  0  0  0  1  
	1  0  0  0  1  
	4  0  0  0  1  
	G1 0  0  0  1  
 * 
 * 0,1
 * 3,3
 * 
 * 3,2
 * 
 * 2 diag
 * 1 oriz
 * 
 * 2*0.5
 * 1*1
 * 
 */
