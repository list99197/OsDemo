package progress.management;
public class SystemClock extends Thread { //系统时钟线程
    private static int systemTime = 0;

    @Override
    public void run(){
        while (true) {
            try {
                Thread.sleep(999);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("SystemClock:" + getSystemTime());
            systemTime++;
        }
    }

    public static int getSystemTime() {
        return systemTime;
    }
}
