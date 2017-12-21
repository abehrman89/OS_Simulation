
public class Process_2 {

	private String PID;
	private double arrival;
	private double burst;
	private double complete;
	private double turnaround;
	private int priority;
	private double start;
	private double waiting;
	private double remaining;
	private double interrupt;
	
	public Process_2(String PID, double arrival, double burst, int priority) {
		this.PID = PID;
		this.arrival = arrival;
		this.burst = burst;
		this.priority = priority;
		this.remaining = burst;
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
	
	public void setArrival(float arrival) {
		this.arrival = arrival;
	}
	
	public double getBurst() {
		return this.burst;
	}
	
	public void setBurst(float burst) {
		this.burst = burst;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
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
}


