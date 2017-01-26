package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AlertStaff {
	
	private final StringProperty firstName;
	private final StringProperty lastName;
    private final StringProperty desc;
    
    public AlertStaff() {
        this(null, null, null);
    }
    
    public AlertStaff(String firstName, String lastName,String desc) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.desc = new SimpleStringProperty(desc);
    }
    
    public String getFirstName() {
		return firstName.get();
	}
	
	public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public String getLastName() {
		return lastName.get();
	}
	
	public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    public String getDesc() {
		return desc.get();
	}
	
	public void setDesc(String desc) {
        this.desc.set(desc);
    }

    public StringProperty descProperty() {
        return desc;
    }

}
