
public class CPU {

	private String state;
	private double utilization;
	
	public CPU(String state) {
		this.state = state;
		this.utilization = 0.0;
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public double getUtilization() {
		return this.utilization;
	}
	
	public void setUtilization(double use, double complete) {
		utilization = (use / complete) * 100.0;
		utilization = Math.round(utilization * 100.0) / 100.0;
	}
}
