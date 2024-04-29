package project1.src.taxify;

public interface IVehicle extends IMovable {

    public int getId();
    public boolean isPink();
    public IDriver getDriver();
    public void setDriver(IDriver driver);
    public String getType();
    public ILocation getLocation();
    public ILocation getDestination();
    public IService getService();
    public IStatistics getStatistics();
    public void setCompany(ITaxiCompany company);
    public void pickService(IService service);
    public void pickSharedService(IService service);
    public void startService();
    public void endService();
    public void notifyArrivalAtPickupLocation();
    public void notifyArrivalAtDropoffLocation();
    public boolean isFree();
    public int calculateCost();
    public String toString();
	
	
	
	
	
    
}