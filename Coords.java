public class Coords
{
	public int X;
	public int Y;
	public float cost = -1;
	
	public Coords(int X, int Y, float moveCost) {
		this.X = X;
		this.Y = Y;
		this.cost = moveCost;
	}

	public Coords() {}

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