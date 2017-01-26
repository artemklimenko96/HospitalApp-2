package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AlertRoom {
	
	private final IntegerProperty roomNbr;
    private final StringProperty desc;
    
    public AlertRoom() {
        this(null, null);
    }
    
    public AlertRoom(Integer roomNbr, String desc) {
        this.roomNbr = new SimpleIntegerProperty(roomNbr);
        this.desc = new SimpleStringProperty(desc);
    }
    
    public Integer getRoomNbr() {
		return roomNbr.get();
	}
	
	public void setRoomNbr(Integer roomNbr) {
        this.roomNbr.set(roomNbr);
    }

    public IntegerProperty roomNbrProperty() {
        return roomNbr;
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
