import java.util.LinkedList;
import java.util.Queue;
public class Simulation_2 {
	
	CPU_2 cpu = new CPU_2("free");
	LinkedList<Process_2> readyQueue = new LinkedList<Process_2>();
	Queue<Process_2> processes = new LinkedList<Process_2>();
	PCB_2 pcb = new PCB_2();

	public static void main (String args[]) {
		Simulation_2 sim = new Simulation_2();
		sim.run_FCFS_Sim();
		sim.run_SJF_Sim();
		sim.run_NPP_Sim();
		sim.run_RR_Sim();
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

		Process_2 P1 = new Process_2("P1", 0.0, 10.0, 3);
		processes.add(P1);
		Process_2 P2 = new Process_2("P2", 0.0, 1.0, 1);
		processes.add(P2);
		Process_2 P3 = new Process_2("P3", 0.0, 2.0, 3);
		processes.add(P3);
		Process_2 P4 = new Process_2("P4", 0.0, 1.0, 4);
		processes.add(P4);
		Process_2 P5 = new Process_2("P5", 0.0, 5.0, 2);
		processes.add(P5);
		
		for (Process_2 p : processes) {
			FCFS_arrival(p, clock);
		}
		while (!stop) {
			
			//loop through processes and check if clock = arrival or completion time
			for (Process_2 p : processes) {
				if (clock == p.getComplete() && clock != 0.0) {
					completion(p, clock);
					break;
				}
			}
			
			//increment clock and round to 1 decimal place
			clock += 0.1;
			clock = Math.round(clock * 100.0) / 100.0;
			
			if (clock == 20) {
				stop = true;
			}
		}
		
		//if sim is over, calculate individual and average turnaround times
		System.out.println();
		System.out.println();
		System.out.println("Turnaround Times");
		for (Process_2 p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getTurnaround());
		}
		
		System.out.println();
		System.out.println("Waiting Times");
		for (Process_2 p : processes) {
			p.setWaiting(p.getStart() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getWaiting());
		}
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

		Process_2 P1 = new Process_2("P1", 0.0, 10.0, 3);
		processes.add(P1);
		Process_2 P2 = new Process_2("P2", 0.0, 1.0, 1);
		processes.add(P2);
		Process_2 P3 = new Process_2("P3", 0.0, 2.0, 3);
		processes.add(P3);
		Process_2 P4 = new Process_2("P4", 0.0, 1.0, 4);
		processes.add(P4);
		Process_2 P5 = new Process_2("P5", 0.0, 5.0, 2);
		processes.add(P5);
		
		for (Process_2 p : processes) {
			SJF_arrival(p, clock);
		}
		
		Process_2 pr = readyQueue.remove();
		start(pr, clock);
		
		while (!stop) {
			
			//loop through processes and check if clock = arrival or completion time
			for (Process_2 p : processes) {
				if (clock == p.getComplete() && clock != 0.0) {
					completion(p, clock);
					break;
				}
			}
			
			//increment clock and round to 1 decimal place
			clock += 0.1;
			clock = Math.round(clock * 100.0) / 100.0;
			
			if (clock == 20) {
				stop = true;
			}
		}
		
