package Disk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class main {
      public static void main(String [] args) throws ClassNotFoundException, IOException {
    	  FileOperation fileoperation=new FileOperation();
    	  
    	  fileoperation.SavingDiskAndFATFile();
    	 //测试文件的创建和保存问
    	  //fileoperation.createFLODERFile("ROOT", "c");
    	  //fileoperation.createTXTFile("ROOT\\c", "a");
    	  //fileoperation.createEXEFileAndAutoCreateExeContext("ROOT\\c","E");
    	  //fileoperation.createEXEFile("ROOT\\c","E");
    	  //fileoperation.changeTxtFileContext("11111111111111111111111111111111111111111111111111111111111111111","ROOT\\c\\a");
    	  //fileoperation.copyTxtOrExeFile("ROOT\\c\\E","ROOT");
    	  //fileoperation.changeExeFileContext("asasasasasasa","ROOT\\c\\E");
    	  //fileoperation.deleteFloderFile("ROOT\\c\\a");
    	  //fileoperation.createTXTFile("ROOT\\c\\a","k");
    	  //fileoperation.changeFileOrFloderName("b", "ROOT\\E");
    	  
    	  //按照目录树的结构输出数组
    	  System.out.println(fileoperation.getFileDataGroup().get(2).getFileName());
    	  printTree(fileoperation.getFileDataGroup().get(2),fileoperation.getFileDataGroup());
    	  
    	  
    	  outputFileDataGroup(fileoperation);
    	  fileoperation.SavingDiskAndFATFile();
    	  showFAT(fileoperation.getFATformat());
    	  //showFAT(fileoperation.getFATformat());

      }
      
      public static void outputFileDataGroup(FileOperation fileoperation) {
    	  System.out.println("文件数组里面的文件");
    	  for(int i=0;i<fileoperation.getFileDataGroup().size();i++) {
    		  System.out.println("文件 ");
    		  System.out.println(fileoperation.getFileDataGroup().get(i).getFileName());
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getFileKind());
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getFileByteSize()+"  "+"x=?;x++;x--;!A5;end;".getBytes().length);
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getUsingDiskNum());
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getFileNameUnderFloder());
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getFileNameStraightUnderFloder());
        	  System.out.println(fileoperation.getFileDataGroup().get(i).getExeContext());
    	  }
      }
      public static void showFAT(FAT fat) {
    	  System.out.println("FAT表中被分配的块号");
    	  for(int i=0;i<256;i++) {
    		  if(fat.getDiskState()[i]!=0) {
    			  System.out.println("块号"+i+" 内容"+fat.getDiskState()[i]);
    		  }
    	  }
      }
  
      public static void printTree(FileData node,ArrayList<FileData> FileDataGroup) {//第一个参数是树根，在这里就是ROOT对应的那个文件，也就是fileoperation.getFileDataGroup().get(2)，一定是在这个下标，前三个文件是固定死的。第二个参数就是fileData的list数组
    	  for(int i=0;i<node.getFileNameStraightUnderFloder().size();i++) {//循环当前文件的子文件有哪些
    		  System.out.println("hhhhh");
    		  System.out.println(node.getFileNameStraightUnderFloder().get(i));//显示子文件名称
    		  int fileIdex=0;
    		  for(int j=0;j<FileDataGroup.size();j++) {//这个循环是对应在子文件的绝对路径名称字符串在整个fileData的list里面的下标是多少
    			  if(FileDataGroup.get(j).getFileName().equals(node.getFileNameStraightUnderFloder().get(i))) {
    				  fileIdex=j;
    				  break;             
    			  }
    		  }
    		  if(FileDataGroup.get(fileIdex).getFileNameStraightUnderFloder().size()!=0) {//如果这个子文件下面还有叶子就继续递归
    			  printTree(FileDataGroup.get(fileIdex),FileDataGroup);//递归，如果这个文件下面还有就继续显示下级的文件，显示完就会回退显示上一级剩下的，以此类推按照树的结构显示全部
    		  }
    	  }
      }
    
}
