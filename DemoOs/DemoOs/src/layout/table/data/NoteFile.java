package layout.table.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.StartOs;

public class NoteFile
extends Stage
{
private static final String NOTEPAD_FXML = "note.fxml";
private BorderPane fxmlNode;


public NoteFile(String con) {
  try {
	  FXMLLoader loader = new FXMLLoader();
	  loader.setLocation(getClass().getResource("note.fxml"));
    
    this.fxmlNode = (BorderPane)loader.load();
    TextArea ta = (TextArea)this.fxmlNode.getChildrenUnmodifiable().get(0);
    ta.setText(con);
    setScene(new Scene(this.fxmlNode, 500.0D, 500.0D));
  }
  catch (IOException ex) {
    Logger.getLogger(NoteFile.class.getName()).log(Level.SEVERE, null, ex);
  } 
}

}

