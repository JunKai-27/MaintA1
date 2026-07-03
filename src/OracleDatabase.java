public class OracleDatabase implements Database {
    @Override
    public void save(Order order) {
        System.out.println("Saved " + order.getOrderId() + " to Oracle database");
    }
}