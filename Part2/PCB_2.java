import java.util.LinkedList;
import java.util.Queue;

public class PCB_2 {

	private Queue<Process_2> queue;
	private String state;
	
	public PCB_2() {
		state = "";
		queue = new LinkedList <Process_2>();
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void add(Process_2 p) {
		queue.add(p);
	}
	
	public void remove(Process_2 p) {
		queue.remove(p);
	}
}
