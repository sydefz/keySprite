package test;
import java.awt.MouseInfo;
import java.util.Random;

public class RunnableProcess implements Runnable {
	private String name;
	private int time;
	private Random rand = new Random();

	public RunnableProcess(String name) {
		this.name = name;
		this.time = rand.nextInt(999);
	}

	public void run() {
		try {
			while (true) {
				Thread.sleep(10-0);
				System.out.println("("
						+ MouseInfo.getPointerInfo().getLocation().x + ", "
						+ MouseInfo.getPointerInfo().getLocation().y + ")");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}