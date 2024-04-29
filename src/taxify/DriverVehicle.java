package project1.src.taxify;

public abstract class DriverVehicle extends Vehicle {
    private IDriver driver;
        
    public DriverVehicle(int id, ILocation location) {        
        super(id, location);
    }
    
    @Override
    public boolean isPink() {
    	return this.driver.getGender() == 'F';
    }

    @Override
    public void endService() {
        // update vehicle statistics
        
        this.statistics.updateBilling(this.calculateCost());
        this.statistics.updateDistance(this.service.calculateDistance());
        this.statistics.updateServices();
        
        
        // if the service is rated by the user, update statistics
        
        if (this.service.getStars() != 0) {
            this.statistics.updateStars(this.service.getStars());
            this.statistics.updateReviews();
        }
        
        //if it was a shared service, add extra statistics
        if(this.sharedService != null) {
        	this.statistics.updateServices();
        	if (this.service.getStars() != 0) {
                this.statistics.updateStars(this.service.getStars());
                this.statistics.updateReviews();
            }
        }
        // set service to null, and status to "free"
        
        this.service = null;
        this.destination = ApplicationLibrary.randomLocation(this.location);
        this.route = new Route(this.location, this.destination);
        this.status = VehicleStatus.FREE;
    }
    
    @Override
    public IDriver getDriver() {
    	return this.driver;
    }
    
    @Override
    public void setDriver(IDriver driver) {
    	this.driver = driver;
    }

    @Override
    public String toString() {
        return this.id + " at " + this.location + " driving to " + this.destination +
               ((this.status == VehicleStatus.FREE) ? " is free with path " + this.route.toString(): ((this.status == VehicleStatus.PICKUP) ?
               " to pickup user " + this.service.getUser().getId() : " in service "));
    }    
}