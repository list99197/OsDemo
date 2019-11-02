package layout.table.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DeviceData {
	private SimpleStringProperty type = new SimpleStringProperty();
	  private SimpleStringProperty waitPID = new SimpleStringProperty();
	  private SimpleStringProperty runPID = new SimpleStringProperty();
	  
	  public DeviceData(String type) {
	    this.type.set(type);
	    this.waitPID.set("");
	    this.runPID.set("");
	  }
	  
	  
	  public String getType() { return this.type.get(); }

	  public String getRunPID() { return this.runPID.get(); }
	  public String getWaitPID() { return this.waitPID.get(); }
	  public SimpleStringProperty getRunPIDProperty() { return this.runPID; }
	  public SimpleStringProperty getTypeProperty() { return this.type; }
	  public SimpleStringProperty getWaitPIDProperty() { return this.waitPID; }
	  public void setRunPIDBy(String pid) {
	    this.runPID.set(pid);
	  }
	  public void setWaitPIDBy(String pid) {
		    this.waitPID.set(pid);
		  }
}
