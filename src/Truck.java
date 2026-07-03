class Truck extends Vehicle implements EnginePowered{
    @Override
    public void processVehicle(){
    	System.out.println("Processing truck");
    }
    
    // Override from EnginePowered interface
    @Override
    public void startEngine(){
    	System.out.println("Truck's engine is starting");
    }
    
    //Similar to Bicycle class, it is optional to override the move() method since it is a non-abstract method in Vehicle class
}