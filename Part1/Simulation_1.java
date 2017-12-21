import java.util.LinkedList;
import java.util.Queue;
public class Simulation_1 {
	
	CPU_1 cpu = new CPU_1("free");
	LinkedList<Process_1> readyQueue = new LinkedList<Process_1>();
	Queue<Process_1> processes = new LinkedList<Process_1>();
	PCB_1 pcb = new PCB_1();

	public static void main (String args[]) {
		Simulation_1 sim = new Simulation_1();
		sim.run_FCFS_Sim();
		sim.run_SJF_Sim();
		sim.run_SJF_Wait_Sim();
		System.out.println();
		System.out.println("Simulation Complete");
	}
	
	/**
	 * run the simulation with the First-Come, First-Served Algorithm
	 */
	public void run_FCFS_Sim() {
	
		Boolean stop = false;
		System.out.println("FCFS ALGORITHM");
		System.out.println();
		double clock = 0.0;

		Process_1 P1 = new Process_1("P1", 0.0, 8.0);
		processes.add(P1);
		Process_1 P2 = new Process_1("P2", 0.4, 4.0);
		processes.add(P2);
		Process_1 P3 = new Process_1("P3", 1.0, 1.0);
		processes.add(P3);
		
		while (!stop) {
			
			//loop through processes and check if clock = arrival or completion time
			for (Process_1 p : processes) {
				if (clock == p.getArrival()) {
					FCFS_arrival(p, clock);
					break;
				}
				if (clock == p.getComplete()) {
					completion(p, clock);
					break;
				}
			}
			
			//increment clock and round to 1 decimal place
			clock += 0.1;
			clock = Math.round(clock * 100.0) / 100.0;
			
			if (clock == 14) {
				stop = true;
			}
		}
		
		//if sim is over, calculate individual and average turnaround times
		double avg_turnaround = 0.0;
		System.out.println();
		System.out.println();
		for (Process_1 p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			avg_turnaround += p.getTurnaround();
			System.out.println("Process " + p.getPID() + " turnaround time: " + p.getTurnaround());
		}
		avg_turnaround = Math.round((avg_turnaround / processes.size()) * 100.0) / 100.0;
		System.out.println("Average turnaround time for these processes with the FCFS scheduling "
				+ "algorithm: " + avg_turnaround);
		
		processes.clear();
	}
	
	/**
	 * run the simulation with the Shortest Job First Algorithm
	 */
	public void run_SJF_Sim() {
		
		Boolean stop = false;
		System.out.println();
		System.out.println("SJF ALGORITHM");
		System.out.println();
		double clock = 0.0;

		Process_1 P1 = new Process_1("P1", 0.0, 8.0);
		processes.add(P1);
		Process_1 P2 = new Process_1("P2", 0.4, 4.0);
		processes.add(P2);
		Process_1 P3 = new Process_1("P3", 1.0, 1.0);
		processes.add(P3);
		
		while (!stop) {
			
			//loop through processes and check if clock = arrival or completion time
			for (Process_1 p : processes) {
				if (clock == p.getArrival()) {
					SJF_arrival(p, clock);
					break;
				}
				if (clock == p.getComplete()) {
					completion(p, clock);
					break;
				}
			}
			
			//increment clock and round to 1 decimal place
			clock += 0.1;
			clock = Math.round(clock * 100.0) / 100.0;
			
			if (clock == 14) {
				stop = true;
			}
		}
		
		//if sim is over, calculate individual and average turnaround times
		double avg_turnaround = 0.0;
		System.out.println();
		System.out.println();
		for (Process_1 p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			avg_turnaround += p.getTurnaround();
			System.out.println("Process " + p.getPID() + " turnaround time: " + p.getTurnaround());
		}
		avg_turnaround = Math.round((avg_turnaround / processes.size()) * 100.0) / 100.0;
		System.out.println("Average turnaround time for these processes with the SJF scheduling "
				+ "algorithm: " + avg_turnaround);
		
		processes.clear();
	}
	
