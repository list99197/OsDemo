package Disk;

import java.io.Serializable;

/*
 * FAT表，也就是盘块的第一块内容，由于第一块固定放FAT所以直接单独拿出来作为一个类存储
 * 总共256块盘，在0，1号块内存储，显示时候直接默认0，1已经分配，然后用这个FAT记录变化即可
 */
public class FAT implements Serializable{
     private int Disknum[];//256块盘块号
     private int DiskState[];//256块盘的状态记录，也就是如果没有分配就是0.分配则显示连接的下一个盘的号码类似链表，如果无就记录-1
     //为求方便检索，添加256大小的数组两个对应记录存在的文件的开头盘块号以及文件名，文件名包括地址
     /*
      * 
      */
     private int DiskheadGroup[];//记录所有文件存储的第一个盘块号
     private String FileName[];//记录对应的文件名，包含路径的名称
     /*
      * 好像赘余了在后面没有使用
      */
     
     public FAT() {//初始化第一次时候，之后应该就是读取保存这个类的对象即可
    	 this.Disknum=new int [256];
    	 this.DiskState=new int [256];
    	 for(int i=0;i<256;i++) {
    		 this.Disknum[i]=i;//给盘块输入号
    		 this.DiskState[i]=0;//所有盘块在未调配状态
    	 }
    	 this.DiskState[0]=-1;
    	 this.DiskState[1]=-1; //盘块0，1FAT必然在使用状态
    	 this.DiskState[2]=-1; //盘块2是给根目录用的
    	 
    	 
    	 this.DiskheadGroup=new int [256];//-1表示没有分配
    	 this.FileName=new String [256];
    	 for(int i=0;i<256;i++) {
    		 this.DiskheadGroup[i]=-1;//给盘块输入号
    		 this.FileName[i]="";//所有盘块在未调配状态
    	 }
    	 this.DiskheadGroup[0]=0;
    	 this.DiskheadGroup[1]=1;
    	 this.DiskheadGroup[2]=2;
    	 this.FileName[0]="FAT1";
    	 this.FileName[1]="FAT2";
    	 this.FileName[3]="ROOT";//根目录文件夹名称
    			 
     }
     
     //判断是否申请盘块的是否空闲
     public Boolean judgeIfFATAllocation(int requestNum) {//参数是请求占用的板块号
    	 Boolean result=true;//如果申请占用的盘块已经占用就返回false
    	 if(this.DiskState[requestNum]!=0)
    		 result=false;
    	 return result;
     }
     
     //分配磁块

     
	public int[] getDisknum() {
		return Disknum;
	}
//	public void setDisknum(int[] disknum) {
//		Disknum = disknum;
//	}不允许修改盘块号
	public int[] getDiskState() {
		return DiskState;
	}
	public void setDiskState(int num,int state) {//第一个参数是改变的diskState的下标，第二个是改变后的值
		this.DiskState[num]=state;
	}
     
}
