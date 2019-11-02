package progress.management;
import java.util.LinkedList;
import java.util.List;

import Disk.FileOperation;
import controller.BlockProcessController;
import controller.DeviceController;
import controller.ReadyProcessController;
import controller.RunningProcessController;

public class CPU extends Thread{
   // int ProcessCounter = 0;
    public static List<PCB> pcbArea = new LinkedList<>(); //就绪对列
    public static List<PCB> blockArea = new LinkedList<>();//阻塞队列
    public static Register registers = new Register();
    public static ProcessController processController;
    public static ProcessManager processManager;
    private static PCB currentProcess;
    private boolean isCPUAvailable = true;
private static RunningProcessController runningProcessController;
private static ReadyProcessController readyProcessController;
    public CPU(RunningProcessController runningProcessController,ReadyProcessController readyProcessController,FileOperation fileOperation,DeviceController deviceController,BlockProcessController blockProcessController){
        this.runningProcessController=runningProcessController;
     this.readyProcessController=readyProcessController;
        processController = new ProcessController(readyProcessController,blockProcessController);
        processManager = new ProcessManager(runningProcessController,processController,fileOperation,deviceController,readyProcessController);
    }


    @Override
    public void run(){  
       
        while (true) {
        	  if (!pcbArea.isEmpty() && isCPUAvailable) { //检测就绪队列是否为空
                  isCPUAvailable = false;
                  ProcessManager.setCurrentProcess(pcbArea.get(0));

                  System.out.println("进程调度，PID=" + ProcessManager.getCurrentProcess().getPid());

                  ProcessManager.AlgorithmRR(pcbArea); //非空，调用轮转算法
                   
                  isCPUAvailable = true;
              } else {
                  //System.out.println("调度闲逛进程");
                  try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
                  }
              }
           checkInterrupt();  
           
        }
    }
    public static void checkInterrupt() {
        /*
        ① 程序结束（执行指令 end 形成的软中断）：在屏幕上
        输出x的值，调用“进程撤销原语”撤销进程，然后进行进程
        调度；
        ② 时间片结束（当相对时钟减到0时）：将正在运行进程的
        CPU 现场（寄存器值即可，暂不考虑“栈”）保存在进程
        控制块中，然后进行进程调度；
        ③ I/O中断发生（设备使用时间变量倒计时至0,完成输
        入输出）：将输入输出完成的进程唤醒，同时将等待该设备的
        另一个进程唤醒。
        */
        //System.out.println("psw in int:" + pcbArea.get(0).getRegisterState().getPSW());

        //psw = pcbArea.get(0).getRegisterState().getPSW();

    	if (ProcessManager.getCurrentProcess() != null) {
            if(ProcessManager.getCurrentProcess().getRegisterState().getPSW() == 001) //程序结束
            {runningProcessController.clearRunningP();
                ProcessController.destroy(0); //进程撤销
                System.out.println("进程撤销");
            } else if (ProcessManager.getCurrentProcess().getRegisterState().getPSW() == 010) { //时间片结束
                ProcessManager.getCurrentProcess().setProcessState(1); //设置为就绪态
                //PCB pcb = ProcessManager.getCurrentProcess();//复制开头
                //pcbArea.remove(pcbArea.get(0)); //移掉开头
                //pcb.setRegisterState(registers); //保存现场到进程控制块里
                runningProcessController.clearRunningP();
                pcbArea.add(ProcessManager.getCurrentProcess()); //加到末尾
                
                readyProcessController.updataReadyInfo(pcbArea);
                System.out.println("时间片结束");
            }
            else if (ProcessManager.getCurrentProcess().getRegisterState().getPSW() == 100) //IO中断发生
            {
            	runningProcessController.clearRunningP();
                //ProcessManager.getCurrentProcess().setRegisterState(registers);  //保存现场
//                ProcessController.block(pcbArea, blockArea); //阻塞该进程
//                BlockProcessController.updataBlockInfo(blockArea);
                System.out.println("IO中断发生");
            }
        }
        ProcessManager.setCurrentProcess(null); //清空当前运行进程记录
       
    }

    public static List<PCB> getPcbArea() {
        return pcbArea;
    }

    public static List<PCB> getBlockArea() {
        return blockArea;
    }

    public static PCB getCurrentProcess() {
        return currentProcess;
    }
}
