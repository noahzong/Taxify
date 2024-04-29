package project1.src.taxify;

public class Shuttle extends DriverVehicle {
	public Shuttle(int id, ILocation location) {
		super(id, location);
	}
	
	public String getType() {
		return "Shuttle";
	}
	
	@Override
	public int calculateCost() {
		return 3*super.calculateCost();
	}
}
