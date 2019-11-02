package layout.table.data;



import javafx.beans.property.SimpleStringProperty;

public class RunningProcessData {
	private SimpleStringProperty runningPid = new SimpleStringProperty();
	  private SimpleStringProperty runOrder = new SimpleStringProperty();
	  private SimpleStringProperty runResult = new SimpleStringProperty();
	  private SimpleStringProperty runRemainTime = new SimpleStringProperty();
	  public RunningProcessData() {
	    this.runningPid.set("");
		  this.runOrder.set("");
		  this.runRemainTime.set("");
		  this.runResult.set("");
	  }
	  public String getRunningPid() {
		  return this.runningPid.get();
	  }
	  public String getRunOrder() {
		  return this.runOrder.get();
	  }
	  public String getRunRemainTime() {
		  return this.runRemainTime.get();
	  }
	  public String getRunResult() {
		  return this.runResult.get();
	  }
	 
	  public SimpleStringProperty getRunningPidProperty() { return this.runningPid; }
	  public SimpleStringProperty getRunOrderProperty() { return this.runOrder; }
	  public SimpleStringProperty getRunRemainTimeProperty() { return this.runRemainTime; }
	  public SimpleStringProperty getRunResultProperty() { return this.runResult; }
	  
	  public void setRunningPidBy(String pid) {
	    this.runningPid.set(pid);
	  }
	  public void setRunOrderBy(String pid) {
		    this.runOrder.set(pid);
		  }
	  public void setRunRemainTimeBy(String pid) {
		    this.runRemainTime.set(pid);
		  }
	  public void setRunResultBy(String pid) {
		    this.runResult.set(pid);
		  }
}
