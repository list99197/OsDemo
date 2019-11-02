package Disk;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * 存储一个个文件的类
 */
public class FileData implements Serializable{
	 public static int TXT=0;//文档
     public static int FLODER=1;//文件夹
     public static int EXECUTABL=2;//可执行文件
     public static int FAT=3;//表文件
	  //如果是FAT类型则盘块的context没有意义，如果是文件夹类型context里面就是路径名词，如果是文档类型context就是内容
	 private ArrayList<Integer> usingDiskNum = new ArrayList<>();//记录文件占用的磁盘号数组，按照先后顺序
	 private ArrayList<String> FileNameUnderFloder = new ArrayList<>();//记录文件夹类型文件下面的所有文件名称，在删除文件夹时候对应删除目录下全部文件时候
	 
	 //
	 private ArrayList<String> FileNameStraightUnderFloder = new ArrayList<>();//记录文件夹类型文件下面一级直接关联的所有文件名称（绝对路径），作为树的孩子集合
	 //
	 
	 private String FileName;//磁盘内部存储的文件名称（包含路径的名称）绝对路径
     private int ListNum=0;//这个是文件夹文件专属的数据，其他类型的文件不用管这个数据没有意义,只记录文件下面直接的文件，间接加直接的文件是那个数组记录每次在这个目录下面加文件就加1，最大不能超8个，否则要添加分配磁盘
     private String Context;//磁盘单元具体存储的内容（只有文本文件有意义）
     private String ExeContext;//可执行文件的指令存储的地方，可以通过读取在老朱那边进行对应格式的分割称一条条指令，我这里对于输入的格式就不做要求了，如果在老朱那边读取格式不符合就返回false报错即可
     private int FileKind=this.TXT;//文件类型，有三种一种是文本文件，文件夹，操作文件,FAT。分别用0，1，2,3表示（后期建议用全局静态变量表示）
     private String FaterStraightFloder;//文件所在的上级文件的绝对路径名称
     //好像没什么用     private int useBasicDiskElement=0;//记录已经使用的磁盘块数
     //默认文件是txt先
     /*
      * 一开始如果第一次使用就自动生成256块这个对象的数组，然后以后打开直接读取即可
      */
     
     
     public String getFaterStraightFloderAbsName() {//作为树的指向父亲的指针，利用这个key去找父亲
    	 String [] splitResult=this.getFileName().split("\\\\");//分割文件名字重组
    	 String FaterStraightFloderAbsName=splitResult[0];
    	 for(int i=1;i<splitResult.length-1;i++) {
    		 FaterStraightFloderAbsName=FaterStraightFloderAbsName+"\\"+splitResult[i];
    	 }
    	 return FaterStraightFloderAbsName;
     }
     
     
     public String getTheShowName() {//获取要显示出来的名字，给界面部分用的函数
    	 String [] splitResult=this.getFileName().split("\\\\");//分割文件名字重组
    	 if(this.getFileKind()==this.TXT) {
    		 return splitResult[splitResult.length-1]+".txt";
    	 }
    	 else if(this.getFileKind()==this.EXECUTABL) 
    	 {
    		 return splitResult[splitResult.length-1]+".exe";
    	 }
    	 else if(this.getFileKind()==this.FLODER) {
    		 return splitResult[splitResult.length-1];
    	 }
    	 else 
    		 return splitResult[splitResult.length-1]+".fat";
     }
     
     
     public FileData(int num,String name,String context) {//生成txt文件
   	  this.usingDiskNum.add(num);//添加序号
   	  this.FileName=name;
      this.FileKind=this.TXT;
   	  this.Context=context;
   	  //this.useBasicDiskElement=1;
      this.ExeContext="";
   	  
     }
     public FileData(int num,String name) {//生成文件夹文件
      	  this.usingDiskNum.add(num);//添加序号
      	  this.FileName=name;
          this.FileKind=this.FLODER;
      	  this.Context="";
      	  this.ListNum=0;
      	 // this.useBasicDiskElement=1;
      	 this.ExeContext="";
        }
     //应该没用这个public方法，写着先
     public FileData(int num,String name,Boolean a) {//生成可执行文件或者是FAT文件，一个Boolean，true就是可执行，，false就是FAT
    	 //this.useBasicDiskElement=1; 
    	 if(a) {
    	  this.usingDiskNum.add(num);//添加序号
     	  this.FileName=name;
          this.FileKind=this.EXECUTABL;
     	  this.Context="";
     	  this.ExeContext="";
     	  }
     	  else {
     		 this.usingDiskNum.add(num);//添加序号
        	  this.FileName=name;
             this.FileKind=this.FAT;
        	  this.Context="";
        	  this.ExeContext="";
     	  }
     	  
       } 
     public Boolean WhetherContextOverLoad(String newContext) {
   	  //判断不超一个块的大小
   	  Boolean result=true;
   	  if(newContext.getBytes().length>64)//判断字节数是否大于64字节
   		  result=false;
   	  return result;
     }
    
//     //获取这个txt类型文件的context占据的字节大小,只有文本文件有意义
//     public int getTxtkindFileByteSize() {
// 		return this.Context.getBytes().length;
// 	}
//   //获取这个txt类型文件的context占据的字节大小,只有文本文件有意义
//     public int getFloderkindFileByteSize() {
// 		return 8*this.ListNum;
// 	}
//     //可执行文件就默认是64byte吧
     //合并上面的三个获取文件大小的函数
     public int getFileByteSize() {
    	 if(this.FileKind==this.TXT) {//如果是文本文件
    		// System.out.println("文本");
    		 return this.getContext().getBytes().length;
    	 }
    	 else if(this.FileKind==this.FLODER) {//如果是文件夹类型
    		// System.out.println("文件夹");
    		 return 8*this.getListNum();
    	 }
    	 else if(this.FileKind==this.EXECUTABL){//如果是可执行文件类型
    		// System.out.println("可执行");
    		 return this.getExeContext().getBytes().length;
    	 }
    	 else
    		// System.out.println("其他");
    		 return 64;
     }

