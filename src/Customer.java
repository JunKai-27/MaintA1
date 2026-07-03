public class Customer {
	//We put private final for each of the variables to acheive better encapsulation where these values can only be accessed through public getters and no longer can be changed after initialised.
    private final String name;
    private final String phone;

    public Customer(String name, String phone) {
    	//Validate b4 object creation (Here we separate the validation of each field out to acheive better SRP)
    	validateName(name);
    	validatePhone(phone);
    	
    	//Object creation after successful validation
        this.name = name;
        this.phone = phone;
    }

    //Public getters
    public String getName() {
        return name;
    }
    public String getPhoneDigits() {
        return phone.replaceAll("\\D", "");
    }
    
    //Validation methods (All private cuz it will only be called locally during object creation)    
	private void validateName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be empty");
		}
	}
	
	private void validatePhone(String phone) {
		if (phone == null || phone.trim().isEmpty()) {
			throw new IllegalArgumentException("Phone cannot be empty");
		}
	}
}