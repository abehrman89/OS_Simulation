import java.util.LinkedList;
import java.util.Queue;

public class PCB_1 {

	private Queue<Process_1> queue;
	private String state;
	
	public PCB_1() {
		state = "";
		queue = new LinkedList <Process_1>();
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void add(Process_1 p) {
		queue.add(p);
	}
	
	public void remove(Process_1 p) {
		queue.remove(p);
	}
}
