package project1.src.taxify;

import java.util.Random;

public class ApplicationLibrary {
    public static final int MINIMUM_DISTANCE = 3;
    private static final int MAP_WIDTH = 10;
    private static final int MAP_HEIGHT = 10;    

  //these two methods were giving me bugs due to the int type casting
    public static int rand() {
        Random random = new Random();
        
        //return random.nextInt((int) System.currentTimeMillis());
        return random.nextInt();
    }
    
    public static int rand(int max) {
    
        Random random = new Random();

        //return random.nextInt((int) System.currentTimeMillis()) % max;
        return random.nextInt(max);
    }
    
    public static int distance(ILocation a, ILocation b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
    
    public static ILocation randomLocation() {
        return new Location(rand(MAP_WIDTH), rand(MAP_HEIGHT));
    }
    
    public static ILocation randomLocation(ILocation location) {
        ILocation destination;
        
        do {
            
            destination = new Location(rand(MAP_WIDTH), rand(MAP_HEIGHT));
            
        } while (distance(location, destination) < MINIMUM_DISTANCE);  
            
        return destination;
    }
}