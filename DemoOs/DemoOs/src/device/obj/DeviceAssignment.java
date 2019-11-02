package device.obj;



import java.util.Vector;

import controller.AllPaneController;
import progress.management.CPU;
import progress.management.PCB;
import progress.management.ProcessController;

public class DeviceAssignment extends Thread {
    private PCB pcb;
    private int time;
    private DeviceAssignment[] list;
    private int type;

    DeviceAssignment(PCB pcb, int useTime, DeviceAssignment[] list, int type) {
        this.pcb = pcb;
        this.time = useTime;
        this.list = list;
        this.type = type;
    }

    @Override
    public void run() {
    	time++;
        pcb.block();
        //开始io，阻塞进程
        while (time > 0) {
            time--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //io结束
        
        pcb.aWake();
        ProcessController.awake(CPU.pcbArea, CPU.blockArea);
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i].getPcb() == pcb) {
                list[i] = null;
                break;
            }
        }
        Device.checkDeviceUseStatus(type);
      
    }

    PCB getPcb() {
        return pcb;
    }
}
