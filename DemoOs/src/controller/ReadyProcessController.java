package controller;

import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import layout.table.data.ReadyProcessData;
import progress.management.PCB;

public class ReadyProcessController {
	private ObservableList<ReadyProcessData> readyProcessList= FXCollections.observableArrayList();
	//private List<PCB> readyPcb;
   public ReadyProcessController() {
	   for(int i=0;i<10;i++) {
		   this.readyProcessList.add(new ReadyProcessData());
	   }
		
	}
	public void updataReadyInfo(List<PCB> list) {
		if(list.size()==0) {
			 for(int i=0;i<10;i++) {
				 this.readyProcessList.get(i).setReadyPidBy(null);
			   }
		}else {
			for(int i=0;i<list.size();i++) {
			if(list.get(i)!=null) {
				
				this.readyProcessList.get(i).setReadyPidBy(list.get(i).getPid()+"");
				
			}
			for(int j=list.size();j<readyProcessList.size();j++) {
				this.readyProcessList.get(j).setReadyPidBy("");
			}
			
		}
		}
		
	}
	public void readyProcessUpdateData
	(TableView<ReadyProcessData> rpv, TableColumn<ReadyProcessData, String> tcrpp) {
		
		tcrpp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReadyProcessData,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<ReadyProcessData, String> param) {
				// TODO Auto-generated method stub
				
				return param.getValue().getReadyPidProperty();
			}
		});
		
	   rpv.setItems(this.readyProcessList);
	   
	}
}
