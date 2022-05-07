public class Coords
{
	public int X;
	public int Y;
	public float cost = -1;
	public float heuristicCost = -1;
	
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
	
	@Override
	public String toString() {
		return "{" + X + ", " + Y + ", " + cost + "}";
	}
}


/*  1  3  0  2  2  
	1  2  0  4  0  
	4  0  3  1  4  
	0  1  4  G1 2  
	G2 4  4  2  4  
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
