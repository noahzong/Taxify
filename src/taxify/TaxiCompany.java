package project1.src.taxify;

import java.util.List;
import java.util.Random;

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
            
            
            IService service;
            //now that a origin is assigned, check for shared vehicles
            if(s == ServiceType.SHARED) {
            	vehicleIndex = findFreeSharedVehicle(origin);
            }
            if(vehicleIndex == -1 || s != ServiceType.SHARED) {
            	vehicleIndex = findFreeVehicle();
            	//create a normal service
            	service = new Service(this.users.get(userIndex), origin, destination);
                
                // assign the new service to the vehicle
                
                this.vehicles.get(vehicleIndex).pickService(service);     
            }
            //if the find free shared vehicle method finds a vehicle, then create the service and add it to the vehicle
            else {
            	service = new Service(this.users.get(userIndex), origin, destination);
            	this.vehicles.get(vehicleIndex).pickSharedService(service);
            }
            
            // update the user status
                       
            this.users.get(userIndex).setService(true);
             
          
            // Determine the service type name
            String serviceName = "NORMAL"; 
            switch (s) {
                case PINK:
                    serviceName = "PINK";
                    break;
                case SILENT:
                    serviceName = "SILENT";
                    break;
                case SHARED:
                    serviceName = "SHARED";
                    break;
            }

            String message = String.format("User %s requests a %s service from %s, the ride is assigned to %s %s at location %s",
                this.users.get(userIndex).getId(),
                serviceName,
                service.toString(),
                this.vehicles.get(vehicleIndex).getClass().getSimpleName(),
                this.vehicles.get(vehicleIndex).getId(),
                this.vehicles.get(vehicleIndex).getLocation().toString());

            // Notify observer
            notifyObserver(message);
            // update the counter of services
            
            this.totalServices++;
            return true;
        }
        
        return false;
    }

    @Override
    public void arrivedAtPickupLocation(IVehicle vehicle) {
        // Notify the observer that a vehicle arrived at the pickup location
        notifyObserver("Vehicle " + vehicle.getId() + " arrived at the pickup location");
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
    
    //finds a vehicle close by that could share a ride
    private int findFreeSharedVehicle(ILocation pickup) {
    	Random random = new Random();
    	for(int i = 0; i<this.vehicles.size(); ++i) {
    		//if this vehicle is already in service, check its location
    		if(!this.vehicles.get(i).isFree()) {
    			ILocation location = this.vehicles.get(i).getLocation();
    			if(ApplicationLibrary.distance(location, pickup) < -1) {
    				//Randomize acceptance
    				if(random.nextBoolean()) {
    					return i;
    				}		
    			}
    		}
    		
    	}
    	
    	return -1;
    }

    private int findUserIndex(int id) {
        for (int i=0; i<this.users.size(); i++) {
            if (this.users.get(i).getId() == id)
                return i;
        }
        
        return -1;
    }
}