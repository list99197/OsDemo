package layout.table.data;

import javafx.beans.property.SimpleStringProperty;

public class BlockProcessData {

	private SimpleStringProperty blockPid = new SimpleStringProperty();
	private SimpleStringProperty blockWaitTime = new SimpleStringProperty();
	  public BlockProcessData() {
	    this.blockPid.set("");
		 this.blockWaitTime.set("");
	  }
	  public String getBlockPid() {
		  return this.blockPid.get();
	  }
	public String getBlockWaitTime() {
		return this.blockWaitTime.get();
	}
	 
	  public SimpleStringProperty getBlockPidProperty() { return this.blockPid; }
	  public SimpleStringProperty getBlockWaitTimeProperty() {
		  return this.blockWaitTime;
	  }
	  public void setBlockPidBy(String pid) {
	    this.blockPid.set(pid);
	  }
	  public void setBlockWaitTimeBy(String pid) {
		    this.blockWaitTime.set(pid);
		  }

}
