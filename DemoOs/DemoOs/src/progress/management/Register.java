package progress.management;
import java.util.LinkedList;
import java.util.List;

import com.sun.glass.ui.TouchInputSupport;

public class Register {
    //public List<PCB> pcbArea;
    //public List<PCB> blockArea;
    private  int AX; //数据寄存器
    private int PSW;
    private  int PC; //程序计数器
    private List<String> IR; //指令寄存器

    public Register(){
        //pcbArea = new LinkedList<>();
        //blockArea = new LinkedList<>();
        PSW = 000;
        PC = 0;
        AX = 0;
        IR = new LinkedList<>();
    }
    public Register(List<String> IR){
        //pcbArea = new LinkedList<>();
        //blockArea = new LinkedList<>();
        PSW = 000;
        PC = 0;
        AX = 0;
        this.IR = IR;
    }

    public int getAX() {
        return AX;
    }

    public  void setAX(int AXc) {
       AX = AXc;
    }

    public int getPSW() {
        return PSW;
    }

    public void setPSW(int PSW) {
        this.PSW = PSW;
    }

    public  int getPC() {
        return PC;
    }

    public  void setPC(int PCc) {
       PC = PCc;
    }

    public List<String> getIR() {
        return IR;
    }

    public void setIR(List<String> IR) {
        this.IR = IR;
    }
}
