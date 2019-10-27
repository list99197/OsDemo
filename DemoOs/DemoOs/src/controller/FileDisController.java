package controller;

import device.obj.Device;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import layout.table.data.DeviceData;
import layout.table.data.FileDistributedData;
import progress.obj.PCB;

public class FileDisController {
	private ObservableList<FileDistributedData> fileDisList= FXCollections.observableArrayList();
	//private FileDistributedData[] a1=new FileDistributedData[128];
   public FileDisController() {
		for(int i=0;i<256;i++) {
			this.fileDisList.add(new FileDistributedData()); 
			this.fileDisList.get(i).setFileidBy(""+i);
		}
		
	}
	public ObservableList<FileDistributedData> getList(){
		return this.fileDisList;
	}
	public void fileDistributedUpdateData
	(TableView<FileDistributedData> tv, TableColumn<FileDistributedData, String> tcFileID, TableColumn<FileDistributedData, String> tcFileCon) {
		
		tcFileID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FileDistributedData,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<FileDistributedData, String> param) {
				// TODO Auto-generated method stub
				
				return param.getValue().getFileidProperty();
			}
		});
		tcFileCon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FileDistributedData,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<FileDistributedData, String> param) {
				// TODO Auto-generated method stub
				
				return param.getValue().getFilecontextProperty();
			}
		});
		
	   tv.setItems(this.fileDisList);
	   
	}
}
