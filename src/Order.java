class Order {
	//We put private final for each of the variables to acheive better encapsulation where these values can only be accessed through public getters and no longer can be changed after initialised.
    private final String orderId;
    private final Customer customer;
    private final double orderAmount;
    private final PaymentMethod paymentMethod;

    // Variables used to avoid Magic Number when calculating the discount amount 
    private static final double DISCOUNT_THRESHOLD = 100;
    private static final double DISCOUNT_RATE = 0.10;
    
    public Order(String orderId, Customer customer, double orderAmount, PaymentMethod paymentMethod) {
        //Validate b4 object creation (Here we separate the validation of each field out to achieve better SRP)
        validateOrderId(orderId);
        validateCustomer(customer);
        validateOrderAmount(orderAmount);
        validatePaymentMethod(paymentMethod);
        
        //Object creation after successful validation
        this.orderId = orderId;
        this.customer = customer;
        this.orderAmount = orderAmount;
        this.paymentMethod = paymentMethod;
    }
    
    // Public getters
    public String getOrderId() {
        return orderId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public double getOrderAmount() {
		return orderAmount;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	
	//Validation methods (All private cuz it will only be called locally during object creation)
	private void validateOrderId(String orderId) {
		if (orderId == null || orderId.trim().isEmpty()) {
			throw new IllegalArgumentException("Order ID cannot be empty");
		}
	}
	
	private void validateCustomer(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("Customer cannot be null");
		}
	}
	
	private void validateOrderAmount(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Order amount cannot be negative");
		}
	}
	
	private void validatePaymentMethod(PaymentMethod paymentMethod) {
		if (paymentMethod == null) {
			throw new IllegalArgumentException("Payment method cannot be null");
		}
	}

    private double calculateDiscountedAmount() { //Private cuz it will only be called by calculateFinalAmount()
        if (orderAmount > DISCOUNT_THRESHOLD) {
            double discount = orderAmount * DISCOUNT_RATE;
            return orderAmount - discount;
        }

        return orderAmount; //means no discount at all
    }

    public double calculateFinalAmount() {
        double discountedAmount = calculateDiscountedAmount();
        double finalAmount = paymentMethod.applyFee(discountedAmount);
        return finalAmount;
    }
}
