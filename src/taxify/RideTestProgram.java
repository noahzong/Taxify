package project1.src.taxify;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class RideTestProgram {
	
    public static void main(String[] args) {
        List<IUser> users = createUsers();
        List<IDriver> drivers = createDrivers();
    	List<IVehicle> vehicles = createVehicles(drivers);
    	
    	//create drivers
    	TaxiCompany company = new TaxiCompany("Taxify", users, vehicles);
    	ApplicationSimulator simulator = new ApplicationSimulator(company, users, vehicles);
    	company.addObserver(simulator);
    	
    	int counter = 0;
    	int nextServiceTime = 5;
    	int simulationTime = 300;
    	Random random = new Random();
    	simulator.requestService();
    	while(simulator.getTotalServices() > 0 || counter < simulationTime) {
    		simulator.update();
    		//simulator.show();
    		if(nextServiceTime == 0 && counter < simulationTime) {
    			nextServiceTime = random.nextInt(10)+1;
    			int serviceType = random.nextInt(4);
    			if(serviceType == 0) {simulator.requestService(ServiceType.PINK);}
    		//	else if(serviceType == 1) {simulator.requestService(ServiceType.SILENT);}
    		//	else if(serviceType == 2) {simulator.requestService(ServiceType.SHARED);}
    		//	else {simulator.requestService(ServiceType.NORMAL);}
    		}
    			
    		counter++;
    		nextServiceTime--;
    	}	
    	simulator.showStatisticswithDriver();
    	
    }

    
    public static List<IUser> createUsers(){
    	List<IUser> users = new ArrayList<IUser>();
        users.add(new User(11, "John", "Doe", 'M', LocalDate.of(1990, 1, 1)));
        users.add(new User(21, "Jane", "Smith", 'F', LocalDate.of(1991, 2, 2)));
        users.add(new User(31, "Bob", "Brown", 'M', LocalDate.of(1992, 3, 3)));
        users.add(new User(41, "Sara", "Jones", 'F', LocalDate.of(1993, 4, 4)));
        users.add(new User(51, "Mike", "Davis", 'M', LocalDate.of(1994, 5, 5)));
        users.add(new User(61, "Emma", "Garcia", 'F', LocalDate.of(1995, 6, 6)));
        users.add(new User(71, "Dave", "Wilson", 'M', LocalDate.of(1996, 7, 7)));
        users.add(new User(81, "Nina", "Martinez", 'F', LocalDate.of(1997, 8, 8)));
        users.add(new User(91, "Chris", "Anderson", 'M', LocalDate.of(1998, 9, 9)));
        users.add(new User(101, "Anna", "Taylor", 'F', LocalDate.of(1999, 10, 10)));
        users.add(new User(111, "Peter", "Thomas", 'M', LocalDate.of(2000, 11, 11)));
        users.add(new User(121, "Lucy", "Moore", 'F', LocalDate.of(2001, 12, 12)));
        users.add(new User(131, "Ethan", "White", 'M', LocalDate.of(2002, 1, 13)));
        users.add(new User(141, "Grace", "Harris", 'F', LocalDate.of(2003, 2, 14)));
        users.add(new User(151, "Leo", "Clark", 'M', LocalDate.of(2004, 3, 15)));

        return users; 
    }

    public static List<IDriver> createDrivers(){
    	List<IDriver> drivers = new ArrayList<>();
    	drivers.add(new Driver(1, "Alex", "Kim", 'M', LocalDate.of(1990, 1, 1)));
    	drivers.add(new Driver(2, "Eva", "Lee", 'F', LocalDate.of(1991, 2, 2)));
    	drivers.add(new Driver(3, "Max", "Parker", 'M', LocalDate.of(1992, 3, 3)));
    	drivers.add(new Driver(4, "Zoe", "Allen", 'F', LocalDate.of(1993, 4, 4)));
    	drivers.add(new Driver(5, "Sam", "Walker", 'M', LocalDate.of(1994, 5, 5)));
    	drivers.add(new Driver(6, "Olivia", "Hernandez", 'F', LocalDate.of(1995, 6, 6)));
    	drivers.add(new Driver(7, "Tom", "King", 'M', LocalDate.of(1996, 7, 7)));
    	drivers.add(new Driver(8, "Mia", "Wright", 'F', LocalDate.of(1997, 8, 8)));
    	drivers.add(new Driver(9, "Jack", "Lopez", 'M', LocalDate.of(1998, 9, 9)));
    	drivers.add(new Driver(10, "Sophia", "Hill", 'F', LocalDate.of(1999, 10, 10)));
    	drivers.add(new Driver(11, "Charlie", "Scott", 'M', LocalDate.of(2000, 11, 11)));
    	drivers.add(new Driver(12, "Lily", "Green", 'F', LocalDate.of(2001, 12, 12)));
    	drivers.add(new Driver(13, "Oscar", "Adams", 'M', LocalDate.of(2002, 1, 13)));
    	drivers.add(new Driver(14, "Isla", "Nelson", 'F', LocalDate.of(2003, 2, 14)));
    	drivers.add(new Driver(15, "Henry", "Mitchell", 'M', LocalDate.of(2004, 3, 15)));
        return drivers;
    }
                        
    public static List<IVehicle> createVehicles(List<IDriver> drivers){
    	 List<IVehicle> vehicles = new ArrayList<>();
         for (int i = 1; i <= 10; i++) {
             ILocation location = ApplicationLibrary.randomLocation();
             if (i % 2 == 0) {
                 vehicles.add(new Taxi(i, location));     
             } else {
                 vehicles.add(new Shuttle(i, location));
             }
             //sets the driver of the vehicle
             vehicles.get(i-1).setDriver(drivers.get(i-1));
         }
         return vehicles;
    }
}
