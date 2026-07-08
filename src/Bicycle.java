public class Bicycle extends Vehicle {
    @Override
    public void processVehicle(){
    	System.out.println("Processing bicycle");
    }
    
    //No need override startEngine() cuz it does not implement EnginePowered interface
    //Also, it is optional to override the move() method since it is a non-abstract method in Vehicle class
}