		//if sim is over, calculate individual and average turnaround times
		System.out.println();
		System.out.println();
		System.out.println("Turnaround Times");
		for (Process_2 p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getTurnaround());
		}
		
		System.out.println();
		System.out.println("Waiting Times");
		for (Process_2 p : processes) {
			p.setWaiting(p.getStart() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getWaiting());
		}
		processes.clear();
	}
	
	/**
	 * run the simulation with the Nonpreemptive Priority Algorithm
	 */
	public void run_NPP_Sim() {
		Boolean stop = false;
		System.out.println();
		System.out.println("NONPREEMPTIVE PRIORITY ALGORITHM");
		System.out.println();
		double clock = 0.0;

		Process_2 P1 = new Process_2("P1", 0.0, 10.0, 3);
		processes.add(P1);
		Process_2 P2 = new Process_2("P2", 0.0, 1.0, 1);
		processes.add(P2);
		Process_2 P3 = new Process_2("P3", 0.0, 2.0, 3);
		processes.add(P3);
		Process_2 P4 = new Process_2("P4", 0.0, 1.0, 4);
		processes.add(P4);
		Process_2 P5 = new Process_2("P5", 0.0, 5.0, 2);
		processes.add(P5);
		
		for (Process_2 p : processes) {
			NPP_arrival(p, clock);
		}
		
		Process_2 pr = readyQueue.remove();
		start(pr, clock);
		
		while (!stop) {
			
			//loop through processes and check if clock = arrival or completion time
			for (Process_2 p : processes) {
				if (clock == p.getComplete() && clock != 0.0) {
					completion(p, clock);
					break;
				}
			}
			
			//increment clock and round to 1 decimal place
			clock += 0.1;
			clock = Math.round(clock * 100.0) / 100.0;
			
			if (clock == 20) {
				stop = true;
			}
		}
		
		//if sim is over, calculate individual and average turnaround times
		System.out.println();
		System.out.println();
		System.out.println("Turnaround Times");
		for (Process_2 p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getTurnaround());
		}
		
		System.out.println();
		System.out.println("Waiting Times");
		for (Process_2 p : processes) {
			p.setWaiting(p.getStart() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getWaiting());
		}
		processes.clear();
	}
	
	/**
	 * run the simulation with the Round Robin Algorithm (Quantum = 1)
	 */
	public void run_RR_Sim() {
		
		Boolean stop = false;
		System.out.println();
		System.out.println("ROUND ROBIN ALGORITHM");
		System.out.println();
		double clock = 0.0;

		Process_2 P1 = new Process_2("P1", 0.0, 10.0, 3);
		processes.add(P1);
		Process_2 P2 = new Process_2("P2", 0.0, 1.0, 1);
		processes.add(P2);
		Process_2 P3 = new Process_2("P3", 0.0, 2.0, 3);
		processes.add(P3);
		Process_2 P4 = new Process_2("P4", 0.0, 1.0, 4);
		processes.add(P4);
		Process_2 P5 = new Process_2("P5", 0.0, 5.0, 2);
		processes.add(P5);
		
		for (Process_2 p : processes) {
			RR_arrival(p, clock);
		}
		
		while (!stop) {
			
			//loop through processes and check if clock = completion or interrupt time
			for (Process_2 p : processes) {
				if (clock == p.getComplete() && clock != 0.0) {
					RR_completion(p, clock);
				}
				if (clock == p.getInterrupt() && clock != 0.0) {
					timer_interrupt(p, clock);
					break;
				}
			}
			
			//increment clock and round to 1 decimal place
			clock += 0.1;
			clock = Math.round(clock * 100.0) / 100.0;
			
			if (clock == 20) {
				stop = true;
			}
		}
		
		//if sim is over, calculate turnaround and waiting times
		System.out.println();
		System.out.println();
		System.out.println("Turnaround Times");
		for (Process_2 p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getTurnaround());
		}
		
		System.out.println();
		System.out.println("Waiting Times");
		for (Process_2 p : processes) {
			p.setWaiting(p.getTurnaround() - p.getBurst());
			System.out.println("Process " + p.getPID() + ": " + p.getWaiting());
		}
		processes.clear();
	}
	
	/**
	 * process has arrived for execution
	 * if cpu is free, start process execution and calculate completion time
	 * if cpu is busy, add process to readyQueue (sorted by arrival time)
	 * @param p
	 * @param clock
	 */
	public void FCFS_arrival(Process_2 p, double clock) {
		pcb.add(p);
		if (cpu.getState() == "free") {
			cpu.setState("busy");
			pcb.setState("running");
			p.setStart(clock);
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
	public void SJF_arrival(Process_2 p, double clock) {
		pcb.add(p);
		pcb.setState("ready");
		
		if (readyQueue.isEmpty()) {
			readyQueue.add(p);
		}
		else {
			for (int i = 0; i < readyQueue.size(); i++) {
				Process_2 temp = readyQueue.get(i);
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
	 * add process to readyQueue (sorted by priority)
	 * @param p
	 * @param clock
	 */
	public void NPP_arrival(Process_2 p, double clock) {
		pcb.add(p);
		pcb.setState("ready");
		
		if (readyQueue.isEmpty()) {
			readyQueue.add(p);
		}
		else {
			for (int i = 0; i < readyQueue.size(); i++) {
				Process_2 temp = readyQueue.get(i);
				if (p.getPriority() < temp.getPriority()) {
					readyQueue.add(i, p);
					return;
				}
			}
			readyQueue.add(p);
		}
	}
	
	/**
	 * process has arrived for execution
	 * add process to readyQueue
	 * @param p
	 * @param clock
	 */
	public void RR_arrival(Process_2 p, double clock) {
		pcb.add(p);
		if (cpu.getState() == "free") {
			cpu.setState("busy");
			pcb.setState("running");
			p.setStart(clock);
			p.setRemaining(p.getBurst());
			p.setInterrupt(clock + 1.0);
		}
		else {
			pcb.setState("ready");
			readyQueue.add(p);
			p.setRemaining(p.getBurst());
		}
	}
	
	/**
	 * clock has increased by one quantum, so current process must move back into ready queue and next process must start
	 * @param p
	 * @param clock
	 */
	public void timer_interrupt(Process_2 p, double clock) {

		if (readyQueue.isEmpty()) {
			p.updateRemaining();
			p.setComplete(clock + p.getRemaining());
			return;
		}

		p.updateRemaining();
		if (p.getRemaining() == 0) {
			RR_completion(p, clock);
			return;
		}
		
		//print gantt chart
		System.out.print(" | ");
		System.out.print("  ");
		System.out.print(p.getPID());
		System.out.print("  ");
		System.out.print(" | " + clock);
		
		readyQueue.add(p);
		Process_2 pr = readyQueue.remove();
		if (pr.getRemaining() == pr.getBurst()) {
			pr.setStart(clock);
		}
		pr.setInterrupt(clock + 1.0);
	}
	
	/**
	 * start first process in readyQueue
	 * @param p
	 * @param clock
	 */
	public void start(Process_2 p, double clock) {
		cpu.setState("busy");
		pcb.setState("running");
		p.setStart(clock);
		p.setInterrupt(clock + 1.0);
		p.setComplete(clock + p.getBurst());
	}
	
	/**
	 * process has finished executing
	 * if there is another process in readyQueue, start process execution and calculate completion time
	 * if readyQueue is empty, set cpu status to "free"
	 * @param p
	 * @param clock
	 */
	public void completion(Process_2 p, double clock) {
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
			Process_2 next = readyQueue.remove();
			pcb.setState("running");
			next.setStart(clock);
			next.setComplete(clock + next.getBurst());
		}
		else {
			cpu.setState("free");
		}
	}
	
	/**
	 * process has finished executing
	 * @param p
	 * @param clock
	 */
	public void RR_completion(Process_2 p, double clock) {
		pcb.remove(p);
		p.setComplete(clock);

		//print gantt chart
		System.out.print(" | ");
		for (int i = 0; i < p.getRemaining(); i++) {
			System.out.print(" ");
		}
		System.out.print(p.getPID());
		for (int i = 0; i < p.getRemaining(); i++) {
			System.out.print(" ");
		}
		System.out.print(" | " + clock);

		
		if (!readyQueue.isEmpty()) {
			Process_2 next = readyQueue.remove();
			pcb.setState("running");
			if (next.getRemaining() == next.getBurst()) {
				next.setStart(clock);
			}
			next.setInterrupt(clock + 1.0);
		}
		else {
			cpu.setState("free");
		}
	}
}
