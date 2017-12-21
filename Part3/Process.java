
public class Process {

	private String PID;
	private double arrival;
	private double execution;
	private int burst;
	private double complete;
	private double turnaround;
	private double start;
	private double waiting;
	private double remaining;
	private double interrupt;
	private int io_time;
	private String status = "";
	private int cpu_run;
	private double quantum;
	
	public Process(String PID, double arrival, double execution, int burst) {
		this.PID = PID;
		this.arrival = arrival;
		this.execution = execution;
		this.burst = burst;
		this.remaining = execution;
		io_time = 60;
		this.cpu_run = burst;
		waiting = 0;
		quantum = 1;
	}
	
	public String getPID() {
		return this.PID;
	}
	
	public void setPID(String PID) {
		this.PID = PID;
	}
	
	public double getArrival() {
		return this.arrival;
	}
	
	public void setArrival(double arrival) {
		this.arrival = arrival;
	}
	
	public double getExecution() {
		return this.execution;
	}
	
	public void setExecution(double execution) {
		this.execution = execution;
	}
	
	public int getBurst() {
		return this.burst;
	}
	
	public void setBurst(int burst) {
		this.burst = burst;
	}
	
	public double getComplete() {
		return this.complete;
	}
	
	public void setComplete(double complete) {
		this.complete = complete;
	}
	
	public double getTurnaround() {
		return this.turnaround;
	}
	
	public void setTurnaround(double turnaround) {
		this.turnaround = turnaround;
	}
	
	public double getStart() {
		return this.start;
	}
	
	public void setStart(double start) {
		this.start = start;
	}
	
	public double getWaiting() {
		return this.waiting;
	}
	
	public void updateWaiting() {
		this.waiting = waiting + 1;
	}
	
	public void setWaiting(double waiting) {
		this.waiting = waiting;
	}
	
	public double getRemaining() {
		return this.remaining;
	}
	
	public void setRemaining(double remaining) {
		this.remaining = remaining;
	}
	
	public void updateRemaining() {
		this.remaining = remaining - 1;
	}
	
	public double getInterrupt() {
		return this.interrupt;
	}
	
	public void setInterrupt(double interrupt) {
		this.interrupt = interrupt;
	}
	
	public int get_io_time() {
		return this.io_time;
	}
	
	public void update_io_time() {
		this.io_time = io_time - 1;
	}
	
	public void set_io_time() {
		this.io_time = 60;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public double get_cpu_run() {
		return this.cpu_run;
	}
	
	public void set_cpu_run(int run) {
		this.cpu_run = run;
	}
	
	public void update_cpu_run() {
		this.cpu_run = cpu_run - 1;
	}
	
	public double getQuantum() {
		return this.quantum;
	}
	
	public void setQuantum(double quantum) {
		this.quantum = quantum;
	}
}


