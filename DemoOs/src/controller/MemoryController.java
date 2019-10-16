package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import layout.table.data.MemoryBlockPane;

public class MemoryController {
	private Pane memoryPane;
	  private MemoryBlockPane[] blocks;
	  private int columnNumber;
	  
	  public MemoryController(Pane memoryPane) {
	    this.columnNumber = 16;
	    this.memoryPane = memoryPane;
	    this.blocks = new MemoryBlockPane[512];//512个小方格的数组
	    initializeMemoryBlockPanes(this.blocks);
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

}
