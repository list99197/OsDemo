package controller;

import Disk.FAT;
import javafx.scene.layout.Pane;
import layout.table.data.DiskBlockPane;
import layout.table.data.MemoryBlockPane;

public class DiskController {
	private Pane diskPane;
	  private DiskBlockPane[] blocks;
	  private int columnNumber;
	  private FAT fatBoss;
	  public FAT getFatBoss() {
		return fatBoss;
	}

	public void setFatBoss(FAT fatBoss) {
		this.fatBoss = fatBoss;
	}

	public DiskController(Pane diskPane,FAT fat) {
	    this.columnNumber = 16;
	    this.diskPane = diskPane;
	    this.fatBoss=fat;
	    this.blocks = new DiskBlockPane[256];//512个小方格的数组
	    initializeDiskBlockPanes(this.blocks);
	    update(fatBoss.getDiskState());
	  }
	  
	  public void initializeDiskBlockPanes(DiskBlockPane[] blocks) {
	   
	    for (int i = 0; i < blocks.length / this.columnNumber; i++) {
	      for (int j = 0; j < this.columnNumber; j++) {
	        int index = i * this.columnNumber + j;
	        if (blocks[index] == null) {
	          blocks[index] = new DiskBlockPane(index);
	          blocks[index].setLayoutX((6.2*(j+1) + j * 30));
	          blocks[index].setLayoutY((19.1*(i+1) + i * 30));
	         // blocks[index].setOnMouseClicked(listener);
	          this.diskPane.getChildren().add(blocks[index]);
	        } 
	      } 
	    } 
	  }
	  public void update(int[] state) {
		  for(int i=0;i<state.length;i++) {
			  if(state[i]!=0) {
				  blocks[i].setUsed();
			  }else {
				  blocks[i].setUnUsed();;
			  }
		  }
	  }
}
