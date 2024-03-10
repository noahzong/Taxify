package taxify;


import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class TestProgram {
	
    public static void main(String[] args) {
        List<IUser> users = createUsers();
    	List<IVehicle> vehicles = createVehicles();
    	TaxiCompany company = new TaxiCompany("Taxify", users, vehicles);
    	ApplicationSimulator simulator = new ApplicationSimulator(company, users, vehicles);
    	company.addObserver(simulator);
    	
    	int minDelay = 1000; 
    	int maxDelay = 3000; 
    	Random random = new Random();
    	for(int i = 0; i<5; i++) {
    		int delay = minDelay + random.nextInt(maxDelay - minDelay + 1);
    		try {
                Thread.sleep(delay); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Exeception caught");
            }
    		simulator.requestService();
    	}

    	System.out.println(simulator.getTotalServices());
    	while(simulator.getTotalServices() > 0) {
    		simulator.update();
    		simulator.show();
    	}

    	simulator.showStatistics();
    }

    
    private static List<IUser> createUsers(){
    	List<IUser> users = new ArrayList<IUser>();
        users.add(new User(1, "John", "Doe", 'M', LocalDate.of(1990, 1, 1)));
        users.add(new User(2, "Jane", "Smith", 'F', LocalDate.of(1991, 2, 2)));
        users.add(new User(3, "Bob", "Brown", 'M', LocalDate.of(1992, 3, 3)));
        users.add(new User(4, "Sara", "Jones", 'F', LocalDate.of(1993, 4, 4)));
        users.add(new User(5, "Mike", "Davis", 'M', LocalDate.of(1994, 5, 5)));
        users.add(new User(6, "Emma", "Garcia", 'F', LocalDate.of(1995, 6, 6)));
        users.add(new User(7, "Dave", "Wilson", 'M', LocalDate.of(1996, 7, 7)));
        users.add(new User(8, "Nina", "Martinez", 'F', LocalDate.of(1997, 8, 8)));
        users.add(new User(9, "Chris", "Anderson", 'M', LocalDate.of(1998, 9, 9)));
        users.add(new User(10, "Anna", "Taylor", 'F', LocalDate.of(1999, 10, 10)));
        users.add(new User(11, "Peter", "Thomas", 'M', LocalDate.of(2000, 11, 11)));
        users.add(new User(12, "Lucy", "Moore", 'F', LocalDate.of(2001, 12, 12)));
        users.add(new User(13, "Ethan", "White", 'M', LocalDate.of(2002, 1, 13)));
        users.add(new User(14, "Grace", "Harris", 'F', LocalDate.of(2003, 2, 14)));
        users.add(new User(15, "Leo", "Clark", 'M', LocalDate.of(2004, 3, 15)));

        return users; 
    }
    
    private static List<IVehicle> createVehicles(){
    	 List<IVehicle> vehicles = new ArrayList<>();
         Random random = new Random();

         for (int i = 0; i < 10; i++) {
             int x = random.nextInt(10); 
             int y = random.nextInt(10);
             Location location = new Location(x, y);

             if (i % 2 == 0) {
                 vehicles.add(new Taxi(i, location));
             } else {
                 vehicles.add(new Shuttle(i, location));
             }
         }
         return vehicles;
    }
}
