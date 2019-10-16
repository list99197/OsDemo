package progress.management;
import java.util.LinkedList;
import java.util.List;

public class Register {
    public  List<PCB> pcbArea;
    public int AX; //数据寄存器
    public int PSW;
    public int PC; //程序计数器
    public int IR; //指令寄存器
    public Register(){
        pcbArea = new LinkedList<>();
        PSW = 000;
        PC = -1;
        IR = -1;
        AX = 0;
    }

    public List<PCB> getPcbArea() {
        return pcbArea;
    }

    public void setPcbArea(List<PCB> pcbArea) {
        this.pcbArea = pcbArea;
    }

    public int getPSW() {
        return PSW;
    }

    public void setPSW(int PSW) {
        this.PSW = PSW;
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public int getIR() {
        return IR;
    }

    public void setIR(int IR) {
        this.IR = IR;
    }

    public int getAX() {
        return AX;
    }

    public void setAX(int AX) {
        this.AX = AX;
    }
}
