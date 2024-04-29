package project1.src.taxify;

public class Bike extends UserVehicle {
	public Bike(int id, ILocation location) {
		super(id, location);
	}
	
	public String getType() {
		return "Bike";
	}
	
	@Override
	public int calculateCost() {
		return super.calculateCost()/2;
	}
}
