package project1.src.taxify;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class ScooterTestProgram {
	
    public static void main(String[] args) {
        List<IUser> users = RideTestProgram.createUsers();
    	List<IVehicle> vehicles = ScooterTestProgram.createVehicles();
    	
    	//create drivers
    	TaxiCompany company = new TaxiCompany("Taxify", users, vehicles);
    	ApplicationSimulator simulator = new ApplicationSimulator(company, users, vehicles);
    	company.addObserver(simulator);
    	
    	int counter = 0;
    	int nextServiceTime = 5;
    	int simulationTime = 300;
    	Random random = new Random();
    	simulator.requestUserService(UserVehicleType.BIKE);
    	while(simulator.getTotalServices() > 0 || counter < simulationTime) {
    		simulator.update();
    		//simulator.show();
    		if(nextServiceTime == 0 && counter < simulationTime) {
    			nextServiceTime = random.nextInt(10)+1;
    			int serviceType = random.nextInt(2);
    			//if(serviceType == 0) {simulator.requestUserService(UserVehicleType.SCOOTER);}
    			//else if(serviceType == 1) {simulator.requestUserService(UserVehicleType.BIKE);}
    		}
    			
    		counter++;
    		nextServiceTime--;
    	}	
    	simulator.showStatistics();
    	
    }



	public static List<IVehicle> createVehicles() {
		List<IVehicle> vehicles = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ILocation location = ApplicationLibrary.randomLocation();
            if (i % 2 == 0) {
                vehicles.add(new Scooter(i, location));     
            } else {
                vehicles.add(new Bike(i, location));
            }
        }
        return vehicles;
	}
}                        
