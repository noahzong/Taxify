package project1.src.taxify;

public interface IRoute {
	public boolean hasLocations();
    public ILocation getNextLocation();
    public String toString();
}
