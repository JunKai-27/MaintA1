public abstract class Vehicle {
    public void move(){
    	System.out.println("Vehicle is moving");
    }
    public abstract void processVehicle(); //removed the TruckHandler and avoid future CarHandler, MotorcycleHandler, etc
}
