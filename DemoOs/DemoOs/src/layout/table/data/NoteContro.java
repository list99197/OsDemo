package layout.table.data;

import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import com.sun.glass.ui.TouchInputSupport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class NoteContro implements Initializable {
	
	  @FXML
	  private MenuBar mb;
	  @FXML
	  private Menu mFile;
	  @FXML
	  private MenuItem miSave;
	  @FXML
	  private TextArea ta;
	  public static String context="";
	 


	  
	  @FXML
	  private void save(ActionEvent event) {
		  context="";
	    String text = this.ta.getText();
	    this.context=text;
	  }


	  public String getcon() {
			return this.context;
		}
	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

	}

