package progress.management;
import java.util.List;

import RAM.obj.Memory;
import controller.BlockProcessController;
import controller.ReadyProcessController;

public class ProcessController {
	private static ReadyProcessController readyProcessController;
	private static BlockProcessController blockProcessController;
	
	public ProcessController(ReadyProcessController readyProcessController,BlockProcessController blockProcessController) {
		this.readyProcessController=readyProcessController;
		this.blockProcessController=blockProcessController;
	}
    public static List<PCB> create(List<PCB> pcbArea, PCB newPCB){
     //maxNum代替内存
        if(PCB.getCount() <= 10){
            //申请主存空间，申请成功，装入主存
        	
            pcbArea.add(newPCB); //装入就绪队列里
            
            readyProcessController.updataReadyInfo(pcbArea);
            System.out.println("创建进程成功");
        }
        else {
            //申请失败
            //拒绝请求
            System.out.println("申请空间失败");
        }
      
        return pcbArea;
        
    }

    public static void destroy(int pid){
        //回收进程所占内存
        //pcbArea.remove(pcbArea.get(pid)); //回收进程控制块

        System.out.println("进程：" + ProcessManager.getCurrentProcess().getPid() + "已销毁");
        Memory.freeMemory(ProcessManager.getCurrentProcess());
        //pcbArea.remove(0);
        PCB.decCount();

    }

    public static void block(List<PCB> pcbArea, List<PCB> blockArea){
    	ProcessManager.getCurrentProcess().setProcessState(2); //修改进程状态为阻塞态
        //PCB pcb = ProcessManager.getCurrentProcess(); //复制开头
        ProcessManager.getCurrentProcess().setTimeOfBlocked(SystemClock.getSystemTime());
        blockArea.add(ProcessManager.getCurrentProcess());
//blockProcessController.updataBlockInfo(blockArea);
        //System.out.println(blockArea.get(0).getPid());
    }

    public static void awake(List<PCB> pcbArea, List<PCB> blockArea){
    	
        blockArea.get(0).setProcessState(1); //修改进程状态为就绪态
        //PCB pcb = blockArea.get(0); //复制开头
        pcbArea.add(blockArea.get(0));
        
        readyProcessController.updataReadyInfo(pcbArea);
        blockArea.remove(blockArea.get(0));
        blockProcessController.updataBlockInfo(blockArea);
    }
}
