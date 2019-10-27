package progress.management;

import java.util.Random;

import Disk.FileOperation;

public class AnalyzeTheFile {
	private static Random random = new Random();
	private static int ranNum;
private static int size;
	public static int getSize() {
	return size;
}
public static void setSize(int size) {
	AnalyzeTheFile.size = size;
}
	public static String getInstruction(FileOperation fileOperation) {
		ranNum = random.nextInt(10) + 6;

		System.out.println(fileOperation.getFileDataGroup().get(ranNum).getExeContext() + "我是指令");
size=fileOperation.getFileDataGroup().get(ranNum).getFileByteSize();
		return fileOperation.getFileDataGroup().get(ranNum).getExeContext();
	}
}
