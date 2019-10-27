package layout.table.data;

import javafx.beans.property.SimpleStringProperty;

public class ReadyProcessData {

	private SimpleStringProperty readyPid = new SimpleStringProperty();
	
	  public ReadyProcessData() {
	    this.readyPid.set("");
		 
	  }
	  public String getReadyPid() {
		  return this.readyPid.get();
	  }
	
	 
	  public SimpleStringProperty getReadyPidProperty() { return this.readyPid; }
	  
	  public void setReadyPidBy(String pid) {
	    this.readyPid.set(pid);
	  }
	 

}