	/**
	 * run the simulation with the Shortest Job First Algorithm, 
	 * but hold the CPU idle until clock = 1.0
	 */
	public void run_SJF_Wait_Sim() {
		
		Boolean stop = false;
		System.out.println();
		System.out.println("SJF WAITING ALGORITHM");
		System.out.println();
		double clock = 0.0;

		Process_1 P1 = new Process_1("P1", 0.0, 8.0);
		processes.add(P1);
		Process_1 P2 = new Process_1("P2", 0.4, 4.0);
		processes.add(P2);
		Process_1 P3 = new Process_1("P3", 1.0, 1.0);
		processes.add(P3);
		
		while (!stop) {
			
			//loop through processes and check if clock = arrival or completion time
			for (Process_1 p : processes) {
				if (clock == p.getArrival()) {
					SJFW_arrival(p, clock);
					break;
				}
				if (clock == p.getComplete()) {
					completion(p, clock);
					break;
				}
			}
			
			//increment clock and round to 1 decimal place
			clock += 0.1;
			clock = Math.round(clock * 100.0) / 100.0;
			
			if (clock == 15) {
				stop = true;
			}
		}
		
		//if sim is over, calculate individual and average turnaround times
		double avg_turnaround = 0.0;
		System.out.println();
		System.out.println();
		for (Process_1 p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			avg_turnaround += p.getTurnaround();
			System.out.println("Process " + p.getPID() + " turnaround time: " + p.getTurnaround());
		}
		avg_turnaround = Math.round((avg_turnaround / processes.size()) * 100.0) / 100.0;
		System.out.println("Average turnaround time for these processes with the SJF Waiting scheduling "
				+ "algorithm: " + avg_turnaround);
		
		processes.clear();
	}
	
	/**
	 * process has arrived for execution
	 * if cpu is free, start process execution and calculate completion time
	 * if cpu is busy, add process to readyQueue (sorted by arrival time)
	 * @param p
	 * @param clock
	 */
	public void FCFS_arrival(Process_1 p, double clock) {
		pcb.add(p);
		if (cpu.getState() == "free") {
			cpu.setState("busy");
			pcb.setState("running");
			p.setComplete(clock + p.getBurst());
		}
		else {
			pcb.setState("ready");
			readyQueue.add(p);
		}
	}
	
	/**
	 * process has arrived for execution
	 * if cpu is free, start process execution and calculate completion time
	 * if cpu is busy, add process to readyQueue (sorted by arrival time)
	 * @param p
	 * @param clock
	 */
	public void SJF_arrival(Process_1 p, double clock) {
		pcb.add(p);
		if (cpu.getState() == "free") {
			cpu.setState("busy");
			pcb.setState("runing");
			p.setComplete(clock + p.getBurst());
		}
		else {
			pcb.setState("ready");
			for (int i = 0; i < readyQueue.size(); i++) {
				Process_1 temp = readyQueue.get(i);
				if (p.getBurst() < temp.getBurst()) {
					readyQueue.add(i, p);
					return;
				}
			}
			readyQueue.add(p);
		}
	}
	
	/**
	 * process has arrived for execution
	 * if cpu is free & clock >= 1, start process execution and calculate completion time
	 * if cpu is busy, add process to readyQueue (sorted by arrival time)
	 * @param p
	 * @param clock
	 */
	public void SJFW_arrival(Process_1 p, double clock) {
		pcb.add(p);
		if (cpu.getState() == "free" && clock > 0.9) {
			cpu.setState("busy");
			pcb.setState("runing");
			p.setComplete(clock + p.getBurst());
		}
		else {
			pcb.setState("ready");
			for (int i = 0; i < readyQueue.size(); i++) {
				Process_1 temp = readyQueue.get(i);
				if (p.getBurst() < temp.getBurst()) {
					readyQueue.add(i, p);
					return;
				}
			}
			readyQueue.add(p);
		}
	}
	
	/**
	 * process has finished executing
	 * if there is another process in readyQueue, start process execution and calculate completion time
	 * if readyQueue is empty, set cpu status to "free"
	 * @param p
	 * @param clock
	 */
	public void completion(Process_1 p, double clock) {
		pcb.remove(p);
		
		//print gantt chart
		System.out.print("|");
		for (int i = 0; i < p.getBurst() / 2; i++) {
			System.out.print(" ");
		}
		System.out.print(p.getPID());
		for (int i = 0; i < p.getBurst() / 2; i++) {
			System.out.print(" ");
		}
		System.out.print("| " + clock);
		
		if (!readyQueue.isEmpty()) {
			Process_1 next = readyQueue.remove();
			pcb.setState("running");
			next.setComplete(clock + next.getBurst());
		}
		else {
			cpu.setState("free");
		}
	}
}
