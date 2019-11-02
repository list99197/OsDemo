package progress.management;
import java.util.*;
public class Main {
    public static void main(String args[]) {
//        CPU cpu = new CPU();
//        SystemClock systemClock = new SystemClock();
//
//        cpu.start(); //进程调度
//        try {
//            cpu.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        systemClock.start(); //系统时钟计时

        while (true) { //选择.exe创建进程
            ProcessManager.Choose_File(CPU.pcbArea);
            //System.out.println("2");
            try {
                Thread.sleep(10000);
                //Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        while (true) { //测试系统时钟
//            System.out.println(systemClock.getSystemTime());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
