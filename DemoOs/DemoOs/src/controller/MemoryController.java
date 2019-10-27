package controller;

import RAM.obj.Memory;
import RAM.obj.SpaceAssignment;
import device.obj.Device;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import layout.table.data.MemoryBlockPane;

public class MemoryController {
	private Pane memoryPane;
	  private static MemoryBlockPane[] blocks;
	  private int columnNumber;
	  
	  public MemoryController(Pane memoryPane) {
	    this.columnNumber = 16;
	    this.memoryPane = memoryPane;
	    this.blocks = new MemoryBlockPane[512];//512个小方格的数组
	    initializeMemoryBlockPanes(this.blocks);
	    updateMemory();
	  }
	  public static void updateMemory() {
			Timeline timeline = new Timeline();
			timeline.setCycleCount(-1);
			Duration duration = Duration.millis(1.0D);
			KeyFrame keyFrame = new KeyFrame(duration, new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					update(Memory.getMemoryTable());
					
				}
			}, new javafx.animation.KeyValue[0]);
			timeline.getKeyFrames().add(keyFrame);
			timeline.play();
		}
	  public void initializeMemoryBlockPanes(MemoryBlockPane[] blocks) {
	   
	    for (int i = 0; i < blocks.length / this.columnNumber; i++) {
	      for (int j = 0; j < this.columnNumber; j++) {
	        int index = i * this.columnNumber + j;
	        if (blocks[index] == null) {
	          blocks[index] = new MemoryBlockPane(index);
	          blocks[index].setLayoutX((9.4*(j+1) + j * 35));
	          blocks[index].setLayoutY((1.1*(i+1) + i * 23));
	         // blocks[index].setOnMouseClicked(listener);
	          this.memoryPane.getChildren().add(blocks[index]);
	        } 
	      } 
	    } 
	  }
	  public static void update(byte[] state) {
		  for(int i=0;i<state.length;i++) {
			  if(state[i]!=0) {
				  blocks[i].setUsed();
				  blocks[i].setLableText(""+state[i]);
			  }else {
				  blocks[i].setUnUsed();
				  blocks[i].setLableText("0");
			  }
		  }
	  }
}
