package project1.src.taxify;

public abstract class Vehicle implements IVehicle {
    protected int id;
    private ITaxiCompany company;
    protected IService service;
    protected IService sharedService;
    protected VehicleStatus status;
    protected ILocation location;
    protected ILocation destination;
    protected IStatistics statistics;
    protected IRoute route;
        
    public Vehicle(int id, ILocation location) {        
        this.id = id;
        this.service = null;
        this.status = VehicleStatus.FREE;
        this.location = location;        
        this.destination = ApplicationLibrary.randomLocation(this.location);
        this.statistics = new Statistics();
        this.route = new Route(this.location, this.destination);
    }

    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public boolean isPink() {
    	return true;
    }
 
    @Override
    public IDriver getDriver() {
    	return null;
    }
    
    @Override
	public void setDriver(IDriver driver) {
    	return;
    }
    
    @Override
    public ILocation getLocation() {
        return this.location;
    }

    @Override
    public ILocation getDestination() {
        return this.destination;
    }
    
    @Override
    public IService getService() {
        return this.service;
    }
    
    @Override
    public IStatistics getStatistics() {
        return this.statistics;
    }
    
    @Override
    public void setCompany(ITaxiCompany company) {
        this.company = company;
    }
    
    @Override
    public void pickService(IService service) {
        // pick a service, set destination to the service pickup location, and status to "pickup"
        
        this.service = service;
        this.destination = service.getPickupLocation();
        this.route = new Route(this.location, this.destination);        
        this.status = VehicleStatus.PICKUP;
    }
    
    @Override
    public void pickSharedService(IService service) {
        // pick a service, set destination to the service pickup location, and status to "pickup"
        
        this.sharedService = service;
        this.destination = sharedService.getPickupLocation();
        this.route = new Route(this.location, this.destination);        
        this.status = VehicleStatus.PICKUP;
    }

    @Override
    public void startService() {
        // set destination to the service drop-off location, and status to "service"
    	this.destination = service.getDropoffLocation();
    	this.route = new Route(this.location, this.destination);
    	this.status = VehicleStatus.SERVICE;
    	
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
        
        // set service to null, and status to "free"
        
        this.service = null;
        this.destination = ApplicationLibrary.randomLocation(this.location);
        this.route = new Route(this.location, this.destination);
        this.status = VehicleStatus.FREE;
    }

    @Override
    public void notifyArrivalAtPickupLocation() {
        // notify the company that the vehicle is at the pickup location and start the service
    	this.company.arrivedAtPickupLocation(this);
    	startService();
    }
        
    @Override
    public void notifyArrivalAtDropoffLocation() {
        // notify the company that the vehicle is at the drop off location and end the service
    	this.company.arrivedAtDropoffLocation(this);
    	endService();
     }
        
    @Override
    public boolean isFree() {
        // returns true if the status of the vehicle is "free" and false otherwise
    	return this.status == VehicleStatus.FREE;

    }   
    
    @Override
    public void move() {
        // get the next location from the driving route
        this.location = this.route.getNextLocation();        
        
        // if the route has more locations the vehicle continues its route, otherwise the vehicle has arrived to a pickup or drop off location
        
        if (!this.route.hasLocations()) {
            if (this.service == null) {
                // the vehicle continues its random route
                this.destination = ApplicationLibrary.randomLocation(this.location);
                this.route = new Route(this.location, this.destination);
            }
            else {
                // check if the vehicle has arrived to a pickup or drop off location

                ILocation origin = this.service.getPickupLocation();
                ILocation destination = this.service.getDropoffLocation();
                System.out.println(origin);
                if (this.location.getX() == origin.getX() && this.location.getY() == origin.getY()) {

                    notifyArrivalAtPickupLocation();

                } else if (this.location.getX() == destination.getX() && this.location.getY() == destination.getY()) {

                    notifyArrivalAtDropoffLocation();

                }        
            }
        }
    }

    @Override
    public int calculateCost() {
    	//if  a ride is shared, charge 1.5x the normal amount (as this is split between two people so each pays only 75%)
    	if(this.sharedService != null) {
    		return this.service.calculateDistance() * 3 / 2;
    	}
        return this.service.calculateDistance();
    }

    @Override
    public String toString() {
        return this.id + " at " + this.location + " driving to " + this.destination +
               ((this.status == VehicleStatus.FREE) ? " is free with path " + this.route.toString(): ((this.status == VehicleStatus.PICKUP) ?
               " to pickup user " + this.service.getUser().getId() : " in service "));
    }    
}