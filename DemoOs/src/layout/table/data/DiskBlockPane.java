package layout.table.data;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;

public class DiskBlockPane extends StackPane {
	private Rectangle rectangle;//小方格
	  private Label indexLabel;//小方格里面的内容，显示是被占用哪些进程
	  private int index;//小方格的下标
	  private int status;
	  public static final int UNUSED_STATUS = 0;
	  public static final int USED_STATUS = 1;
	  public static final int DAMAGE_STATUS = -1;
	  public DropShadow dropShadow;
	  
	  public DiskBlockPane(int index) {
	    this.rectangle = new Rectangle(35.0D, 23.0D, Color.rgb(233, 239, 244));
	    this.rectangle.setStroke(Color.BLACK);
	    this.rectangle.setStrokeLineCap(StrokeLineCap.ROUND);
	    this.index = index;
	    this.status = 0;
	    this.indexLabel = new Label(index + "");
	    this.indexLabel.setFont(Font.font(13.0D));
	    this.dropShadow = new DropShadow();
	    getChildren().addAll(new Node[] { this.rectangle, this.indexLabel });
	    initializeListener();
	  }
	  
	  private void initializeListener() {
	    setOnMouseEntered(new EventHandler<MouseEvent>()
	        {
	          public void handle(MouseEvent event) {
	        	  DiskBlockPane.this.setEffect(DiskBlockPane.this.dropShadow);
	          }
	        });
	    
	    setOnMouseExited(new EventHandler<MouseEvent>()
	        {
	          public void handle(MouseEvent event) {
	        	  DiskBlockPane.this.setEffect(null);
	          }
	        });
	  }

	  
	  public int getIndex() { return this.index; }

	  
	  public void setUsed() {
	    this.status = 1;
	    this.rectangle.setFill(Color.rgb(230, 248, 97));
	  }
	  
	  public void setUnUsed() {
	    this.status = 0;
	    this.rectangle.setFill(Color.rgb(202, 223, 242));
	  }
	  
	  public void setDamage() {
	    this.status = -1;
	    this.rectangle.setFill(Color.RED);
	  }
	  public void setLableText(String string) {
		    this.indexLabel.setText(string);
		  }
	  
	  public int getStatus() { return this.status; }
}
