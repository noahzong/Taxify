package project1.src.taxify;

public class Shuttle extends Vehicle {
	public Shuttle(int id, ILocation location) {
		super(id, location);
	}
	
	@Override
	public int calculateCost() {
		return 3*super.calculateCost();
	}
}
