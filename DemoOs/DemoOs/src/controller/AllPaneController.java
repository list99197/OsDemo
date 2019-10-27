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
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import layout.table.data.BlockProcessData;
import layout.table.data.DeviceData;
import layout.table.data.FileDistributedData;
import layout.table.data.ReadyProcessData;
import layout.table.data.RunningProcessData;
import main.StartOs;
import progress.management.CPU;
import progress.management.ProcessManager;
import progress.management.SystemClock;

public class AllPaneController implements Initializable {
	@FXML
	private MenuItem about;
	@FXML
	private MenuItem useItem;
	@FXML
	private MenuBar osBar;
	@FXML
	private Menu systemMenu;
	@FXML
	private MenuItem exitItem;

	@FXML
	private Menu helpMenu;
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
	@FXML
	private TableView<RunningProcessData> runningProcessInfo;
	@FXML
	private TableColumn<RunningProcessData, String> remainTime;
	@FXML
	private TableColumn<RunningProcessData, String> result;
	@FXML
	private TableColumn<RunningProcessData, String> runningPid;
	@FXML
	private TableColumn<RunningProcessData, String> order;
	@FXML
	private TableView<ReadyProcessData> readyProcessInfo;
	@FXML
	private MenuItem startItem;
	@FXML
    private MenuItem pauseItem;
	@FXML
	private TableColumn<ReadyProcessData, String> readyPid;
	@FXML
	private TableView<BlockProcessData> blockProcessInfo;
	@FXML
	private TableColumn<BlockProcessData, String> blockWaitTime;
	@FXML
	private TableColumn<BlockProcessData, String> blockPid;
	private int state=0;
	private CPU cpu;
	public static DeviceController deviceController;
	private MemoryController memoryController;
	private FileController fileController;
	private RunningProcessController runningProcessController;
	private ReadyProcessController readyProcessController;
	private BlockProcessController blockProcessController;
	private Map<String, TreeItem<String>> map = new HashMap<String, TreeItem<String>>();
	private static Image simg = new Image("controller/folder25.png");
	private FileOperation fileOperation;
	private DiskController diskController;
	private ObservableList<FileDistributedData> fileDisListt = FXCollections.observableArrayList();
	private FileDisController fileDisController;
	private static ImageView img = new ImageView(simg);
	private SystemClock systemclock;

//        private TreeItem<String> hh=new TreeItem<String>("cc");
//        private TreeItem<String> hhh=new TreeItem<String>("vv");
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			this.fileOperation = new FileOperation();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		readyProcessController = new ReadyProcessController();
		readyProcessController.readyProcessUpdateData(readyProcessInfo, readyPid);
		blockProcessController = new BlockProcessController();
		blockProcessController.blockProcessUpdateData(blockProcessInfo, blockPid, blockWaitTime);
		runningProcessController = new RunningProcessController();
		runningProcessController.runProcessUpdateData(runningProcessInfo, runningPid, order, remainTime, result);
		deviceController = new DeviceController();
		deviceController.deviceUpdateData(deviceTv, deviceType, deviceRunPid, deviceWaitPid);
		cpu = new CPU(runningProcessController, readyProcessController, fileOperation, deviceController,blockProcessController);
		cpu.start();
		this.memoryController = new MemoryController(this.memoryPane);
		initializeTreeView();
		fileDisController = new FileDisController();
		fileDisController.fileDistributedUpdateData(fileDistributed, fileId, fileContext);
        systemclock = new SystemClock();
		systemclock.start();
		initCPULabel(clockLable);


		this.fileDisListt = fileDisController.getList();
		diskController = new DiskController(diskViewPane, fileOperation.getFATformat());
		try {
			this.fileController = new FileController(fileTreePane, fileOperation, fileDisListt, diskController, map);
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
		double startime = 0.0;
		Timeline timeline = new Timeline();
		timeline.setCycleCount(-1);
		Duration duration = Duration.millis(1000.0D);
		KeyFrame keyFrame = new KeyFrame(duration, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				double endtime = systemclock.getSystemTime();

				endtime = endtime - startime;
				String string = String.valueOf(endtime).substring(0, String.valueOf(endtime).lastIndexOf("."));
				label.setText(string + "s");

			}
		}, new javafx.animation.KeyValue[0]);
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

	@FXML
	public void miexit(ActionEvent event) {
		System.exit(0);
		cpu.stop();
	}

	@FXML
	public void aboutUsage(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);// 提醒窗口
		alert.setTitle("使用说明");
		alert.setHeaderText(null);

		DialogPane dialogPane = alert.getDialogPane();// 对话框
		Text textGap = new Text("\n");
		Text text1 = new Text("                              使用说明                  \n\n");
		text1.setStyle(
				"-fx-font-size: 34px; -fx-fill: blue; -fx-font-family: 'Helvetica', Microsoft JhengHei, sans-serif;");
		Text text2 = new Text("文件管理：" + " 所有关于文件的操作均可在文件目录面板点击右键进行选择；\n\n"
				+ "                  文件分为三种，分别是文件夹、txt文件以及可执行文件，每一种文件对应的操作都有所区别；\n\n"
				+ "                  磁盘共有256块，淡蓝色磁盘块表示未被使用，黄色则表示已被使用。\n\n");

		text2.setStyle("-fx-font-size: 17px; -fx-fill: black; -fx-font-family: 'Helvetica', Arial, sans-serif;");
		TextFlow flow = new TextFlow(textGap, text1, text2);
		dialogPane.setContent(flow);
		alert.showAndWait();
	}

	@FXML
	public void startProcess(ActionEvent event) {
		Timeline timeline = new Timeline();
	
		
	
		cpu.processManager.Choose_File(cpu.pcbArea);
		
		timeline.setCycleCount(-1);
		Duration duration = Duration.millis(4000.0D);
		KeyFrame keyFrame = new KeyFrame(duration, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
				cpu.processManager.Choose_File(cpu.pcbArea);

			}
		}, new javafx.animation.KeyValue[0]);
		timeline.getKeyFrames().add(keyFrame);
		
			timeline.play();
	}
	   @FXML
	   public void pause(ActionEvent event) {

	    }
	@FXML
	public void aboutSystem(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);// 提醒窗口
		alert.setTitle("关于系统");
		alert.setHeaderText(null);

		DialogPane dialogPane = alert.getDialogPane();// 对话框
		Text textGap = new Text("\n");
		Text text1 = new Text("               关于系统                  \n\n");
		text1.setStyle(
				"-fx-font-size: 34px; -fx-fill: blue; -fx-font-family: 'Helvetica', Microsoft JhengHei, sans-serif;");
		Text text2 = new Text("                              时间片：6s\n\n" + "                              内存大小：512B\n\n"
				+ "                              PCB总数：10个\n\n" + "                              磁盘块数：256块\n\n"
				+ "                              磁盘大小：16KB\n\n");

		text2.setStyle("-fx-font-size: 17px; -fx-fill: black; -fx-font-family: 'Helvetica', Arial, sans-serif;");
		TextFlow flow = new TextFlow(textGap, text1, text2);
		dialogPane.setContent(flow);
		alert.showAndWait();
	}
}
