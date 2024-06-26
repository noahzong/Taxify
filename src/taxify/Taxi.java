package project1.src.taxify;

public class Taxi extends DriverVehicle {
	public Taxi(int id, ILocation location) {
		super(id, location);
	}
	
	public String getType() {
		return "Taxi";
	}
	
	@Override
	public int calculateCost() {
		return 4*super.calculateCost();
	}
}
