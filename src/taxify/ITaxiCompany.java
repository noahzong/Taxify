package project1.src.taxify;

public interface ITaxiCompany {
	public String getName();    
    public int getTotalServices();
    public boolean provideService(int user, ServiceType s);
    public boolean provideUserService(int user, UserVehicleType v);
    public void arrivedAtPickupLocation(IVehicle vehicle);
    public void arrivedAtDropoffLocation(IVehicle vehicle);
}
