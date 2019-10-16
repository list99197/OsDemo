package controller;

import device.obj.Device;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import layout.table.data.DeviceData;
import progress.obj.PCB;
public class DeviceController {
	private ObservableList<DeviceData> deviceList= FXCollections.observableArrayList();
	private DeviceData a1=new DeviceData("A");
	private DeviceData a2=new DeviceData("A");
	private DeviceData b1=new DeviceData("B");
	private DeviceData b2=new DeviceData("B");
	private DeviceData b3=new DeviceData("B");
	private DeviceData c1=new DeviceData("C");
	private DeviceData c2=new DeviceData("C");
	private DeviceData c3=new DeviceData("C");
	private PCB[][] useTable=Device.getDeviceUseTable();
	private PCB[][] waitTable=Device.getDeviceWaitQueue();
   public DeviceController() {
		this.deviceList.add(a1);
		this.deviceList.add(a2);
		this.deviceList.add(b1);
		this.deviceList.add(b2);
		this.deviceList.add(b3);
		this.deviceList.add(c1);
		this.deviceList.add(c2);
		this.deviceList.add(c3);
		getPid();
	}
	private void getPid() {
		for(int i=0;i<2;i++) {
			if(useTable[0][i]!=null) {
				this.deviceList.get(i).setRunPIDBy(useTable[0][i].toString());
			}
		}
		for(int i=0;i<3;i++) {
			int j;
			j=i+2;
			if(useTable[1][i]!=null) {
				this.deviceList.get(j).setRunPIDBy(useTable[1][i].toString());
			}
		}
		for(int i=0;i<3;i++) {
			int j;
			j=i+5;
			if(useTable[2][i]!=null) {
				this.deviceList.get(j).setRunPIDBy(useTable[2][i].toString());
			}
		}
		String string1 = "";
		if(waitTable[0].length>0) {
		for(int i=0;i<waitTable[0].length;i++) {
				string1=string1+waitTable[0][i].toString()+"  ";
			this.deviceList.get(0).setWaitPIDBy(string1);
		}
		}
		String string2 = "";
		if(waitTable[1].length>0) {
		for(int i=0;i<waitTable[1].length;i++) {
				string2=string2+waitTable[1][i].toString()+"  ";
			this.deviceList.get(2).setWaitPIDBy(string2);
		}
		}
		String string3 = "";
		if(waitTable[2].length>0) {
		for(int i=0;i<waitTable[2].length;i++) {
				string3=string3+waitTable[2][i].toString()+"  ";
			this.deviceList.get(5).setWaitPIDBy(string3);
		}
		}
	}
	public void deviceUpdateData
	(TableView<DeviceData> tv, TableColumn<DeviceData, String> tcDeviceType, TableColumn<DeviceData, String> tcRunPID,TableColumn<DeviceData, String> tcWaitPID) {
		
		tcDeviceType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DeviceData,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<DeviceData, String> param) {
				// TODO Auto-generated method stub
				
				return param.getValue().getTypeProperty();
			}
		});
		tcRunPID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DeviceData,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<DeviceData, String> param) {
				// TODO Auto-generated method stub
				
				return param.getValue().getRunPIDProperty();
			}
		});
		tcWaitPID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DeviceData,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<DeviceData, String> param) {
				// TODO Auto-generated method stub
				
				return param.getValue().getWaitPIDProperty();
			}
		});
	   tv.setItems(this.deviceList);
	   
	}
}
