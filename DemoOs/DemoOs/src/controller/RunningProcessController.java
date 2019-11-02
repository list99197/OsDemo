package controller;


import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import layout.table.data.RunningProcessData;
import progress.management.PCB;

public class RunningProcessController {
		private ObservableList<RunningProcessData> runningProcessList= FXCollections.observableArrayList();
		private PCB runPcb;
	   public RunningProcessController() {
			this.runningProcessList.add(new RunningProcessData());
		}
	   public void clearRunningP() {
		   this.runningProcessList.get(0).setRunningPidBy("");
			this.runningProcessList.get(0).setRunOrderBy("");
			this.runningProcessList.get(0).setRunRemainTimeBy("");
			this.runningProcessList.get(0).setRunResultBy("");
	   }
		public void updataRunningInfo(PCB pcb,int remainTime,String order) {
			this.runningProcessList.get(0).setRunningPidBy(pcb.getPid()+"");
			this.runningProcessList.get(0).setRunOrderBy(order);
			this.runningProcessList.get(0).setRunRemainTimeBy(remainTime+"");
			this.runningProcessList.get(0).setRunResultBy(pcb.getRegisterState().getAX()+"");
		}
		public void runProcessUpdateData
		(TableView<RunningProcessData> rpv, TableColumn<RunningProcessData, String> tcrpp, TableColumn<RunningProcessData, String> tcrpo,TableColumn<RunningProcessData, String> tcrpt,TableColumn<RunningProcessData, String> tcrpr) {
			
			tcrpp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RunningProcessData,String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<RunningProcessData, String> param) {
					// TODO Auto-generated method stub
					
					return param.getValue().getRunningPidProperty();
				}
			});
			tcrpo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RunningProcessData,String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<RunningProcessData, String> param) {
					// TODO Auto-generated method stub
					
					return param.getValue().getRunOrderProperty();
				}
			});
			tcrpt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RunningProcessData,String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<RunningProcessData, String> param) {
					// TODO Auto-generated method stub
					
					return param.getValue().getRunRemainTimeProperty();
				}
			});
			tcrpr.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RunningProcessData,String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<RunningProcessData, String> param) {
					// TODO Auto-generated method stub
					
					return param.getValue().getRunResultProperty();
				}
			});
		   rpv.setItems(this.runningProcessList);
		   
		}
	

}
