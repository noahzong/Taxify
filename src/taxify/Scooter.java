package project1.src.taxify;

public class Scooter extends UserVehicle {
	public Scooter(int id, ILocation location) {
		super(id, location);
	}
	
	public String getType() {
		return "Scooter";
	}
	
	@Override
	public int calculateCost() {
		return 3*super.calculateCost()/4;
	}
}
