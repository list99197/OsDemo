package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.sun.glass.ui.TouchInputSupport;
import com.sun.javafx.logging.Logger;
import com.sun.media.jfxmedia.events.NewFrameEvent;

import Disk.FAT;
import Disk.FileOperation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import layout.table.data.DeviceData;
import layout.table.data.FileDistributedData;
import main.StartOs;


public class AllPaneController implements Initializable  {
	    @FXML
	    private TableColumn<DeviceData, String> deviceType;
        @FXML
	    private TableColumn<DeviceData, String> deviceRunPid;
        @FXML
	    private TableColumn<DeviceData, String> deviceWaitPid;
	    @FXML
	    private TableView<DeviceData> deviceTv;
	    @FXML
	    private Pane memoryPane;
	    @FXML
	    private Pane diskViewPane;
	    @FXML
	    private Label clockLable;
	    @FXML
	    private TreeView<String> fileTreePane;
	    @FXML
	    private TableColumn<FileDistributedData, String> fileContext;
	    @FXML
	    private TableColumn<FileDistributedData, String> fileId;
	    @FXML
	    private TableView<FileDistributedData> fileDistributed;
        private DeviceController deviceController;
        private MemoryController memoryController;
        private FileController fileController;
     private Map<String, TreeItem<String>> map=new HashMap<String, TreeItem<String>>();
        private static Image simg=new Image("controller/folder25.png");
        private FileOperation fileOperation;
        private DiskController diskController;
        private ObservableList<FileDistributedData> fileDisListt= FXCollections.observableArrayList();
        private FileDisController fileDisController;
        private static ImageView img=new ImageView(simg);
        
        private TreeItem<String> hh=new TreeItem<String>("cc");
        private TreeItem<String> hhh=new TreeItem<String>("vv");
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)  {
		initCPULabel(clockLable);
		try {
			this.fileOperation=new FileOperation();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		
		deviceController=new DeviceController();
deviceController.deviceUpdateData(deviceTv, deviceType, deviceRunPid, deviceWaitPid);
this.memoryController=new MemoryController(this.memoryPane);
initializeTreeView();
fileDisController=new FileDisController();
fileDisController.fileDistributedUpdateData(fileDistributed, fileId, fileContext);

this.fileDisListt=fileDisController.getList();
diskController=new DiskController(diskViewPane, fileOperation.getFATformat());
try {
	this.fileController=new FileController(fileTreePane,fileOperation,fileDisListt,diskController,map);
} catch (ClassNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


	}
	public static ImageView getImg() {
		return img;
	}

	  private void initCPULabel(Label label) {
		double startime=System.currentTimeMillis();
		  Timeline timeline = new Timeline();
		    timeline.setCycleCount(-1);
		    Duration duration = Duration.millis(1000.0D);
		    KeyFrame keyFrame = new KeyFrame(duration, 
		        new EventHandler<ActionEvent>()
		        {
		          public void handle(ActionEvent event) {
		        	  double endtime=System.currentTimeMillis();
		        	  
		        	  endtime=(endtime-startime)/1000;
		        	String string=  String.valueOf(endtime).substring(0, String.valueOf(endtime).lastIndexOf("."));
		        	  label.setText(string+"s");
		        	
		          }
		        },new javafx.animation.KeyValue[0]);
		    timeline.getKeyFrames().add(keyFrame);
		    timeline.play();
		  }
public void initializeTreeView() {
	TreeItem<String> rootItem = new TreeItem<String>("ROOT");
	rootItem.setExpanded(true);
	rootItem.setGraphic(img);
	
	this.fileTreePane.setRoot(rootItem);
	map.put("ROOT", rootItem);
}
}
