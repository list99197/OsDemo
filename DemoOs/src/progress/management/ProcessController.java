package progress.management;
import java.util.List;

public class ProcessController {
    public List<PCB> create(List<PCB> pcbArea,int maxnum){
        if(pcbArea.size() < maxnum){
            PCB newpcb = new PCB();

        }
        else {


        }
        return pcbArea;
    }
    public List<PCB> destroy(List<PCB> pcbArea,int index){
        //回收进程所占内存
        pcbArea.remove(index);
        return pcbArea;


    }
    public void block(CPU cpustate){

    }
    public void awake(){

    }
}
