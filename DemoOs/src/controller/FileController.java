package controller;


import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.KeyStroke;
import javax.xml.transform.Templates;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.media.jfxmedia.control.VideoDataBuffer;

import Disk.FileOperation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import layout.table.data.FileDistributedData;
import layout.table.data.NoteContro;
import layout.table.data.NoteFile;

public class FileController  {
	private DiskController diskController;
	private FileOperation fOperation;
	private TreeItem<String> beSelected;
	public static final String[] ILLEGAL_ELEMENTS = { "\\", ":", "?", "*", "<", ">", "|", "." };
private ContextMenu fileMenu;
private MenuItem miOpenFile;
private MenuItem miCreateFile;
private MenuItem miCreateDirectory;
private MenuItem miCreateExeCon;
private MenuItem miCreateExe;
private MenuItem miModifyExe;
private MenuItem miModifyName;
private MenuItem mideleteFile;
private MenuItem mideleteDiFile;
private MenuItem miCopyFile;
private MenuItem mipaste;
private Map<String, TreeItem<String>> map;
private int fileKind=4;
private int state=1;
private int copyState=0;
//private List<String> allParentPath=new ArrayList<String>();
private String copyPath="";
private String copyname="";
//private List<String> allCopyName=new ArrayList<String>();
private String parentPath="";
private TreeView<String> tv;
private int[] kind;
private ObservableList<FileDistributedData> fileDisList= FXCollections.observableArrayList();
public FileController(TreeView<String> tv,FileOperation fileOperation,ObservableList<FileDistributedData> fileDisListt,DiskController diskController,Map<String, TreeItem<String>> map)throws IOException, ClassNotFoundException {
	this.fOperation=fileOperation;
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile1");
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile2");
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile3");
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile4");
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile5");
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile6");
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile7");
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile8");
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile9");
	fOperation.createEXEFileAndAutoCreateExeContext("ROOT","ExeFile0");
	fOperation.SavingDiskAndFATFile();
	
	this.diskController=diskController;
	diskController.update(fOperation.getFATformat().getDiskState());
	fOperation.SavingDiskAndFATFile();
	this.tv=tv;
	this.map=map;
	this.fileDisList=fileDisListt;
	initializeContextMenu(); 
	this.tv.setContextMenu(fileMenu);
	kind=new int[fOperation.getFileDataGroup().size()-3];
	for(int i=3;i<fOperation.getFileDataGroup().size();i++) {
		kind[i-3]=fOperation.getFileDataGroup().get(i).getFileKind();
		
	}
	
//	List<String> list1=fOperation.getFileDataGroup().get(2).getFileNameUnderFloder();
//	String[] arrString=new String[list1.size()];
//	for(int i=0;i<list1.size();i++) {
//		arrString[i]=(String)list1.get(i);
//	}
//	String first=arrString[1].substring(arrString[1].lastIndexOf("\\")+1,arrString[1].length());
//	String temp=arrString[1].substring(0,arrString[1].lastIndexOf("\\"));//前面的，除掉最后的
//	System.out.println(temp);
	createNewFile(fOperation.getFileDataGroup().get(2).getFileNameUnderFloder(),kind);
	this.tv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {

		@Override
		public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
				TreeItem<String> newValue) {
			// TODO Auto-generated method stub
			parentPath="";
			beSelected=newValue;
			
			parentPath=beSelected.getValue();
			getname(beSelected);
			//allParentPath.add(parentPath);
			//allCopyName.add(beSelected.getValue());
			
			if(beSelected.getGraphic()!=null) {
				fileKind=1;
			}else if(beSelected.getValue().contains(".")) {
				fileKind=2;
			}else {
				fileKind=0;
			}
			
		}
		
	});
			
		
}
private void initializeContextMenu() {
    this.fileMenu = new ContextMenu();
    this.miOpenFile = new MenuItem("打开文件");
    this.miCreateFile = new MenuItem("新建txt文件");
    this.miCreateDirectory = new MenuItem("新建文件夹");
    this.miCreateExeCon=new MenuItem("新建exe文件（空）");
   this.miCreateExe=new MenuItem("新建exe文件(已有指令)");
   this.miModifyExe=new MenuItem("修改exe文件");
   this.miModifyName=new MenuItem("重命名");
   this.mideleteFile=new MenuItem("删除文件");
   this.mideleteDiFile=new MenuItem("删除文件夹");
   this.miCopyFile=new MenuItem("复制文件");
   this.mipaste=new MenuItem("粘贴文件");
   this.mipaste.setAccelerator(new KeyCodeCombination(KeyCode.V,KeyCombination.CONTROL_DOWN));
	this.miCopyFile.setAccelerator(new KeyCodeCombination(KeyCode.C,KeyCombination.CONTROL_DOWN));
    this.fileMenu.getItems().addAll(new MenuItem[] { this.miOpenFile, this.miCreateFile, this.miCreateDirectory,this.miCopyFile,this.miCreateExeCon,this.miCreateExe,this.mideleteDiFile,this.mideleteFile,this.miModifyName,this.miModifyExe,this.mipaste});
    
    this.fileMenu.setOnShowing(new EventHandler<WindowEvent>()
    {
      public void handle(WindowEvent event) {
        System.out.println(fileKind);
        if (fileKind==0) {
        	FileController.this.miOpenFile.setVisible(true);
        	FileController.this.miCreateFile.setVisible(false);
        	FileController.this.miCreateDirectory.setVisible(false);
        	FileController.this.miCopyFile.setVisible(true);
        	FileController.this.miCreateExe.setVisible(false);
        	FileController.this.miCreateExeCon.setVisible(false);
        	FileController.this.mideleteFile.setVisible(true);
        	FileController.this.mideleteDiFile.setVisible(false);
        	FileController.this.miModifyName.setVisible(true);
        	FileController.this.miModifyExe.setVisible(false);
        	FileController.this.mipaste.setVisible(true);
        	FileController.this.mipaste.setAccelerator(new KeyCodeCombination(KeyCode.V,KeyCombination.CONTROL_DOWN));
        	FileController.this.miCopyFile.setAccelerator(new KeyCodeCombination(KeyCode.C,KeyCombination.CONTROL_DOWN));
        } else if (fileKind==1){
        	FileController.this.miOpenFile.setVisible(false);
        	FileController.this.miCreateFile.setVisible(true);
        	FileController.this.miCreateDirectory.setVisible(true);
        	FileController.this.miCopyFile.setVisible(false);
        	FileController.this.miCreateExe.setVisible(true);
        	FileController.this.miCreateExeCon.setVisible(true);
        	FileController.this.mideleteFile.setVisible(false);
        	FileController.this.miModifyExe.setVisible(false);
        	FileController.this.mipaste.setVisible(true);
        	FileController.this.mipaste.setAccelerator(new KeyCodeCombination(KeyCode.V,KeyCombination.CONTROL_DOWN));
        	if(beSelected.getValue().equals("ROOT")) {
        		FileController.this.miModifyName.setVisible(false);
        		FileController.this.mideleteDiFile.setVisible(false);
        	}
        	else {
        		FileController.this.miModifyName.setVisible(true);
        		FileController.this.mideleteDiFile.setVisible(true);
        	}
        } else if (fileKind==2){
        	FileController.this.miOpenFile.setVisible(false);
        	FileController.this.miCreateFile.setVisible(false);
        	FileController.this.miCreateDirectory.setVisible(false);
        	FileController.this.miCopyFile.setVisible(true);
        	FileController.this.miCreateExe.setVisible(false);
        	FileController.this.miCreateExeCon.setVisible(false);
        	FileController.this.mideleteFile.setVisible(true);
        	FileController.this.mideleteDiFile.setVisible(false);
        	FileController.this.miModifyName.setVisible(true);
        	FileController.this.miModifyExe.setVisible(true);
        	FileController.this.mipaste.setVisible(false);
        	FileController.this.mipaste.setAccelerator(new KeyCodeCombination(KeyCode.V,KeyCombination.CONTROL_DOWN));
        	FileController.this.miCopyFile.setAccelerator(new KeyCodeCombination(KeyCode.C,KeyCombination.CONTROL_DOWN));
        }
      }
    });
    
    this.miCreateFile.setOnAction(new EventHandler<ActionEvent>()
        {
          public void handle(ActionEvent event) {
        	   state=1;
        	  FileController.this.createFile();
        	 
          }
        });
    
    this.miCreateDirectory.setOnAction(new EventHandler<ActionEvent>()
        {
          public void handle(ActionEvent event) {
        	  state=1;
        	  FileController.this.createDirectory();
        	  
          }
        });
    
    this.miOpenFile.setOnAction(new EventHandler<ActionEvent>()
        {
          public void handle(ActionEvent event) {
            state=1;
            FileController.this.openFile();
          }
        });
  
this.miCreateExeCon.setOnAction(new EventHandler<ActionEvent>()
{
  public void handle(ActionEvent event) {
	   state=1;
	  FileController.this.createExeCon();
	 
  }
});
this.miCreateExe.setOnAction(new EventHandler<ActionEvent>()
{
  public void handle(ActionEvent event) {
	   state=1;
	  FileController.this.createExe();
	 
  }
});
this.miModifyExe.setOnAction(new EventHandler<ActionEvent>()
{
  public void handle(ActionEvent event) {
	   state=1;
	  FileController.this.modifyExe();
	 
  }
});
this.miModifyName.setOnAction(new EventHandler<ActionEvent>()
{
  public void handle(ActionEvent event) {
	   state=1;
	  FileController.this.modifyName();
	 
  }
});
this.mideleteFile.setOnAction(new EventHandler<ActionEvent>()
{
  public void handle(ActionEvent event) {
	   state=1;
	  FileController.this.deleteFile();
	 
  }
});
this.mideleteDiFile.setOnAction(new EventHandler<ActionEvent>()
{
  public void handle(ActionEvent event) {
	   state=1;
	  FileController.this.deleteDiFile();
	 
  }
});
this.miCopyFile.setOnAction(new EventHandler<ActionEvent>()
{
  public void handle(ActionEvent event) {
	   state=1;
	  FileController.this.copyFile();
	 
  }
});
this.mipaste.setOnAction(new EventHandler<ActionEvent>()
{
  public void handle(ActionEvent event) {
	   state=1;
	  FileController.this.pasteFile();
	 
  }
});
}
private void digui(String[] arrString,int[] kind,String first,String second,String temp,String[] na,int[] nk) {
	
	
	String[] newArr=na;
	int[] newKind=nk;
	int count=0;
	for(int i=0;i<arrString.length;i++) {
		if(arrString[i]!=null) {

		first=arrString[i].substring(arrString[i].lastIndexOf("\\")+1,arrString[i].length());
		temp=arrString[i].substring(0,arrString[i].lastIndexOf("\\"));//前面的，除掉最后的
		
		if(temp.contains("\\")) {
			second=temp.substring(temp.lastIndexOf("\\")+1,temp.length());
			if(map.get(second)==null) {
				newArr[count]=arrString[i];
				newKind[count]=kind[i];
				count++;
			}else {
			if(kind[i]==1) {
				TreeItem<String> tempItem=new TreeItem<String>(first);
				tempItem.setGraphic(new ImageView(new Image("controller/folder25.png")));
			tempItem.setExpanded(true);
			map.get(second).getChildren().add(tempItem);
			map.put(tempItem.getValue(), tempItem);
			
			}else if(kind[i]==0) {
				TreeItem<String> tempItem=new TreeItem<String>(first);
				map.get(second).getChildren().add(tempItem);
				map.put(tempItem.getValue(), tempItem);
			}else {
				TreeItem<String> tempItem=new TreeItem<String>(first+".exe");
			
				map.get(second).getChildren().add(tempItem);
				map.put(tempItem.getValue(), tempItem);
				
			}
			
			
			for(int j=3;j<fileDisList.size()-1;j++) {
    			if(fileDisList.get(j).getfilecontext().equals("0")) {
    				fileDisList.get(j).setFilecontextBy("-1");
    				break;
    			}
    		}
		}
		}else {
			if(kind[i]==1) {
				TreeItem<String> tempItem=new TreeItem<String>(first);
				
				tempItem.setGraphic(new ImageView(new Image("controller/folder25.png")));
			tempItem.setExpanded(true);
			map.get("ROOT").getChildren().add(tempItem);
			map.put(tempItem.getValue(), tempItem);
			}else if(kind[i]==0) {
				TreeItem<String> tempItem=new TreeItem<String>(first);
				map.get("ROOT").getChildren().add(tempItem);
				map.put(tempItem.getValue(), tempItem);
			}else {
				TreeItem<String> tempItem=new TreeItem<String>(first+".exe");
				map.get("ROOT").getChildren().add(tempItem);
				map.put(tempItem.getValue(), tempItem);
			}
			
			
			for(int j=3;j<fileDisList.size()-1;j++) {
    			if(fileDisList.get(j).getfilecontext().equals("0")) {
    				fileDisList.get(j).setFilecontextBy("-1");
    				break;
    			}
    		}
		}
	}
	}
	if(count!=0) {
		first="";
		second="";
		temp="";
		for(int i=0;i<newArr.length;i++) {
			newArr[i]=null;
			newKind[i]=3;
		}
		digui(newArr, newKind,first,second,temp,newArr,newKind);
	}else {
		System.out.println(count+"结束digui");
	}
}
public void createNewFile(ArrayList<String> list,int[] kind) {
	String first="";
	String second="";
	String temp="";
	List<String> list1=list;
	String[] newarr=new String[list1.size()];
	int[] newkind=new int[list1.size()];
	String[] arrString=new String[list1.size()];
	System.out.println(list1+"lujing");
	for(int i=0;i<list1.size();i++) {
		arrString[i]=(String)list1.get(i);
		System.out.println(arrString[i]+"hhh");
	}
	int[] kindS;
	kindS=kind;
	digui(arrString, kindS,first,second,temp,newarr,newkind);
	
		
		
	
}
public void copyFile() {
	copyPath=parentPath;
	copyname=beSelected.getValue();
	copyState=1;
	
}
public void pasteFile() {
	if(copyState==1) {
		if(copyPath.contains(".")) {
			copyPath=copyPath.substring(0,copyPath.lastIndexOf("."));
    	}
		if(fOperation.copyTxtOrExeFile(copyPath,parentPath)) {
			System.out.println("cccccccccccccc");
			TreeItem<String> temmp=new TreeItem<String>(copyname);
    		beSelected.getChildren().add(temmp);
    		for(int i=3;i<fileDisList.size()-1;i++) {
    			if(fileDisList.get(i).getfilecontext().equals("0")) {
    				fileDisList.get(i).setFilecontextBy("-1");
    				break;
    			}
    		}
    		
    		System.out.println("复制成功");
		}
	}
	try {
		this.fOperation.SavingDiskAndFATFile();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	copyState=0;
	diskController.update(diskController.getFatBoss().getDiskState());
}
public void deleteDiFile() {
	if(fOperation.deleteFloderFile(parentPath)) {
		int count=beSelected.getChildren().size();
		count+=1;
		int j;int i;
		beSelected.getParent().getChildren().remove(beSelected);
		//allParentPath.remove(allParentPath.size()-1);
		//allCopyName.remove(allCopyName.size()-1);
		for(i=3;i<fileDisList.size()-1;i++) {
			if(fileDisList.get(i).getfilecontext().equals("0")) {
				j=i;
				break;
			}
		}
		for(j=i-1;j>i-count-1;j--) {
			fileDisList.get(j).setFilecontextBy("0");
		}
		try {
			this.fOperation.SavingDiskAndFATFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		diskController.update(diskController.getFatBoss().getDiskState());
		System.out.println("删除成功");
	}
	
}
public void deleteFile() {
	if(this.parentPath.contains(".")) {
		this.parentPath=parentPath.substring(0,parentPath.lastIndexOf("."));
	}
	if(fOperation.deleteTxtOrExeKindFile(parentPath)) {
		int j = 0;int i;
		beSelected.getParent().getChildren().remove(beSelected);
//		allParentPath.remove(allParentPath.size()-1);
//		allCopyName.remove(allCopyName.size()-1);
		for(i=3;i<fileDisList.size()-1;i++) {
			if(fileDisList.get(i).getfilecontext().equals("0")) {
				j=i;
				break;
			}
		}
		
			fileDisList.get(j-1).setFilecontextBy("0");
			try {
				this.fOperation.SavingDiskAndFATFile();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			diskController.update(diskController.getFatBoss().getDiskState());
		System.out.println("删除成功");
	}
	
}
public void modifyName() {
	TextInputDialog tid = new TextInputDialog();
    tid.setTitle("重命名");
    tid.setHeaderText("请输入新的文件名");
    tid.setContentText("新文件名:");
    tid.showAndWait();
    String result = (String)tid.getResult();
    
    for(int i=0;i<ILLEGAL_ELEMENTS.length;i++) {
    	if(result==null||result.contains(ILLEGAL_ELEMENTS[i])) {
    		state=0;
    		break;
    	}
    }
    if(state==1) {
    	
    	this.tv.refresh();
    	
    	if(this.parentPath.contains(".")) {
    		this.parentPath=parentPath.substring(0,parentPath.lastIndexOf("."));
    	}
    	this.fOperation.changeFileOrFloderName(result,this.parentPath);
    	
    	if(beSelected.getValue().contains(".")){
    		
        	beSelected.setValue(result+".exe");
        	map.put(beSelected.getValue(),beSelected);
        	
    	}else {
    		this.tv.refresh();
    	beSelected.setValue(result);
    	map.put(beSelected.getValue(),beSelected);
    	
    	}
    	
    	
    	try {
			this.fOperation.SavingDiskAndFATFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
public void modifyExe() {
	if(this.parentPath.contains(".")) {
		this.parentPath=parentPath.substring(0,parentPath.lastIndexOf("."));
	}
	int fileGroupNum=0;//保存所有文件的数组中这个文件所在的下标
	   for(int j=0;j<this.fOperation.getFileDataGroup().size();j++) {
		   if(this.fOperation.getFileDataGroup().get(j).getFileName().equals(parentPath)) {
			   fileGroupNum=j;
			   break;
		   }
	   }
	   String filecont=this.fOperation.getFileDataGroup().get(fileGroupNum).getContext();
	   System.out.println("ceshi"+filecont);
	  ;
	NoteFile noteFile=new NoteFile(filecont);
noteFile.showAndWait();

 
 fOperation.changeExeFileContext(NoteContro.context,parentPath);
 System.out.println(NoteContro.context);
 try {
		this.fOperation.SavingDiskAndFATFile();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
public void createExe() {
	TextInputDialog tid = new TextInputDialog();
    tid.setTitle("新建可执行文件（非空）");
    tid.setHeaderText("请输入文件名");
    tid.setContentText("文件名:");
    tid.showAndWait();
    String result = (String)tid.getResult();
    for(int i=0;i<ILLEGAL_ELEMENTS.length;i++) {
    	if(result==null||result.contains(ILLEGAL_ELEMENTS[i])) {
    		state=0;
    		break;
    	}
    }
    if(state==1) {
    	if(this.fOperation.createEXEFileAndAutoCreateExeContext(this.parentPath,result))
    	{
    		TreeItem<String> temmp=new TreeItem<String>(result+".exe");
    		beSelected.getChildren().add(temmp);
    		for(int i=3;i<fileDisList.size()-1;i++) {
    			if(fileDisList.get(i).getfilecontext().equals("0")) {
    				fileDisList.get(i).setFilecontextBy("-1");
    				break;
    			}
    		}
    		
    		System.out.println("新建成功");
    		
    	}
    	try {
			this.fOperation.SavingDiskAndFATFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	diskController.update(diskController.getFatBoss().getDiskState());
    }
}
public void createExeCon() {
	TextInputDialog tid = new TextInputDialog();
    tid.setTitle("新建可执行文件（空）");
    tid.setHeaderText("请输入文件名");
    tid.setContentText("文件名:");
    tid.showAndWait();
    String result = (String)tid.getResult();
    for(int i=0;i<ILLEGAL_ELEMENTS.length;i++) {
    	if(result==null||result.contains(ILLEGAL_ELEMENTS[i])) {
    		state=0;
    		break;
    	}
    }
    if(state==1) {
    	if(this.fOperation.createEXEFile(this.parentPath,result))
    	{
    		TreeItem<String> temmp=new TreeItem<String>(result+".exe");
    		beSelected.getChildren().add(temmp);
    		for(int i=3;i<fileDisList.size()-1;i++) {
    			if(fileDisList.get(i).getfilecontext().equals("0")) {
    				fileDisList.get(i).setFilecontextBy("-1");
    				break;
    			}
    		}
    		
    		System.out.println("新建成功");
    		
    	}
    	try {
			this.fOperation.SavingDiskAndFATFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	diskController.update(diskController.getFatBoss().getDiskState());
    }
}
 public void openFile() {
	 int fileGroupNum=0;//保存所有文件的数组中这个文件所在的下标
	   for(int j=0;j<this.fOperation.getFileDataGroup().size();j++) {
		   if(this.fOperation.getFileDataGroup().get(j).getFileName().equals(parentPath)) {
			   fileGroupNum=j;
			   break;
		   }
	   }
	   String filecont=this.fOperation.getFileDataGroup().get(fileGroupNum).getContext();
	  
	   NoteFile noteFile=new NoteFile(filecont);
 noteFile.showAndWait();

 fOperation.changeTxtFileContext(NoteContro.context,parentPath);
 System.out.println(NoteContro.context);
 try {
		this.fOperation.SavingDiskAndFATFile();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
public void createDirectory() {
	TextInputDialog tid = new TextInputDialog();
    tid.setTitle("新建文件夹");
    tid.setHeaderText("请输入文件夹名");
    tid.setContentText("文件夹名:");
    tid.showAndWait();
    String result = (String)tid.getResult();
    for(int i=0;i<ILLEGAL_ELEMENTS.length;i++) {
    	if(result==null||result.contains(ILLEGAL_ELEMENTS[i])) {
    		state=0;
    		break;
    	}
    }
    if(state==1) {
    	if(this.fOperation.createFLODERFile(this.parentPath,result))
    	{
    		TreeItem<String> temmp=new TreeItem<String>(result);
    		temmp.setGraphic(new ImageView(new Image("controller/folder25.png")));
    		temmp.setExpanded(true);
    		beSelected.getChildren().add(temmp);
    		for(int i=3;i<fileDisList.size()-1;i++) {
    			if(fileDisList.get(i).getfilecontext().equals("0")) {
    				fileDisList.get(i).setFilecontextBy("-1");
    				break;
    			}
    		}
    		System.out.println("新建成功");
    		
    	}
    	try {
			this.fOperation.SavingDiskAndFATFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	diskController.update(diskController.getFatBoss().getDiskState());
    }
    
}
public void createFile() {
	TextInputDialog tid = new TextInputDialog();
    tid.setTitle("新建txt文件");
    tid.setHeaderText("请输入文件名");
    tid.setContentText("文件名:");
    tid.showAndWait();
    String result = (String)tid.getResult();
    for(int i=0;i<ILLEGAL_ELEMENTS.length;i++) {
    	if(result==null||result.contains(ILLEGAL_ELEMENTS[i])) {
    		state=0;
    		break;
    	}
    }
    if(state==1) {
    	if(this.fOperation.createTXTFile(this.parentPath,result))
    	{
    		beSelected.getChildren().add(new TreeItem<String>(result));
    		for(int i=3;i<fileDisList.size()-1;i++) {
    			if(fileDisList.get(i).getfilecontext().equals("0")) {
    				fileDisList.get(i).setFilecontextBy("-1");
    				break;
    			}
    		}
    		System.out.println("新建成功");
    		
    	}
    	try {
			this.fOperation.SavingDiskAndFATFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	diskController.update(diskController.getFatBoss().getDiskState());
    }
    
}
public void getname(TreeItem<String> beItem) {
	TreeItem<String> temp;
	temp=beItem;
	while(temp.getParent()!=null) {
		parentPath=temp.getParent().getValue()+"\\" +parentPath;
		temp=temp.getParent();
	}
		}

}
