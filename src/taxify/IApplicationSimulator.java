package project1.src.taxify;

public interface IApplicationSimulator {

    public void show();
    public void showStatistics();
    public void showStatisticswithDriver();
    public void update();
    public void requestService();
    public void requestService(ServiceType s);
    public void requestUserService(UserVehicleType v);
    public int getTotalServices();
	
    
}