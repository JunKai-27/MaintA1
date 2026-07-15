package TestCase;

import RefactoredCode.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Technical validation suite for the refactored Order Management System (OMS).
 * Verifies code quality, regression prevention, and strict encapsulation.
 */
public class OMSTest {

    private Customer validCustomer;
    private PaymentMethod cardPayment;
    private PaymentMethod cashPayment;

    @BeforeEach
    public void setUp() {
        // Initialize clean, isolated domain and strategy objects before each test
        validCustomer = new Customer("John Doe", "555-123-4567");
        cardPayment = new CardPayment();
        cashPayment = new CashPayment();
    }

    @Test
    public void testOrderCreationWithValidData() {
        Order order = new Order("ORD001", validCustomer, 200.0, cardPayment);
        
        assertNotNull(order);
        assertEquals("ORD001", order.getOrderId());
        assertEquals("John Doe", order.getCustomer().getName());
        assertEquals("5551234567", order.getCustomer().getPhoneDigits()); // Clean digits
        assertEquals(200.0, order.getOrderAmount());
    }

    @Test
    public void testDiscountCalculationThresholds() {
        // Test Order above threshold: Amount $150 (triggers 10% discount)
        Order discountOrder = new Order("ORD002", validCustomer, 150.0, cashPayment);
        // Calculation: 150 * 0.9 = $135 (cash surcharge = $0)
        assertEquals(135.0, discountOrder.calculateFinalAmount());
    }

    @Test
    public void testNoDiscountCalculationAtOrBelowThreshold() {
        // Test Order at threshold limit: Amount exactly $100 (no discount)
        Order noDiscountOrder = new Order("ORD003", validCustomer, 100.0, cashPayment);
        assertEquals(100.0, noDiscountOrder.calculateFinalAmount());
    }

    @Test
    public void testPaymentMethodSurcharges() {
        // Test Card Payment: adds a flat $5.0 CARD_FEE
        Order cardOrder = new Order("ORD004", validCustomer, 100.0, cardPayment);
        // Calculation: 100 (no discount) + Card Surcharge 5 = $105
        assertEquals(105.0, cardOrder.calculateFinalAmount());

        // Test Cash Payment: adds $0 fee
        Order cashOrder = new Order("ORD005", validCustomer, 100.0, cashPayment);
        assertEquals(100.0, cashOrder.calculateFinalAmount());
    }

    @Test
    public void testCustomerPhoneSanitizationAndCohesion() {
        Customer dirtyPhoneCustomer = new Customer("Alice Smith", "(555) 098-7654");
        // Customer component must strip dashes, spaces, and brackets
        assertEquals("5550987654", dirtyPhoneCustomer.getPhoneDigits());
        
        Order order = new Order("ORD006", dirtyPhoneCustomer, 50.0, cashPayment);
        assertEquals("5550987654", order.getCustomer().getPhoneDigits());
    }
    
    @Test
    public void testVehicleManagementSystemCoverage() {
        // Test Bicycle lifecycle
        Bicycle bicycle = new Bicycle();
        assertNotNull(bicycle);
        bicycle.processVehicle();
        bicycle.move(); // Executes parent abstract class move() method

        // Test Truck lifecycle
        Truck truck = new Truck();
        assertNotNull(truck);
        truck.processVehicle();
        truck.startEngine(); // Executes EnginePowered interface implementation
        truck.move(); // Executes parent abstract class move() method
    }

    @Test
    public void testNegativeOrderCreationWithInvalidInputs() {
        // 1. Assert empty order ID fails validation
        assertThrows(IllegalArgumentException.class, () -> {
            new Order("", validCustomer, 50.0, cashPayment);
        }, "Empty Order ID must trigger an IllegalArgumentException.");

        // 2. Assert null customer object fails validation
        assertThrows(IllegalArgumentException.class, () -> {
            new Order("ORD007", null, 50.0, cashPayment);
        }, "Null Customer must trigger an IllegalArgumentException.");

        // 3. Assert zero or negative order amount fails validation
        assertThrows(IllegalArgumentException.class, () -> {
            new Order("ORD008", validCustomer, -10.0, cashPayment);
        }, "Negative order amounts must trigger an IllegalArgumentException.");
    }

    @Test
    public void testNegativeEncapsulationAndStructureViolations() {
        Class<Order> orderClass = Order.class;

        Method[] methods = orderClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("validate")) {
                assertTrue(Modifier.isPrivate(method.getModifiers()), 
                    "Validation logic must be private inside the order class.");
            }
            assertNotEquals("middle", method.getName(), "Redundant middle() method must be removed.");
        }
        Field[] fields = orderClass.getDeclaredFields();
        for (Field field : fields) {
            assertNotEquals("tempDiscount", field.getName(), 
                "tempDiscount must not be stored as a class field; calculations must use local scope.");
            
            assertTrue(Modifier.isPrivate(field.getModifiers()), 
                "Field '" + field.getName() + "' must be private to enforce data encapsulation.");
        }
    }
}
