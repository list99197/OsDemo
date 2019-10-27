package progress.management;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.glass.ui.TouchInputSupport;

import Disk.FileOperation;
import RAM.obj.Memory;
import controller.AllPaneController;
import controller.BlockProcessController;
import controller.DeviceController;
import controller.ReadyProcessController;
import controller.RunningProcessController;
import device.obj.Device;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;
import layout.table.data.BlockProcessData;

public class ProcessManager {
	private static PCB currentProcess;
	private static int timeSlice = 6;
	private static int timeForDevice; // 申请某一设备的时间
	private static char typeOfDevice; // 申请设备的类型
	private static String MathRegEx = "[^0-9]"; // 数字正则
	private static Pattern Math_Pattern = Pattern.compile(MathRegEx); /// 数字正则
	private static Matcher num;

	private static RunningProcessController runningProcessController;
	private static ReadyProcessController readyProcessController;
	public static ProcessController processController;
	private static FileOperation fileOperation;
	private static DeviceController deviceController;

	public ProcessManager(RunningProcessController runningProcessController, ProcessController processController,
			FileOperation fileOperation, DeviceController deviceController,
			ReadyProcessController readyProcessController) {
		this.runningProcessController = runningProcessController;
		this.processController = processController;
		this.fileOperation = fileOperation;
		this.deviceController = deviceController;
		this.readyProcessController = readyProcessController;
	}

	public static void Choose_File(List<PCB> pcbArea) {
if(PCB.getCount()<=10) {

		List<String> PCB_IR = new LinkedList<>();
		String content = AnalyzeTheFile.getInstruction(fileOperation); // EXEFile.getExeContext();
int size=AnalyzeTheFile.getSize();
		String[] commands = content.split(";"); // 根据格式分开每句指令

		for (int i = 0; i < commands.length; i++) { // 把指令存进指令寄存器
			PCB_IR.add(commands[i]);
		}

		Register PCB_Register = new Register(PCB_IR); // 初始化寄存器

		PCB newPCB = new PCB(PCB_Register,size); // 初始化PCB
       Memory.applyMemory(size, newPCB);
		ProcessController.create(pcbArea, newPCB); // 创建进程
		
		}else {
			System.out.println("申请失败");
		}
	}

