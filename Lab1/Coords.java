package Lab1;
/* BALLOS   EVANGELOS    4739
 * GKIOULIS KONSTANTINOS 4654
 */


/**
 * A set of coordinates, also referenced as Nodes.
 * @author cs04739
 * @author cs04654
 */
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
