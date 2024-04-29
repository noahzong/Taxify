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
    
    //Overrides the move method for user driven vehicles
//    @Override
//    public void move() {
//    	if(this.service == null) {
//    		//scooters and bikes don't move by themselves
//    	}
//    	else {
//    		ILocation origin = this.service.getPickupLocation();
//    		ILocation destination = this.service.getDropoffLocation();
//    		if (this.location.getX() == origin.getX() && this.location.getY() == origin.getY()) {
//                notifyArrivalAtPickupLocation();
//            }
//    		else if (this.location.getX() == destination.getX() && this.location.getY() == destination.getY()) {
//                notifyArrivalAtDropoffLocation();
//            }     
//    		else if(!this.route.hasLocations()) {
//    			this.route = new Route(this.location, this.service.getPickupLocation());
//    		}
//    		else {
//    			this.route.getNextLocation();
//    		}
//    	}
//    }

    @Override
    public String toString() {
        return this.id + " at " + this.location + " driving to " + this.destination +
               ((this.status == VehicleStatus.FREE) ? " is free with path " + this.route.toString(): ((this.status == VehicleStatus.PICKUP) ?
               " to pickup user " + this.service.getUser().getId() : " in service "));
    }    
}