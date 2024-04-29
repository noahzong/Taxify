package project1.src.taxify;

public abstract class UserVehicle extends Vehicle {
	private IUser user;
	
    public UserVehicle(int id, ILocation location) {        
    	super(id, location);
    }
    
    public abstract String getType();
    
    public IUser getUser() {
    	return this.user;
    }
    
    public void setUser(IUser u) {
    	this.user = u;
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
    public String toString() {
        return "User at " + this.location + " moving to " + this.destination +
               ((this.status == VehicleStatus.FREE) ? " is free with path " + this.route.toString(): ((this.status == VehicleStatus.PICKUP) ?
               " to pickup vehicle " + this.service.getUser().getId() : " in service "));
    }    
}