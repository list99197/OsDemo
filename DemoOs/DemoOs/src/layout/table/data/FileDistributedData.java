package layout.table.data;

import javafx.beans.property.SimpleStringProperty;

public class FileDistributedData {
	private SimpleStringProperty fileid = new SimpleStringProperty();
	  private SimpleStringProperty filecontext = new SimpleStringProperty();
	  
	  
	  public FileDistributedData() {
	    this.filecontext.set("0");
	  }
	  
	  
	  public String getfileid() { return this.fileid.get(); }
	  public String getfilecontext() { return this.filecontext.get(); }
	 
	  
	  public SimpleStringProperty getFileidProperty() { return this.fileid; }
	  public SimpleStringProperty getFilecontextProperty() { return this.filecontext; }
	 
	  public void setFileidBy(String fileid) {
	    this.fileid.set(fileid);
	  }
	  public void setFilecontextBy(String pid) {
		    this.filecontext.set(pid);
		  }
}
