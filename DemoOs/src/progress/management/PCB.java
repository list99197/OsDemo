package progress.management;
public class PCB {
    private int pid;
    private int process_state;
    public Register RegisterState;
    public PCB(){
        int pid;
        //
        //

    }
    int getPid(){
        return this.pid;
    }
    int getProcess_state(){
        return this.process_state;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setProcess_state(int process_state) {
        this.process_state = process_state;
    }

    public Register getRegisterState() {
        return RegisterState;
    }

    public void setRegisterState(Register registerState) {
        RegisterState = registerState;
    }
}
