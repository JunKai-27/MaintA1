public class OMS {
    public static void main(String[] args) {

    	// For Order Management System
    	System.out.println("--- Order Management System ---");
        Database database = DatabaseFactory.createDB(); //Factory pattern: Does not care how the Database object is created behind (encapsulate object creation).
        PaymentMethod paymentMethod = new CardPayment();
        Customer customer = new Customer("John", "012-3458463");
        Order order = new Order(
                "ORD001",
                customer,
                200.0,
                paymentMethod
        );
        
        OrderProcessor processor = new OrderProcessor(database);
        processor.process(order);
        
        // For Vehicle Management System
        System.out.println("\n--- Vehicle Management System ---");
        Bicycle bicycle = new Bicycle();
        bicycle.processVehicle();
        
        Truck truck = new Truck();
        truck.processVehicle();
        truck.startEngine();
    }
}