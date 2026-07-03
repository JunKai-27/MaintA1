public class OrderProcessor {
    private final Database database;

    public OrderProcessor(Database database) {
        this.database = database;
    }

    public void process(Order order) {
        double finalAmount = order.calculateFinalAmount();
        
        printOrderSummary(order, finalAmount);
        
        database.save(order);
    }
    
    private void printOrderSummary(Order order, double finalAmount) { //Private cuz it will only be called by process()
    	System.out.println("Order: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("Phone: " + order.getCustomer().getPhoneDigits());
        System.out.println("Total: " + finalAmount);
    }
}