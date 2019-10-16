package Disk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/*
 * 分两个文件存储，在src\Disk相对路径下面一个FAT的文件存FAT表格，一个FileData的文件存每个创建的文件的大小内容名字等数据
 * 文件的存取只需要在整个程序的开启和关闭时候清算即可
 *模拟磁盘的根目录是root\
 *
 *之前想到在此生成时候的显示问题，因为有根目录，所以用那个分割取得上一级文件夹绝对路径名的方法就能一层层显示出文件夹和文件之间的关系了
 *生成的时候可以分两拨遍历先扫出全部的文本文件，有文件类型元素很简单，然后再扫其它文件显示出来
 *
 *避免一些麻烦所以所有文件不同类型也不可重名
 *
 *
 *复制，在题目中提及的是复制文件，所以只实现对于一般文件的复制，文件夹不允许复制，因为复制文件夹涉及文件夹下面太多文件的复制，最初的设计不太适合做这种复制
 root文件夹是根目录不能删除不然出错，因为里面有一些逻辑是默认保底有一个文件夹的存在的
 */
public class FileOperation {
	   private ArrayList<FileData> FileDataGroup;
       private FAT FATformat;
       
       
    public  int FindFileDataIdex(String name) {//根据绝对路径名称获取对应文件在文件数组里面的下标
    	int result=0;
    	for(int i=0;i<FileDataGroup.size();i++) {
    		if(FileDataGroup.get(i).getFileName().equals(name)) {
    			result=i;
    			break;
    		}
    	}
    	return result;
    }
       
       public ArrayList<FileData> getFileDataGroup() {
		return FileDataGroup;
	}


	public void setFileDataGroup(ArrayList<FileData> fileDataGroup) {
		FileDataGroup = fileDataGroup;
	}


	public FAT getFATformat() {
		return FATformat;
	}


	public void setFATformat(FAT fATformat) {
		FATformat = fATformat;
	}


	
	
	//初始化函数
    public FileOperation() throws IOException, ClassNotFoundException{
    File FileDataFile = new File("src/Disk/FileData.txt");
    File FATFile = new File("src/Disk/FAT.txt");
    if(FileDataFile.exists()) {//判断一个即可，因为会一起创建
         // 如果文件存在就读取即可
     //先读取FAT
     FileInputStream fisFAT = new FileInputStream(FATFile);
     ObjectInputStream objIPFAT = new ObjectInputStream(fisFAT);
     this.FATformat= (FAT)objIPFAT.readObject();
     objIPFAT.close(); 
     
     //读取Disk
     FileInputStream fisDisk = new FileInputStream(FileDataFile);
     ObjectInputStream objIPDisk = new ObjectInputStream(fisDisk);
     this.FileDataGroup= (ArrayList<FileData>)objIPDisk.readObject();
     objIPFAT.close();
     
    }
    else {
     //初始化具体的磁盘块
     this.FileDataGroup= new ArrayList<>();
     this.FileDataGroup.add(new FileData(0,"FAT1",false));//磁盘0，1默认FAT
     this.FileDataGroup.add(new FileData(1,"FAT2",false));
     this.FileDataGroup.add(new FileData(2,"ROOT"));//磁盘2是根目录文件夹
     //默认使用了
     //初始化FAT
     this.FATformat=new FAT();
     
     //因为判断文件不存在所以需要创立文件保存数据进入文件
     
     //创建FAT文件并存储数据
     FileOutputStream fosFAT = new FileOutputStream(FATFile);
     ObjectOutputStream objOPFAT = new ObjectOutputStream(fosFAT);
     objOPFAT.writeObject(this.FATformat);
     objOPFAT.close();
     
     //创建Disk文件存储数据
     FileOutputStream fosDisk = new FileOutputStream(FileDataFile);
     ObjectOutputStream objOPDisk = new ObjectOutputStream(fosDisk);
     objOPDisk.writeObject(this.FileDataGroup);
     objOPDisk.close();  
     
     
     //19.10.13增加部分，帮助老朱那边开局自动生成10个带有指令的那种exe
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile1");
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile2");
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile3");
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile4");
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile5");
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile6");
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile7");
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile8");
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile9");
//     createEXEFileAndAutoCreateExeContext("ROOT","ExeFile0");
       }
    } 
       
       //退出程序的保存函数，保存FAT表格和Disk数组两个文件
       public void SavingDiskAndFATFile() throws IOException, ClassNotFoundException{
    	  File FileDataFile = new File("src/Disk/FileData.txt");
     	  File FATFile = new File("src/Disk/FAT.txt");
    	   //创建FAT文件并存储数据
 		  FileOutputStream fosFAT = new FileOutputStream(FATFile);
 		  ObjectOutputStream objOPFAT = new ObjectOutputStream(fosFAT);
 		  objOPFAT.writeObject(this.FATformat);
 		  objOPFAT.close();
 		  
 		  //创建Disk文件存储数据
 		  FileOutputStream fosDisk = new FileOutputStream(FileDataFile);
 		  ObjectOutputStream objOPDisk = new ObjectOutputStream(fosDisk);
 		  objOPDisk.writeObject(this.FileDataGroup);
 		  objOPDisk.close();  
       }
       //获取字符串字节大小的办法String.getBytes()length
       