     //get和set
     
	public ArrayList<Integer> getUsingDiskNum() {
		return usingDiskNum;
	}
	public void addUsingDiskNum(int num) {
		this.usingDiskNum.add(num);
	}
	public void ObjDeleteUsingDiskNum(int num) {//这个是删除等于num的元素
		this.usingDiskNum.remove(Integer.valueOf(num));
	}
	public void indexDeleteUsingDiskNum(int num) {//这个是删除num下标的元素
		this.usingDiskNum.remove(num);
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
	public String getExeContext() {
		return Context;
	}
	public void setExeContext(String context) {
		Context = context;
	}
	public int getFileKind() {
		return FileKind;
	}
	public void setFileKind(int fileKind) {
		FileKind = fileKind;
	}
	public int getListNum() {
		return ListNum;
	}
	public void setListNum(int listNum) {
		ListNum = listNum;
	}
//	public int getUseBasicDiskElement() {
//		return useBasicDiskElement;
//	}
//	public void setUseBasicDiskElement(int useBasicDiskElement) {
//		this.useBasicDiskElement = useBasicDiskElement;
//	}
	public ArrayList<String> getFileNameUnderFloder() {
		return FileNameUnderFloder;
	}
	public void addFileNameUnderFloder(String name) {
		this.FileNameUnderFloder.add(name);//添加到文件夹下
	}
	
	
	public ArrayList<String> getFileNameStraightUnderFloder() {
		return FileNameStraightUnderFloder;
	}


	public void addFileNameStraightUnderFloder(String name) {
		this.FileNameStraightUnderFloder.add(name);//添加到文件夹下
	}


	public void deleteFileNameUnderFloder(String name) {
		//为了防止运行出错首先需要有这个文件名称，才会运行
		if(this.FileNameUnderFloder.contains(name)) {
		this.FileNameUnderFloder.remove(name);//删除文件夹下面对应的文件名
		}	
	}
	public void deleteFileNameStraightUnderFloder(String name) {
		//为了防止运行出错首先需要有这个文件名称，才会运行
		if(this.FileNameStraightUnderFloder.contains(name)) {
		this.FileNameStraightUnderFloder.remove(name);//删除文件夹下面对应的文件名
		}	
	}
     
}
