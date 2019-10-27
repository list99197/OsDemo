package progress.management;
public class PCB {
    private int pid=0;
    private static int allPid=1;
    private int ProcessState;
    private int timeOfBlocked; //阻塞时的系统时间
    private static int count = 0;
    public Register RegisterState ;
   private int applyTime=0;
   private int size;
    public int getSize() {
	return size;
}

public void setSize(int size) {
	this.size = size;
}

	public int getApplyTime() {
	return applyTime;
}

public void setApplyTime(int applyTime) {
	this.applyTime = applyTime;
}

	public PCB(){
    	RegisterState=new Register();
    	this.pid=allPid;
    	allPid++;
        
        timeOfBlocked = 0;
        ProcessState = -1; //0 运行态 1 就绪态 2 阻塞态
        count++;
    }

    public PCB(Register registerState,int size){
    	this.pid=allPid;
    	allPid++;
    	this.size=size;
    	 
         RegisterState = registerState;
         timeOfBlocked = 0;
         ProcessState = -1;
         count++;
         
         }
   public int getPid(){
        return this.pid;
    }
   public int getProcessState(){
        return this.ProcessState;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public void setProcessState(int process_state) {
        this.ProcessState = process_state;
    }

    public Register getRegisterState() {
        return RegisterState;
    }

    public void setRegisterState(Register registerState) {
        RegisterState = registerState;
    }
    public int getTimeOfBlocked() {
        return timeOfBlocked;
    }

    public void setTimeOfBlocked(int timeOfBlocked) {
        this.timeOfBlocked = timeOfBlocked;
    }

    public static int getCount() {
        return count;
    }

    public static void decCount() {
        count--;
    }
    public void block() {
    }
    public void aWake() {
    }
    @Override
    public String toString() {
        return "" + pid;
    }
}