	public static void AlgorithmRR(List<PCB> pcbArea/* , Register CPUState */) {
		updateDevice();
		timeSlice = 6;
		// currentProcess = pcbArea.get(0);
		// CPUState = pcbArea.get(0).RegisterState; //恢复现场
		currentProcess.setRegisterState(pcbArea.get(0).RegisterState);
		pcbArea.remove(0);
		readyProcessController.updataReadyInfo(pcbArea);
		
		int begin = currentProcess.getRegisterState().getPC();
		// 执行指令

		for (int command_i = begin; timeSlice > 0; command_i++) { // 每个时间片长度为6 执行运行进程内的6条指令
			//readyProcessController.updataReadyInfo(pcbArea);
			currentProcess.setProcessState(0); // 将进程设置为运行态

//            if(command_i + 1 > pcbArea.get(0).RegisterState.IR.size()){ //执行文件里没有end指令 warning
//                System.out.println("No End!");
//            }
			// 设置指令寄存器PC
			currentProcess.getRegisterState().setPC((currentProcess.getRegisterState().getPC() + 1));

			System.out.println("当前指令:" + command_i);
			System.out.println("PC:" + currentProcess.getRegisterState().getPC());
			if (currentProcess.getRegisterState().getIR().get(command_i).contains("x=")) { // 执行x=？
				num = Math_Pattern.matcher(currentProcess.getRegisterState().getIR().get(command_i));
				currentProcess.getRegisterState().setAX(Integer.parseInt(num.replaceAll("").trim()));
				System.out.println("AX=" + currentProcess.getRegisterState().getAX());
				runningProcessController.updataRunningInfo(currentProcess, getTimeSlice(),
						currentProcess.getRegisterState().getIR().get(command_i));
			}
			if (currentProcess.getRegisterState().getIR().get(command_i).contains("x++")) { // 执行x++
				currentProcess.getRegisterState().setAX((currentProcess.getRegisterState().getAX() + 1));

				System.out.println("AX++:" + currentProcess.getRegisterState().getAX());
				runningProcessController.updataRunningInfo(currentProcess, getTimeSlice(),
						currentProcess.getRegisterState().getIR().get(command_i));
			}
			if (currentProcess.getRegisterState().getIR().get(command_i).contains("x--")) { // 执行x--
				currentProcess.getRegisterState().setAX(currentProcess.getRegisterState().getAX() - 1);
				System.out.println("AX--:" + currentProcess.getRegisterState().getAX());
				runningProcessController.updataRunningInfo(currentProcess, getTimeSlice(),
						currentProcess.getRegisterState().getIR().get(command_i));
			}
			if (currentProcess.getRegisterState().getIR().get(command_i).contains("!")) { // 执行!?? !A5 !B2
				runningProcessController.updataRunningInfo(currentProcess, getTimeSlice(),
						currentProcess.getRegisterState().getIR().get(command_i));
				// currentProcess.getRegisterState().getIR().get(command_i));
				num = Math_Pattern.matcher(currentProcess.getRegisterState().getIR().get(command_i));
				typeOfDevice = currentProcess.getRegisterState().getIR().get(command_i).charAt(1);
				timeForDevice = Integer.parseInt(num.replaceAll("").trim());
currentProcess.setApplyTime(timeForDevice);
				
				if (typeOfDevice == 'A') {
					Device.applyDevice(Device.A_DEVICE, timeForDevice, currentProcess);
					deviceController.getPid(Device.getDeviceUseTable(), Device.getDeviceWaitQueue());
					ProcessController.block(pcbArea, CPU.blockArea); //阻塞该进程
	                BlockProcessController.updataBlockInfo(CPU.blockArea);

				}
				if (typeOfDevice == 'B') {
					Device.applyDevice(Device.B_DEVICE, timeForDevice, currentProcess);
					deviceController.getPid(Device.getDeviceUseTable(), Device.getDeviceWaitQueue());
					ProcessController.block(pcbArea, CPU.blockArea); //阻塞该进程
	                BlockProcessController.updataBlockInfo(CPU.blockArea);
					
				}
				if (typeOfDevice == 'C') {
					Device.applyDevice(Device.C_DEVICE, timeForDevice, currentProcess);
					deviceController.getPid(Device.getDeviceUseTable(), Device.getDeviceWaitQueue());
					ProcessController.block(pcbArea, CPU.blockArea); //阻塞该进程
	                BlockProcessController.updataBlockInfo(CPU.blockArea);
					
					

				}

				currentProcess.getRegisterState().setPSW(100); // I/O中断
				System.out.println("!??:!" + typeOfDevice + timeForDevice);
				break;
			}
			if (currentProcess.getRegisterState().getIR().get(command_i).equals("end")) { // 执行end
				runningProcessController.updataRunningInfo(currentProcess, getTimeSlice(),
						currentProcess.getRegisterState().getIR().get(command_i));
				currentProcess.getRegisterState().setPSW(001);
				System.out.println("end!");
				break;
			}
			if (command_i + 1 > currentProcess.getRegisterState().getIR().size()) { // 执行文件里没有end指令 warning
				System.out.println("No End!");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currentProcess.getRegisterState().setPSW(010); // 时间片结束
			timeSlice--;
			System.out.println("时间片剩余：" + getTimeSlice());
		}
		// pcbArea.get(0).setProcessState(1); //设置为就绪态
//        if(currentProcess.getRegisterState().getPSW() != 001){ //如果时间片结束（不是执行到end psw没有发出程序中断信号）就设置时间片中断信号
//            currentProcess.getRegisterState().setPSW(010);
//        }

		try { //
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	 public static void updateDevice() {
			Timeline timeline = new Timeline();
			timeline.setCycleCount(-1);
			Duration duration = Duration.millis(1.0D);
			KeyFrame keyFrame = new KeyFrame(duration, new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					AllPaneController.deviceController.getPid(Device.getDeviceUseTable(), Device.getDeviceWaitQueue());
					
				}
			}, new javafx.animation.KeyValue[0]);
			timeline.getKeyFrames().add(keyFrame);
			timeline.play();
		}
	public static int getTimeSlice() {
		return timeSlice;
	}

	public static PCB getCurrentProcess() {
		return currentProcess;
	}

	public static void setCurrentProcess(PCB currentProcess) {
		ProcessManager.currentProcess = currentProcess;
	}
}