       //创建文本文件,一般创建文件都没有内容要后续打开再改写所以这里就没有修改内容的需要
         public Boolean createTXTFile(String road,String name) {//文件名是非绝对路径名，一般产生时候都应该是分开名字和前面路径，生成后再合起来
        	 Boolean result=false;//如果有空位分配就返回真否则返回假
        	 Boolean whetherSameName=false;//如果重名就true，这样就不会有下面的步骤直接判断创建失败
        	 name=road+"\\"+name;//得到绝对路径的名字
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(name)) {//判断有没有绝对路径名称一样的存在
        			 whetherSameName=true;
        			 break;
        		 }
        	 }
             if(!whetherSameName) {//如果重名无法继续分配直接创建失败
        	 int findnum;//可使用的磁盘分区
        	 for(int i=0;i<256;i++) {
        		 if(this.FATformat.getDiskState()[i]==0) {
        			 findnum=i;
        			 result=true;
        			 this.FATformat.setDiskState(i,-1);//分配出去FAT表对应状态字变成-1
        			 this.addOrDeleteFileStraightToFloder(name, road, true);// 获取road对应的所有文件夹文件，然后将这个绝对路径名称的文件夹文件里面的FileNameUnderFloder增加这个文件的名字
        			 FileData txtFile=new FileData(i,name,"");
                	 this.FileDataGroup.add(txtFile);
                	 break; 
        		 }
        	 }
            }
             return result;
         }
       //创建文件夹文件
         public Boolean createFLODERFile(String road,String name) {//创建文件所在的文件夹的绝对路径，文件的名字（非绝对路径）
        	 Boolean whetherSameName=false;//如果重名就true，这样就不会有下面的步骤直接判断创建失败
        	 Boolean result=false;//如果有空位分配就返回真否则返回假
        	 name=road+"\\"+name;//得到绝对路径的名字
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(name)) {//判断有没有绝对路径名称一样的存在
        			 whetherSameName=true;
        			 break;
        		 }
        	 }
             if(!whetherSameName) {//如果重名无法继续分配直接创建失败
        	 int findnum;//可使用的磁盘分区
        	 for(int i=0;i<256;i++) {
        		 if(this.FATformat.getDiskState()[i]==0) {
        			 findnum=i;
        			 result=true;
        			 this.FATformat.setDiskState(i,-1);//分配出去FAT表对应状态字变成-1
        			 this.addOrDeleteFileStraightToFloder(name, road, true);// 获取road对应的所有文件夹文件，然后将这个绝对路径名称的文件夹文件里面的FileNameUnderFloder增加这个文件的名字
        			 FileData FLODERFile=new FileData(i,name);
                	 this.FileDataGroup.add(FLODERFile);
                	 break; 
        		 }
        	  }
             }
             return result;
         }
  
       //创建可执行文件文件，两种模式任君挑选
         public Boolean createEXEFile(String road,String name) {
        	 Boolean whetherSameName=false;//如果重名就true，这样就不会有下面的步骤直接判断创建失败
        	 Boolean result=false;//如果有空位分配就返回真否则返回假
        	 name=road+"\\"+name;//得到绝对路径的名字
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(name)) {//判断有没有绝对路径名称一样的存在
        			 whetherSameName=true;
        			 break;
        		 }
        	 }
             if(!whetherSameName) {//如果重名无法继续分配直接创建失败
        	 int findnum;//可使用的磁盘分区
        	 for(int i=0;i<256;i++) {
        		 if(this.FATformat.getDiskState()[i]==0) {
        			 findnum=i;
        			 result=true;
        			 this.FATformat.setDiskState(i,-1);//分配出去FAT表对应状态字变成-1
        			 this.addOrDeleteFileStraightToFloder(name, road, true);// 获取road对应的所有文件夹文件，然后将这个绝对路径名称的文件夹文件里面的FileNameUnderFloder增加这个文件的名字
        			 FileData EXEFile=new FileData(i,name,true);
                	 this.FileDataGroup.add(EXEFile);
                	 break; 
        		 }
        	  }
             }
             return result;
         }
         
         public Boolean createEXEFileAndAutoCreateExeContext(String road,String name) {
        	 Boolean whetherSameName=false;//如果重名就true，这样就不会有下面的步骤直接判断创建失败
        	 Boolean result=false;//如果有空位分配就返回真否则返回假
        	 name=road+"\\"+name;//得到绝对路径的名字
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(name)) {//判断有没有绝对路径名称一样的存在
        			 whetherSameName=true;
        			 break;
        		 }
        	 }
             if(!whetherSameName) {//如果重名无法继续分配直接创建失败
        	 int findnum;//可使用的磁盘分区
        	 for(int i=0;i<256;i++) {
        		 if(this.FATformat.getDiskState()[i]==0) {
        			 findnum=i;
        			 result=true;
        			 this.FATformat.setDiskState(i,-1);//分配出去FAT表对应状态字变成-1
        			 this.addOrDeleteFileStraightToFloder(name, road, true);// 获取road对应的所有文件夹文件，然后将这个绝对路径名称的文件夹文件里面的FileNameUnderFloder增加这个文件的名字
        			 FileData EXEFile=new FileData(i,name,true);
        			 EXEFile.setExeContext("x=?;x++;x--;!A5;end;");//自动生成带内容的可执行文件的内容
                	 this.FileDataGroup.add(EXEFile);
                	 break; 
        		 }
        	  }
             }
             return result;
         }
         
         
       //修改文本文件
       public Boolean changeTxtFileContext(String newContext,String txtFileAbsName) {//参数修改后的新内容，以及文件的绝对路径名
    	   //如果文件类型不是txt，如果发生磁盘不够也会返回false
    	   //寻找对应文件
    	   Boolean whetherFileIsTxt=false;//文件是否是txt文件，如果不是函数返回值就是false
    	   int fileGroupNum=0;//保存所有文件的数组中这个文件所在的下标
    	   for(int j=0;j<this.FileDataGroup.size();j++) {
    		   if(this.FileDataGroup.get(j).getFileName().equals(txtFileAbsName)) {
    			   fileGroupNum=j;
    			   break;
    		   }
    	   }
    		if(this.FileDataGroup.get(fileGroupNum).getFileKind()==0) {//0就是TXT，如果文件不是txt文件则无需理会直接结束函数   
    			whetherFileIsTxt=true;
    	        //首先判断新旧内容所需的磁盘是增加还是减少了，然后做后续操作
    			int oldNeedDiskNum=0;
    		    int newNeedDiskNum=0;//先声明		
    			if((this.FileDataGroup.get(fileGroupNum).getFileByteSize()%64)==0&&this.FileDataGroup.get(fileGroupNum).getFileByteSize()!=0) {
        	       oldNeedDiskNum=(int)(this.FileDataGroup.get(fileGroupNum).getFileByteSize()/64);//旧的内容所需磁盘块数
  
    			}
    			else {
    				oldNeedDiskNum=(int)(this.FileDataGroup.get(fileGroupNum).getFileByteSize()/64)+1;
    			}
    			if((newContext.getBytes().length%64)==0&&newContext.getBytes().length!=0) {
    				newNeedDiskNum=(int)(newContext.getBytes().length/64);//新的内容所需磁盘块数
    			}
    			else {
    				newNeedDiskNum=(int)(newContext.getBytes().length/64)+1;
    			}
    			
    			
    			
    			//测试代码
//    			System.out.println("旧"+oldNeedDiskNum);
//    			System.out.println("新"+newNeedDiskNum);
    	        //
    			
    			
    			//分三种情况，新的内容需要更多磁盘，新的内容不需要增减磁盘，新的内容不需要原来那么多磁盘
    	        if(oldNeedDiskNum<newNeedDiskNum) {//要分配更多磁盘
    	        	int additionDiskNumRequest=newNeedDiskNum-oldNeedDiskNum;//需要再给这个文件分配的磁盘块数
    	        	//先要判断FAT还够不够分配
    	        	int avaliableDiskTotalNum=0;//可用的磁盘总数目
    	        	int [] avaliableDiskNum=new int[additionDiskNumRequest];//这个是一会儿遍历判断是否足够数目时候顺便一起记录方便后面分配不用在此遍历
    	        	int recordNum=0;//记录循环时候数组记录下来的可用磁盘数量，只要记录要用的数量即可
    	        	for(int i=0;i<256;i++) {
    	        		if(this.FATformat.getDiskState()[i]==0) {
    	        			if(recordNum<additionDiskNumRequest) {//只记录需要多出的数量的可用磁盘号
    	        			avaliableDiskNum[recordNum]=i;//记录可用的磁盘号
    	        			recordNum++;
    	        			}
    	        			avaliableDiskTotalNum++;
    	        		}
    	        	}
    	        	if(avaliableDiskTotalNum<additionDiskNumRequest) {
    	        		return false;//如果数量不够说明无法分配返回false不做任何修改,直接结束函数
    	        	}
    	        	for(int i=0;i<additionDiskNumRequest;i++) {//循环分配
    	        		int lastDiskNum=this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().get(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-1);//先获取前一个磁块的号码
    	        		this.FATformat.setDiskState(lastDiskNum,avaliableDiskNum[i]);//先修改FAT中的前一个磁块的状态字，让它指向新分配磁块
    	        		this.FATformat.setDiskState(avaliableDiskNum[i],-1);//在修改新磁块的状态字，变成-1
    	        		this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().add(avaliableDiskNum[i]);//再在fileData对应的对象的usingDiskNum这个数组中后面添加这个磁块号
    	        	}
    	        	this.FileDataGroup.get(fileGroupNum).setContext(newContext);
    	        }
    	        else if(oldNeedDiskNum>newNeedDiskNum) {//要分配更少磁盘
    	        	//因为是减少就不需要判断不够内磁盘的问题
    	        	//只需要保留新的需要的磁盘就好了
    	        	int freeDiskNumRequest=oldNeedDiskNum-newNeedDiskNum;//需要释放的磁盘数
    	        	for(int i=0;i<freeDiskNumRequest;i++) {//循环分配
    	                  int endFileFATNum=this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().get(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-1);//找到FileData的UsingDiskNum数组的最后一个记录的FAT号
    	                  int beforeEndFileFATNum=this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().get(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-2);//找到FileData的UsingDiskNum数组的倒数第二个记录的FAT号
    	                  this.FATformat.setDiskState(endFileFATNum,0);//修改FATformat里面的UsingDiskNum最后一个号码的状态变成0
    	                  this.FATformat.setDiskState(beforeEndFileFATNum,-1);//修改FATformat里面的UsingDiskNum倒数第二个号码的状态变成-1
    	                  this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().remove(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-1);//删除UsingDiskNum最后一个元素
    	        	}
    	        	this.FileDataGroup.get(fileGroupNum).setContext(newContext);
    	        }
    	        else {//不需要修改磁盘，直接修改内容即可
    	        	this.FileDataGroup.get(fileGroupNum).setContext(newContext);
    	        }

    		}
    		return whetherFileIsTxt;
       }
       
       //修改可执行文件的指令内容
       public Boolean changeExeFileContext(String newExeContext,String exeFileAbsName) {//参数修改后的新内容，以及文件的绝对路径名
    	   //如果文件类型不是exe，如果发生磁盘不够也会返回false
    	   //寻找对应文件
    	   Boolean whetherFileIsExe=false;//文件是否是txt文件，如果不是函数返回值就是false
    	   int fileGroupNum=0;//保存所有文件的数组中这个文件所在的下标
    	   for(int j=0;j<this.FileDataGroup.size();j++) {
    		   if(this.FileDataGroup.get(j).getFileName().equals(exeFileAbsName)) {
    			   fileGroupNum=j;
    			   break;
    		   }
    	   }
    		if(this.FileDataGroup.get(fileGroupNum).getFileKind()==2) {//2就是Exe，如果文件不是txt文件则无需理会直接结束函数   
    			whetherFileIsExe=true;
    	        //首先判断新旧内容所需的磁盘是增加还是减少了，然后做后续操作
    			int oldNeedDiskNum=0;
    		    int newNeedDiskNum=0;//先声明		
    			if((this.FileDataGroup.get(fileGroupNum).getFileByteSize()%64)==0&&this.FileDataGroup.get(fileGroupNum).getFileByteSize()!=0) {
        	       oldNeedDiskNum=(int)(this.FileDataGroup.get(fileGroupNum).getFileByteSize()/64);//旧的内容所需磁盘块数
  
    			}
    			else {
    				oldNeedDiskNum=(int)(this.FileDataGroup.get(fileGroupNum).getFileByteSize()/64)+1;
    			}
    			if((newExeContext.getBytes().length%64)==0&&newExeContext.getBytes().length!=0) {
    				newNeedDiskNum=(int)(newExeContext.getBytes().length/64);//新的内容所需磁盘块数
    			}
    			else {
    				newNeedDiskNum=(int)(newExeContext.getBytes().length/64)+1;
    			}
    			
    			
    			
    			//测试代码
//    			System.out.println("旧"+oldNeedDiskNum);
//    			System.out.println("新"+newNeedDiskNum);
    	        //
    			
    			
    			//分三种情况，新的内容需要更多磁盘，新的内容不需要增减磁盘，新的内容不需要原来那么多磁盘
    	        if(oldNeedDiskNum<newNeedDiskNum) {//要分配更多磁盘
    	        	int additionDiskNumRequest=newNeedDiskNum-oldNeedDiskNum;//需要再给这个文件分配的磁盘块数
    	        	//先要判断FAT还够不够分配
    	        	int avaliableDiskTotalNum=0;//可用的磁盘总数目
    	        	int [] avaliableDiskNum=new int[additionDiskNumRequest];//这个是一会儿遍历判断是否足够数目时候顺便一起记录方便后面分配不用在此遍历
    	        	int recordNum=0;//记录循环时候数组记录下来的可用磁盘数量，只要记录要用的数量即可
    	        	for(int i=0;i<256;i++) {
    	        		if(this.FATformat.getDiskState()[i]==0) {
    	        			if(recordNum<additionDiskNumRequest) {//只记录需要多出的数量的可用磁盘号
    	        			avaliableDiskNum[recordNum]=i;//记录可用的磁盘号
    	        			recordNum++;
    	        			}
    	        			avaliableDiskTotalNum++;
    	        		}
    	        	}
    	        	if(avaliableDiskTotalNum<additionDiskNumRequest) {
    	        		return false;//如果数量不够说明无法分配返回false不做任何修改,直接结束函数
    	        	}
    	        	for(int i=0;i<additionDiskNumRequest;i++) {//循环分配
    	        		int lastDiskNum=this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().get(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-1);//先获取前一个磁块的号码
    	        		this.FATformat.setDiskState(lastDiskNum,avaliableDiskNum[i]);//先修改FAT中的前一个磁块的状态字，让它指向新分配磁块
    	        		this.FATformat.setDiskState(avaliableDiskNum[i],-1);//在修改新磁块的状态字，变成-1
    	        		this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().add(avaliableDiskNum[i]);//再在fileData对应的对象的usingDiskNum这个数组中后面添加这个磁块号
    	        	}
    	        	this.FileDataGroup.get(fileGroupNum).setExeContext(newExeContext);
    	        }
    	        else if(oldNeedDiskNum>newNeedDiskNum) {//要分配更少磁盘
    	        	//因为是减少就不需要判断不够内磁盘的问题
    	        	//只需要保留新的需要的磁盘就好了
    	        	int freeDiskNumRequest=oldNeedDiskNum-newNeedDiskNum;//需要释放的磁盘数
    	        	for(int i=0;i<freeDiskNumRequest;i++) {//循环分配
    	                  int endFileFATNum=this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().get(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-1);//找到FileData的UsingDiskNum数组的最后一个记录的FAT号
    	                  int beforeEndFileFATNum=this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().get(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-2);//找到FileData的UsingDiskNum数组的倒数第二个记录的FAT号
    	                  this.FATformat.setDiskState(endFileFATNum,0);//修改FATformat里面的UsingDiskNum最后一个号码的状态变成0
    	                  this.FATformat.setDiskState(beforeEndFileFATNum,-1);//修改FATformat里面的UsingDiskNum倒数第二个号码的状态变成-1
    	                  this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().remove(this.FileDataGroup.get(fileGroupNum).getUsingDiskNum().size()-1);//删除UsingDiskNum最后一个元素
    	        	}
    	        	this.FileDataGroup.get(fileGroupNum).setExeContext(newExeContext);
    	        }
    	        else {//不需要修改磁盘，直接修改内容即可
    	        	this.FileDataGroup.get(fileGroupNum).setExeContext(newExeContext);
    	        }

    		}
    		return whetherFileIsExe;
       }
       
       //修改文件或者文件夹名称
       public void changeFileOrFloderName(String newName,String oldAbsName) {//两个参数一个是修改后的非绝对名称，一个是修改前的绝对名称
    	   String [] splitOldName=oldAbsName.split("\\\\");//分割
    	   String textName=splitOldName[splitOldName.length-1];//获取非绝对的名字
    	   String newAbsName=splitOldName[0];
    	   for(int i=1;i<splitOldName.length-1;i++) {
    		   newAbsName=newAbsName+"\\"+splitOldName[i];
    	   }
    	   newAbsName=newAbsName+"\\"+newName;//新的文件的绝对路径名
    	   Boolean whetherSameName=false;//是否新名字和旧名字一样
    	   if(textName.equals(newName)) {
    		   whetherSameName=true;
    	   }
    	   if(!whetherSameName) {
    		   String [] roadNameGroup=this.splitRoadToGetAllAboveFloder(oldAbsName);//分割后最后一个字符串是旧名字的绝对路径本身，所以不管这个
    	       for(int i=0;i<roadNameGroup.length-1;i++) {
    	    	   int FileGroupNum=0;
    	    	   int fileNameNum=0;
    	    	    for(int j=0;j<this.FileDataGroup.size();j++) {
    	    		   if(this.FileDataGroup.get(j).getFileName().equals(roadNameGroup[i])) {
    	    			   //测试输出代码
    	    			   //System.out.println(this.FileDataGroup.get(j).getFileName()+"  "+roadNameGroup[i]);
    	    			   FileGroupNum=j;
    	    			   break;
    	    		   }
    	    		  } //在FileDataGroup找到对应名称的文件夹对象
    	    		for(int k=0;k<this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().size();k++) {
    	    			if(this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().get(k).equals(oldAbsName)) {
    	    				//System.out.println("第二个for"+this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().get(k)+"  "+oldAbsName);
    	    				fileNameNum=k;
    	    				break;
    	    			}
    	    		}
    	    	  //找到对象里面这个旧名字的下标
    	    	    
    	    	    //测试代码
    	    	    //System.out.println("修改的旧名字"+ this.FileDataGroup.get(FileGroupNum).getFileName()+" "+this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().get(fileNameNum));
    	    	    
    	    	    
    	    	    this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().set(fileNameNum,newAbsName);
    	    	    //修改文件夹下该文件名的记录
    	    	    if(i==roadNameGroup.length-2) {
    	    	    	int StraightIndex=0;
    	    	    	for(int b=0;b<this.FileDataGroup.get(FileGroupNum).getFileNameStraightUnderFloder().size();b++) {
    		    			if(this.FileDataGroup.get(FileGroupNum).getFileNameStraightUnderFloder().get(b).equals(oldAbsName)) {
    		    				//System.out.println("第二个for"+this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().get(k)+"  "+oldAbsName);
   		    				StraightIndex=b;
   		    				break;
    		    			}
    		    		}
    	    	    	this.FileDataGroup.get(FileGroupNum).getFileNameStraightUnderFloder().set(StraightIndex,newAbsName);
    	    	    }
    	       }
    	       
//    	       //还有修改直接上级文件夹对应的文件的名称,用于存储孩子的数组
//    	       int StraightIndex=1;
//    	       for(int k=0;k<this.FileDataGroup.get(this.FindFileDataIdex(roadNameGroup[roadNameGroup.length-1])).getFileNameStraightUnderFloder().size();k++) {
//	    			if(this.FileDataGroup.get(FindFileDataIdex(roadNameGroup[roadNameGroup.length-1])).getFileNameStraightUnderFloder().get(k).equals(oldAbsName)) {
//	    				//System.out.println("第二个for"+this.FileDataGroup.get(FileGroupNum).getFileNameUnderFloder().get(k)+"  "+oldAbsName);
//	    				StraightIndex=k;
//	    				break;
//	    			}
//	    		}
//    	   	   System.out.print(StraightIndex);
//    	       this.FileDataGroup.get(FindFileDataIdex(roadNameGroup[roadNameGroup.length-1])).getFileNameStraightUnderFloder().set(StraightIndex,newAbsName);
//    	       //
//    	       
    	       
    	       for(int j=0;j<this.FileDataGroup.size();j++) {
	    		   if(this.FileDataGroup.get(j).getFileName().equals(oldAbsName)) {
	    			   this.FileDataGroup.get(j).setFileName(newAbsName);
	    			   break;
	    		   } 
    	            //修改这个文件名
    	          }
    	   }
       }
         
       //增加或者减少文件夹下文件，是直接在文件夹下一级的文件加入那种，还有一种是方便删除文件一起删除的是
       ////注意这个函数只是修改了其路径上的文件夹的相关信息和文件夹的FAT的分配问题，但是不包含添加或删除在文件夹下文件本身的在FileData数组里的创建或者删除
         public Boolean addOrDeleteFileStraightToFloder(String fileName,String flodername,Boolean whetherAdd) {//参数是要添加或删去的文件的绝对路径名和添加到的文件夹的绝对路径名
        	 Boolean result=false;//如果分配成功就是true返回否则就是false  
        	 //那个参数true就是增加到，false就是删去
        	 Boolean whetherFloderFileExsit=false;//文件夹文件这个绝对路径名字是否是存在的
        	 int floderFileDataNum=0;//对应文件夹名的下标号
        	 //先寻找对应的floder文件
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(flodername)) {//判断有没有绝对路径名称一样的存在
        			 floderFileDataNum=i;//赋值下标号
        			 whetherFloderFileExsit=true;
        			 break;
        		 }
        	 }
        	 if(whetherFloderFileExsit) {//存在这个文件夹才有必要进行下面的操作
        	     if(whetherAdd) {//增加到
                      if(this.FileDataGroup.get(floderFileDataNum).getListNum()>0&&this.FileDataGroup.get(floderFileDataNum).getListNum()%8==0) {//超出了文件夹下面最多8文件的限制因为是用的总和每次超出都是已经在八的临界值了    
        		          //分配FAT
                    	  for(int i=0;i<256;i++) {
            		        if(this.FATformat.getDiskState()[i]==0) {
            			     result=true;
            			     int LastFatNum=this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().get(this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().size()-1);//因为添加都是按照先用在前原则所以这个数组里面的盘块本就是有顺序的，取最后一个的块号
            			     this.FATformat.setDiskState(LastFatNum, i);//原本最后一块的末尾指向新加的块号
            			     this.FATformat.setDiskState(i,-1);//分配出去FAT表对应状态字变成-1
            			     this.FileDataGroup.get(floderFileDataNum).addUsingDiskNum(i);
            			     //分配的链式也要修改好信息FAT的下一个号码，usingDiskNum也要更新 
            			     String[] AllAboveFloderName=this.splitRoadToGetAllAboveFloder(flodername);
            			     //先处理非直接上级的目录文档
            			     for(int j=0;j<AllAboveFloderName.length-1;j++) {//先给这些间接上级文档录入新增文件名称
            			    	 this.addOrDeleteFileUnstraightToFloder(fileName,AllAboveFloderName[j], whetherAdd);
            			     }
            			     //处理直接的上级目录文档
            			     this.FileDataGroup.get(floderFileDataNum).addFileNameUnderFloder(fileName);
            			     this.FileDataGroup.get(floderFileDataNum).addFileNameStraightUnderFloder(fileName);
            			     this.FileDataGroup.get(floderFileDataNum).setListNum(this.FileDataGroup.get(floderFileDataNum).getListNum()+1);
                    	    //获取road对应的所有文件夹文件，然后将这个绝对路径名称的文件夹文件里面的FileNameUnderFloder增加这个文件的名字
                    	    //本文件夹这一级的，应为是直接关联特殊处理,listnum也要加1
                    	     break; 
            		      }
        		        }
        		      }
                      else {//分配的磁盘块空间还够就不需要FAT处理了，只要修改下FileGroup就好
                    	  result=true;
                    	  String[] AllAboveFloderName=this.splitRoadToGetAllAboveFloder(flodername);
         			     //先处理非直接上级的目录文档
         			     for(int j=0;j<AllAboveFloderName.length-1;j++) {//先给这些间接上级文档录入新增文件名称
         			    	 this.addOrDeleteFileUnstraightToFloder(fileName,AllAboveFloderName[j], whetherAdd);
         			     }
         			     //处理直接的上级目录文档
         			     this.FileDataGroup.get(floderFileDataNum).addFileNameUnderFloder(fileName);
         			     this.FileDataGroup.get(floderFileDataNum).addFileNameStraightUnderFloder(fileName);
         			     this.FileDataGroup.get(floderFileDataNum).setListNum(this.FileDataGroup.get(floderFileDataNum).getListNum()+1);
                 	    //获取road对应的所有文件夹文件，然后将这个绝对路径名称的文件夹文件里面的FileNameUnderFloder增加这个文件的名字
                 	    //本文件夹这一级的，应为是直接关联特殊处理,listnum也要加1
                      }
                      
        	     }
             	 else {//删除到
        		 result=true;//因为删除不可能空间不够
        		    if(this.FileDataGroup.get(floderFileDataNum).getListNum()>8&&this.FileDataGroup.get(floderFileDataNum).getListNum()%8==1) {//当listnum大于8且可以用八求余正好是1就代表删除后可以去掉一个占用的FAT块单元
        		    	int LastFatNum=this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().get(this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().size()-1);//因为添加都是按照先用在前原则所以这个数组里面的盘块本就是有顺序的，取最后一个的块号
       			        int LastLastFatNum=this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().get(this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().size()-2);
        		    	this.FATformat.setDiskState(LastFatNum, 0);//原本最后一块的末尾恢复0，空出来了
       			        this.FATformat.setDiskState(LastLastFatNum,-1);//FAT表倒数第二个这个文件夹使用的块对应状态字变成-1变为最后一个
       			        this.FileDataGroup.get(floderFileDataNum).indexDeleteUsingDiskNum(this.FileDataGroup.get(floderFileDataNum).getUsingDiskNum().size()-1);//删除尾部一个
        		        //删除文件在各级上面文件夹里面的信息
       			     String[] AllAboveFloderName=this.splitRoadToGetAllAboveFloder(flodername);
    			     //先处理非直接上级的目录文档
    			     for(int j=0;j<AllAboveFloderName.length-1;j++) {//先给这些间接上级文档录入新增文件名称
    			    	 this.addOrDeleteFileUnstraightToFloder(fileName,AllAboveFloderName[j], false);//false表示是delete，true是add
    			     }
    			     //处理直接的上级目录文档
    			     this.FileDataGroup.get(floderFileDataNum).deleteFileNameUnderFloder(fileName);
    			     this.FileDataGroup.get(floderFileDataNum).deleteFileNameStraightUnderFloder(fileName);
    			     this.FileDataGroup.get(floderFileDataNum).setListNum(this.FileDataGroup.get(floderFileDataNum).getListNum()-1);
    			   
        		    }
        		    else {//否则就不需要处理FAT和usingdisknum这些问题
        		    	result=true;
                  	  String[] AllAboveFloderName=this.splitRoadToGetAllAboveFloder(flodername);
       			     //先处理非直接上级的目录文档
       			     for(int j=0;j<AllAboveFloderName.length-1;j++) {//先给这些间接上级文档录入新增文件名称
       			    	 this.addOrDeleteFileUnstraightToFloder(fileName,AllAboveFloderName[j], false);//false表示是delete，true是add
       			     }
       			     //处理直接的上级目录文档
       			     this.FileDataGroup.get(floderFileDataNum).deleteFileNameUnderFloder(fileName);
       			     this.FileDataGroup.get(floderFileDataNum).deleteFileNameStraightUnderFloder(fileName);
       			     this.FileDataGroup.get(floderFileDataNum).setListNum(this.FileDataGroup.get(floderFileDataNum).getListNum()-1);
               	    //获取road对应的所有文件夹文件，然后将这个绝对路径名称的文件夹文件里面的FileNameUnderFloder去掉这个文件的名字
               	    //本文件夹这一级的，应为是直接关联特殊处理,listnum也要减1
        		    }
        	     }
        	 }
        	 return result;
         }
       //增加或者减少文件夹下文件，是在文件夹下两级或跟多的间接的文件加入那种(辅助直接添加的一个函数)，只是改变数据，具体文件的在filedata的删除是没有的
         public Boolean addOrDeleteFileUnstraightToFloder(String fileName,String flodername,Boolean whetherAdd) {//参数是要添加或删去的文件的绝对路径名和添加到的文件夹的绝对路径名
        	 Boolean result=false;//如果分配成功就是true返回否则就是false  
        	 //那个参数true就是增加到，false就是删去
        	 Boolean whetherFloderFileExsit=false;//文件夹文件这个绝对路径名字是否是存在的
        	 int floderFileDataNum=0;//对应文件夹名的下标号
        	 //先寻找对应的floder文件
        	 for(int i=0;i<this.FileDataGroup.size();i++) {
        		 if(this.FileDataGroup.get(i).getFileName().equals(flodername)) {//判断有没有绝对路径名称一样的存在
        			 floderFileDataNum=i;//赋值下标号
        			 whetherFloderFileExsit=true;
        			 break;
        		 }
        	 }
        	 if(whetherFloderFileExsit) {//存在这个文件夹才有必要进行下面的操作
        		 result=false;
        		 if(whetherAdd) {//增加到，因为是间接的所以只需要增加到FileData的名字数组中即可
        			 this.FileDataGroup.get(floderFileDataNum).addFileNameUnderFloder(fileName); 
        			 result=true;
        		 }
             	 else {//删除到
             		//搜索对应的文件名下标
             		this.FileDataGroup.get(floderFileDataNum).deleteFileNameUnderFloder(fileName); 
       			    result=true;	 
        	     }
        	 }
        	 return result;//因为静态方法里面不能用this所以修改后还是返回这个变量吧
         } 
         //删除时候都加个判断找不到上级就跳过，防止程序出错卡死
       //删除一般文件,这个大致就是分割获得一层层上级，一级级上级文件夹对于这个文件名的记录删除，然后释放这个文件的FAT，最后在FileData数组里面删去这个文件
       public Boolean deleteTxtOrExeKindFile(String fileAbsName) {//参数是这个文件的绝对路径名称，
    	    Boolean result=false;
    	    String allAboveFloderAbs[]=this.splitRoadToGetAllAboveFloder(fileAbsName);//由于这个分割方法最后一个元素就是输入参数本身，所以其实这个数组的最后一个是无效路径，是文件本身的绝对路径，倒数第二个是路径直接的上级文件夹，其他文件夹用间接处理，这个文件夹的删除要用特殊处理
    	    result=this.addOrDeleteFileStraightToFloder(fileAbsName,allAboveFloderAbs[allAboveFloderAbs.length-2],false);//直接调用类里面写的直接文件夹下文件的删减函数addOrDeleteFileStraightToFloder(String fileName,String flodername,Boolean whetherAdd) {//参数是要添加或删去的文件的绝对路径名和添加到的文件夹的绝对路径名
    	    int fileNum=0;//寻找文件在FileDataGroup中的下标
    	    for(int i=0;i<this.FileDataGroup.size();i++) {
    	    	if(this.FileDataGroup.get(i).getFileName().equals(fileAbsName)&&(this.FileDataGroup.get(i).getFileKind()!=this.FileDataGroup.get(i).FLODER)) {
    	    		fileNum=i;
    	    		result=true;//文件存在且文件不是文件夹类型的后续才有意义
    	    	}
    	    } 
    	    if(result) {
    	    for(int i=0;i<this.FileDataGroup.get(fileNum).getUsingDiskNum().size();i++) {
    	    	
    	    	this.FATformat.setDiskState(this.FileDataGroup.get(fileNum).getUsingDiskNum().get(i),0);//恢复没使用状态
    	   
    	    }//修改完全部FAT状态释放完毕
    	    this.FileDataGroup.remove(fileNum);//删除这个文件的FileData
    	    //由于上面那个方法没有在FileData中删除或创建文件，所以还需要删除一下这个文件在数组里面的信息以及这个文件自身占用的FAT
    	    }
    	    return result;
       }
       //删除文件夹类型文件 ，因为是根据记录的下面的文件一个个都要删除，但是因为删除时候可能会先删了上级的文件夹，再删除下面的文件时候要删除沿途上面的文件夹对于这个文件的信息，这时候可能会出现找不到上级文件夹的情况，就不管继续往上找就行了反正全删了就好了
       public Boolean deleteFloderFile(String fileAbsName) {//参数是这个文件的绝对路径名称
    	   Boolean result=false;
    	   String [] aboveFloderAbsRoadGroup=this.splitRoadToGetAllAboveFloder(fileAbsName);
    	   //获取了包括自身绝对路径的全部绝对路径字符串。所以这个变量的最后一个字符串没用
    	   //
    	   //好像多余了，有写间接删除的函数只需要提供文件名就好了，不用对应下标
//    	   int [] aboveFloderNumInFileDataGroup=new int [aboveFloderAbsRoadGroup.length-1];
//    	   //记录所有这些上层文件在filedata数组里面的下标
//    	   
//    	   for(int i=0;i<aboveFloderAbsRoadGroup.length-1;i++) {
//    		   for(int j=0;j<this.FileDataGroup.size();j++) {
//    			   if(aboveFloderAbsRoadGroup[i].equals(this.FileDataGroup.get(j).getFileName())) {
//    				   aboveFloderNumInFileDataGroup[i]=j;
//    				   break;
//    			   }
//    		   }
//    	   }
    	   int thisFileNumInFileDataGroup=0;//要删除的文件夹本身在FileDataGroup数组里面的下标
    	   for(int j=0;j<this.FileDataGroup.size();j++) {
			   if(fileAbsName.equals(this.FileDataGroup.get(j).getFileName())&&(this.FileDataGroup.get(j).getFileKind()==this.FileDataGroup.get(j).FLODER)) {
				   thisFileNumInFileDataGroup=j;
				   result=true;//判断要删除的文件是否存在，且文件必须要是文件夹类型；要是文件夹本身不存在就没必要后续操作了
				   break;
			   }
		   }
    	   if(result) {//只有文件存在且是文件夹才执行后面的操作
    		   //先删除非直接联系的文件，也就要删除的文件夹下面的文件在其上面文件的数据
    		   for(int i=0;i<this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().size();i++) {//外循环是要删除的文件夹下面的文件数量
    			   //内循环就是对应要删除文件夹下文件记录的那些上级文件夹
    			   for(int j=0;j<aboveFloderAbsRoadGroup.length-1;j++) {
    				   this.addOrDeleteFileUnstraightToFloder(this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().get(i),aboveFloderAbsRoadGroup[j],false);
    			       //删除要删除的文件夹下面的文件在文件夹上方文件的FileNameUnderFloder中的记录，应为是间接所以调用的是间接方法不涉及FAT的变化
    			   }
    		   }
    		   //最后删除文件夹对应上一级的直接信息删除，因为有root一定在删除的一定是root下级文件夹所以减2不会出错
    		   this.addOrDeleteFileStraightToFloder(fileAbsName,aboveFloderAbsRoadGroup[aboveFloderAbsRoadGroup.length-2],false);
    	       //接下来释放文件夹以及文件夹下面所有文件的FAT占用的内容
    		   //先收集文件夹下面的文件在FileData中的下标
    		   int [] underFloderNumInFileDataGroup=new int [this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().size()];
               for(int i=0;i<this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().size();i++) {
            	   for(int j=0;j<this.FileDataGroup.size();j++) {
            		   if(this.FileDataGroup.get(j).getFileName().equals(this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().get(i))) {
            			   underFloderNumInFileDataGroup[i]=j;
            			   break;
            		   }
            	   }
    		   }
    		   //先修改文件夹下面文件的FAT盘块状态
    		   for(int i=0;i<underFloderNumInFileDataGroup.length;i++) {
    			   for(int j=0;j<this.FileDataGroup.get(underFloderNumInFileDataGroup[i]).getUsingDiskNum().size();j++) {
    				   this.FATformat.getDiskState()[this.FileDataGroup.get(underFloderNumInFileDataGroup[i]).getUsingDiskNum().get(j)]=0;
    			   }
    		   }
    		   //再修改本文件夹的FAT盘块状态
    		   for(int j=0;j<this.FileDataGroup.get(thisFileNumInFileDataGroup).getUsingDiskNum().size();j++) {
				   this.FATformat.getDiskState()[this.FileDataGroup.get(thisFileNumInFileDataGroup).getUsingDiskNum().get(j)]=0;
			   }
    		   //修改FAT的信息完毕后，删除文件夹以及文件夹下面的文件在FileDataGroup中的元素
    		   //System.out.println("daxiao:"+underFloderNumInFileDataGroup.length);
//    		   for(int i=0;i<underFloderNumInFileDataGroup.length;i++) {//删除文件夹下面的文件本身
//    			   this.FileDataGroup.remove(underFloderNumInFileDataGroup[i]);
//    		   }
    		   for(int i=0;i<this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().size();i++) {//删除文件夹下面的文件本身
    			   for(int j=0;j<this.FileDataGroup.size();j++) {
            		   if(this.FileDataGroup.get(j).getFileName().equals(this.FileDataGroup.get(thisFileNumInFileDataGroup).getFileNameUnderFloder().get(i))) {
            			   this.FileDataGroup.remove(j);
            			   break;
            		   }
            	   }
    		   }
    		   this.FileDataGroup.remove(thisFileNumInFileDataGroup);//删除文件夹本身
    	   
    	   }
    	
    	   return result;
       }   
       //复制非文件夹类型的文件，参数是要复制的文件绝对路径名以及要复制到的地方的绝对路径
       public Boolean copyTxtOrExeFile(String copyFileAbsName,String copyToWhereAbsRoad) {
    	   Boolean result=false;
    	   int beCopyedFileNumInFileDataGroup=0;
    	   for(int i=0;i<this.FileDataGroup.size();i++) {//首先先搜索这个要复制的文件，获得文件对应再FileDataGroup里面的下标，检索不到直接结束
    	       if(this.FileDataGroup.get(i).getFileName().equals(copyFileAbsName)) {
    	    	   result=true;
    	    	   beCopyedFileNumInFileDataGroup=i;
    	    	   break;
    	       }
    	   }
    	   int avaliableDiskNum=0;//还没被使用的磁盘块数
    	   for(int i=0;i<256;i++){
    		   if(this.FATformat.getDiskState()[i]==0) {
    			   avaliableDiskNum++;
    		   }
    	   }//得到还没使用的磁盘数量
    	   
    	   String [] splitResult=copyFileAbsName.split("\\\\");//分割文件名字重组
    	   String newCopyFileAbsName=copyToWhereAbsRoad+"\\"+splitResult[splitResult.length-1];//获得重组名字
    	   for(int i=0;i<this.FileDataGroup.size();i++) {//判断有没有已经存在一模一样绝对路径名称的文件。如果有result变成false后面就不需要进行了
    		   if(this.FileDataGroup.get(i).getFileName().equals(newCopyFileAbsName)) {
    	    	   result=false;
    	    	   break;
    	       }
    	   }
    
    	   if(result&&this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getFileKind()==this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).TXT) {
    		   //如果是TXT文件，且文件存在时候
    		   result=false;//如果进入If就会变回true否则返回时候依旧是执行失败的false
    		   if(this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getUsingDiskNum().size()<=avaliableDiskNum) {//如果磁盘够复制才会执行复制
    			   result=true;
    			   this.createTXTFile(copyToWhereAbsRoad,splitResult[splitResult.length-1]);
    			   this.changeTxtFileContext(this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getContext(),newCopyFileAbsName);
    		   }
    	   }
    	   if(result&&this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getFileKind()==this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).EXECUTABL) {
    		 //如果是EXE文件，且文件存在时候
    		   //this.createEXEFile(copyToWhereAbsRoad,splitResult[splitResult.length-1]);
    		   result=false;//如果进入If就会变回true否则返回时候依旧是执行失败的false
    		   if(this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getUsingDiskNum().size()<=avaliableDiskNum) {//如果磁盘够复制才会执行复制
    			   result=true;
    			   this.createEXEFile(copyToWhereAbsRoad,splitResult[splitResult.length-1]);
    			   this.changeExeFileContext(this.FileDataGroup.get(beCopyedFileNumInFileDataGroup).getExeContext(),newCopyFileAbsName);
    		   }
    	   }
    	   return result;
    	   //判断是文本文件还是可执行文件
    	   //获取文件需要占据多少磁块
    	   //遍历FAT看存储空间是否够，不够直接结束
    	   //然后直接用文件非绝对名和创建路径调用创建函数创建一个文件
    	   //如果是文本文件就再复制原文本文件Context，调用修改文本文件的文本的函数修改新建立的文件
    	   /*判断复制到的地方会不会重名
    	    * 文件需要复制的东西包括，FileData
    	    * 1.文件名，修改路径后的
    	    * 2.txt的话要复制Context内容
    	    * 3.FileKind
    	    * 再根据文件大小分配FAT
    	    * usingDiskNum
    	    */
       }
         
         //分割文件名称，获得一层层路径上的文件夹，在创建文件时候会要求给上面每个文件夹加入这个新文件的名字，方便删除时候文件夹下面所有文件删除
         public static String[] splitRoadToGetAllAboveFloder(String road) {//输入这个road是不包含文件名那一层的road
        	 String [] splitResult=road.split("\\\\");//因为\特殊，所以四个才代表用斜杠分割，在重组时候分割\双斜杠才行
        	 int aboveFloderNum=splitResult.length;
        	 String AllAboveFloder[]=new String[aboveFloderNum];
        	 for(int i=1;i<=aboveFloderNum;i++) {
        		 String result=splitResult[0];
        		 for(int j=1;j<i;j++) {
        			 result=result+"\\"+splitResult[j];
        		 }
        		 AllAboveFloder[i-1]=result;
        	 }
        	 return AllAboveFloder;//从根目录往后顺序得到的文件夹绝对路径字符串数组
         }
         //修改名字时候需要拆开该最后的名字然后再合起来变成绝对路径名返回
         public static String changeName(String oldNameIncludeRoad,String name) {//输入的是原本的绝对路径名称和新的名字
        	 String [] splitResult=oldNameIncludeRoad.split("\\\\");//因为\特殊，所以四个才代表用斜杠分割，在重组时候分割\双斜杠才行
        	 int Num=splitResult.length;
        	 String newNameIncludeRoad=splitResult[0];
        	 for(int i=1;i<Num-1;i++) {
        		 newNameIncludeRoad=newNameIncludeRoad+"\\"+splitResult[i];
        	 }
        	 newNameIncludeRoad=newNameIncludeRoad+"\\"+name;
        	 return newNameIncludeRoad;
         }
         
         
         
}
