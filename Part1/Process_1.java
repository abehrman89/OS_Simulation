
public class Process_1 {

	private String PID;
	private double arrival;
	private double burst;
	private double complete;
	private double turnaround;
	
	public Process_1(String PID, double arrival, double burst) {
		this.PID = PID;
		this.arrival = arrival;
		this.burst = burst;
		complete = 0.0;
		turnaround = 0;
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
}
