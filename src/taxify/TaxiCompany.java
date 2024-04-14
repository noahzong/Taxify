package project1.src.taxify;

import java.util.List;

public class TaxiCompany implements ITaxiCompany, ISubject {
    private String name;
    private List<IUser> users;
    private List<IVehicle> vehicles;
    private int totalServices;
    private IObserver observer;
    
    public TaxiCompany(String name, List<IUser> users, List<IVehicle> vehicles) {
        this.name = name;
        this.users = users;
        this.vehicles = vehicles;        
        this.totalServices = 0;
        
        for (IUser user : this.users) {
            user.setCompany(this);
        }
        
        for (IVehicle vehicle : this.vehicles) {
            vehicle.setCompany(this);
        }
    }
    
    @Override
    public String getName() {
        // return name
    	return this.name;
    }

    @Override
    public int getTotalServices() {
        // return total services
    	return this.totalServices;
    }
        
    @Override
    public boolean provideService(int user, ServiceType s) {
        int userIndex = findUserIndex(user);        
        int vehicleIndex;
        
        if(s == ServiceType.PINK) {
        	vehicleIndex = findFreePinkVehicle();
        }
        else {
        	//If the user requests a silent ride, this doesn't change how the vehicle is found, as any vehicle can be silent
        	vehicleIndex = findFreeVehicle();
        }
        
        // if there is a free vehicle, assign a random pickup and drop-off location to the new service
        // the distance between the pickup and the drop-off location should be at least 3 blocks
        
        if (vehicleIndex != -1) {
            ILocation origin, destination;
            
            do {
                
                origin = ApplicationLibrary.randomLocation();
                destination = ApplicationLibrary.randomLocation(origin);
                
            } while (ApplicationLibrary.distance(origin, this.vehicles.get(vehicleIndex).getLocation()) < ApplicationLibrary.MINIMUM_DISTANCE);
            
            // update the user status
                       
            this.users.get(userIndex).setService(true);
            
            // create a service with the user, the pickup and the drop-off location

            IService service = new Service(this.users.get(userIndex), origin, destination);
            
            // assign the new service to the vehicle
            
            this.vehicles.get(vehicleIndex).pickService(service);            
          
            if(s == ServiceType.PINK) {
            	notifyObserver("User " + this.users.get(userIndex).getId() + " requests a PINK service from " + service.toString() + ", the ride is assigned to " +
                this.vehicles.get(vehicleIndex).getClass().getSimpleName() + " " + this.vehicles.get(vehicleIndex).getId() + " at location " +
                this.vehicles.get(vehicleIndex).getLocation().toString());
            }
            else if(s == ServiceType.SILENT) {
            	notifyObserver("User " + this.users.get(userIndex).getId() + " requests a SILENT service from " + service.toString() + ", the ride is assigned to " +
                this.vehicles.get(vehicleIndex).getClass().getSimpleName() + " " + this.vehicles.get(vehicleIndex).getId() + " at location " +
                this.vehicles.get(vehicleIndex).getLocation().toString());
            }
            else if(s == ServiceType.SHARED) {
            	notifyObserver("User " + this.users.get(userIndex).getId() + " requests a SHARED service from " + service.toString() + ", the ride is assigned to " +
                this.vehicles.get(vehicleIndex).getClass().getSimpleName() + " " + this.vehicles.get(vehicleIndex).getId() + " at location " +
                this.vehicles.get(vehicleIndex).getLocation().toString());
            }
            else {
            	notifyObserver("User " + this.users.get(userIndex).getId() + " requests a NORMAL service from " + service.toString() + ", the ride is assigned to " +
                this.vehicles.get(vehicleIndex).getClass().getSimpleName() + " " + this.vehicles.get(vehicleIndex).getId() + " at location " +
                this.vehicles.get(vehicleIndex).getLocation().toString());
            }
            // update the counter of services
            
            this.totalServices++;
            return true;
        }
        
        return false;
    }

    @Override
    public void arrivedAtPickupLocation(IVehicle vehicle) {
        // notify the observer a vehicle arrived at the pickup location
    	notifyObserver("Vehicle arrived at the pickup location");
    }
    
    @Override
    public void arrivedAtDropoffLocation(IVehicle vehicle) {
        // a vehicle arrives at the drop-off location
        
        IService service = vehicle.getService();       
        int user = service.getUser().getId();
        int userIndex = findUserIndex(user);
       
        // the taxi company requests the user to rate the service, and updates its status

        this.users.get(userIndex).rateService(service);
        this.users.get(userIndex).setService(false);

        // update the counter of services
        
        this.totalServices--;    
        
        notifyObserver(String.format("%-8s",vehicle.getClass().getSimpleName()) + vehicle.getId() + " drops off user " + user);         
    }
        
    @Override
    public void addObserver(IObserver observer) {
        // add observer
    	this.observer = observer;
    }
    
    @Override
    public void notifyObserver(String message) {
        // send a message to the observer
    	this.observer.updateObserver(message);
    }
    
    private int findFreeVehicle() {
        int index;
        
        do {
            
            index = ApplicationLibrary.rand(this.vehicles.size());
            
        } while (!this.vehicles.get(index).isFree());

        return index;
    }
    
  //finds a free pink vehicle
    private int findFreePinkVehicle() {
    	int index;
        
        do {
            
            index = ApplicationLibrary.rand(this.vehicles.size());
            
        } while (!this.vehicles.get(index).isFree() || !this.vehicles.get(index).isPink());

        return index;
    }

    private int findUserIndex(int id) {
        for (int i=0; i<this.users.size(); i++) {
            if (this.users.get(i).getId() == id)
                return i;
        }
        
        return -1;
    }
}