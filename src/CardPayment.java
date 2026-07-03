public class CardPayment implements PaymentMethod {
	private static final double CARD_FEE = 5.0; // To resolve Magic Number in applyFee()
	
    @Override
    public double applyFee(double amount) {
        return amount + CARD_FEE;
    }
}