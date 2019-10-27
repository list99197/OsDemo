package controller;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.util.Duration;
import layout.table.data.BlockProcessData;
import progress.management.PCB;
import progress.management.SystemClock;

public class BlockProcessController {
//	private static List<BlockDispaly> blockDispalies;
private static Timeline[] timelines=new Timeline[10];
	public static ObservableList<BlockProcessData> blockProcessList= FXCollections.observableArrayList();
	//private List<PCB> readyPcb;
   public BlockProcessController() {
	   for(int i=0;i<10;i++) {
		   this.blockProcessList.add(new BlockProcessData());
		   timelines[i]=new Timeline();
	   }
		
	}
   
   private static int time=0;
   private static int a=0;
	public static void updataBlockInfo(List<PCB> list) {
		for(int m=0;m<10;m++) {
				timelines[m].stop();
			}
		if(list.size()==0) {
			 for(int k=0;k<10;k++) {
				blockProcessList.get(k).setBlockPidBy("");
					blockProcessList.get(k).setBlockWaitTimeBy("");
					
			   }
		}else {
			
			for(int i=0;i<list.size();i++) {
			if(list.get(i).getRegisterState()!=null) {
				
//				time=list.get(i).getApplyTime();
//				 a=list.get(i).getTimeOfBlocked();
				 blockProcessList.get(i).setBlockPidBy(""+list.get(i).getPid());
				blockProcessList.get(i).setBlockWaitTimeBy((SystemClock.getSystemTime()-list.get(i).getTimeOfBlocked())+"");
				
			initCPULabel(blockProcessList.get(i), list.get(i).getTimeOfBlocked(), list.get(i).getApplyTime()-(SystemClock.getSystemTime()-list.get(i).getTimeOfBlocked()), list.get(i).getPid(),timelines[i]);
			}
			for(int j=list.size();j<blockProcessList.size();j++) {
				
				blockProcessList.get(j).setBlockPidBy("");
				blockProcessList.get(j).setBlockWaitTimeBy("");
			}	
		}
		}
		
	}
	
	public static void initCPULabel(BlockProcessData label,int a,int time,int pid,Timeline timeline) {
	
	if(time>0) {
		
		timeline.setCycleCount(time+1);
	}else {
		timeline.setCycleCount(0);
	}
		
		Duration duration = Duration.millis(1000.0D);
		KeyFrame keyFrame = new KeyFrame(duration, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
					int endtime = SystemClock.getSystemTime();
					
				endtime = endtime - a;
				
					String string = String.valueOf(endtime);
				label.setBlockWaitTimeBy(string);
				label.setBlockPidBy(pid+"");	
			}
		}, new javafx.animation.KeyValue[0]);
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		timeline.setOnFinished( new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				timeline.stop();
				label.setBlockWaitTimeBy("");
				label.setBlockPidBy("");
				
			}
			
		});
			
		
	}
	public void blockProcessUpdateData
	(TableView<BlockProcessData> bpv, TableColumn<BlockProcessData, String> blockPid,TableColumn<BlockProcessData, String> blockTime) {
		
		blockPid.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BlockProcessData,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<BlockProcessData, String> param) {
				// TODO Auto-generated method stub
				
				return param.getValue().getBlockPidProperty();
			}
		});
		blockTime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BlockProcessData,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<BlockProcessData, String> param) {
				// TODO Auto-generated method stub
				
				return param.getValue().getBlockWaitTimeProperty();
			}
		});
	   bpv.setItems(this.blockProcessList);
	   
	}
}
