public class MySQLDatabase implements Database {
    @Override
    public void save(Order order) {
        System.out.println("Saved " + order.getOrderId() + " to MySQL database");
    }
}
