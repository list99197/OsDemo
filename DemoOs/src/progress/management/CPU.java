package progress.management;
public class CPU {
    int ProcessCounter = 0;
    public CPU(){
        this.ProcessCounter = 0;
    }
    public void setProcessCounter(int num){
        this.ProcessCounter = num;
    }
    public void increaseProcessCounter(){
        this.ProcessCounter += 1;
    }
    public void decreaseProcessCounter(){
        this.ProcessCounter -= 1;
    }
    /*public void runCPU(){
        checkInterrupt();
    }*/
    public static void checkInterrupt(Register state){
        /*
        ① 程序结束（执行指令 end 形成的软中断）：在屏幕上
        输出 x 的值，调用“进程撤销原语”撤销进程，然后进行进程
        调度；
        ② 时间片结束（当相对时钟减到0时 ）：将正在运行进程的
        CPU 现场（寄存器值即可，暂不考虑“栈”）保存在进程
        控制块中，然后进行进程调度；
        ③ I/O 中 断 发 生（设备使用时间变量倒计时至0,完成输
        入输出）：将输入输出完成的进程唤醒，同时将等待该设备的
        另一个进程唤醒。
         */
        int psw = state.getPSW();
        if(psw == 001) //程序结束
        {

        } else if (psw == 010) //时间片结束
        {

        }
        else if (psw == 100) //IO中断发生
        {

        }
        else
        {

        }
    }

}
