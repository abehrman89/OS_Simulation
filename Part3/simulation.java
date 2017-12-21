import java.util.*;
public class simulation {
	
	CPU cpu = new CPU("free");
	IO io_channel = new IO("free");
	LinkedList<Process> readyQueue = new LinkedList<Process>();
	LinkedList<Process> IO_Queue = new LinkedList<Process>();
	Queue<Process> processes = new LinkedList<Process>();
	Queue<Process> tracking = new LinkedList<Process>();
	PCB pcb = new PCB();

	public static void main (String args[]) {
		simulation sim = new simulation();
		//sim.run_FCFS_sim();
		//sim.run_SJF_sim();
		sim.run_RR_sim();
		System.out.println();
		System.out.println("Simulation Complete");
	}
	
	/**
	 * run the simulation with the First-Come, First-Served Algorithm
	 */
	public void run_FCFS_sim() {
	
		Boolean stop = false;
		System.out.println();
		System.out.println();
		System.out.println("FCFS ALGORITHM");
		double clock = 0.0;
		double lastComplete;
		double cpu_use = 0.0;
	
		Random rand = new Random();
		int burst = 30;
		int ex = 0;

		//create processes
		for (int i = 1; i < 11; i++) {
			ex = (rand.nextInt(120000) + 120000);
			processes.add(new Process("P" + i, 0.0, ex, burst));
			burst += 5;
		}
		
		//tracking queue used to determine when simulation should end
		//processes will be removed from queue as they complete
		for (Process p : processes) {
			tracking.add(p);
			arrival(p, clock);
		}
		
		while (!stop) {
			
			//if tracking is empty, simulation is over
			if (tracking.isEmpty()) {
				stop = true;
			}
			else {
			
				for (Process p : processes) {
					
					String status = p.getStatus();
					switch(status) {
					
					//if process is in readyQueue, update waiting time
					case "cpu_waiting":
						p.updateWaiting();
						break;
					
					/*
					 * if process is running on cpu check if:
					 * execution is complete or,
					 * cpu burst is complete or,
					 * update the time remaining in execution and in that cpu burst
					 */
					case "cpu_running":
						
						if(p.getRemaining() == 0) {
							completion(p, clock);
						}
						else if(p.get_cpu_run() == 0) {
							io_request(p, clock);
						}
						else {
							p.update_cpu_run();
							p.updateRemaining();
						}
						break;
						
					/*
					 * if process is running on i/o check if:
					 * i/o time is complete or,
					 * update time in i/o
					 */
					case "io_running":
						if (p.get_io_time() == 0) {
							cpu_request(p, clock);
						}
						else {
							p.update_io_time();
						}
						break;
					}
				}
				clock += 1.0;
			}
			
			//for calculating cpu utilization
			for (Process proc : processes) {
				if (proc.getStatus() == "cpu_running") {
					cpu_use++;
					break;
				}
			}
		}
		
		lastComplete = clock;
		cpu.setUtilization(cpu_use, lastComplete);
		System.out.println();
		System.out.println("CPU Utilization: " + cpu.getUtilization() + "%");
		
		System.out.println();
		System.out.println("Turnaround Times");
		for (Process p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getTurnaround());
		}
		
		System.out.println();
		System.out.println("Waiting Times");
		for (Process p : processes) {
			System.out.println("Process " + p.getPID() + ": " + p.getWaiting());
		}
		
		double avgWait = 0;
		for (Process p : processes) {
			avgWait += p.getWaiting();
		}
		avgWait = avgWait / processes.size();
		System.out.println();
		System.out.println("Average Waiting Time: " + avgWait);
		
		double throughput = processes.size() / lastComplete;
		System.out.println();
		System.out.println("Throughput: " + throughput);
		
		processes.clear();
	}

	/**
	 * run the simulation with the Shortest Job First Algorithm
	 */
	public void run_SJF_sim() {

		Boolean stop = false;
		System.out.println();
		System.out.println();
		System.out.println("SJF ALGORITHM");
		double clock = 0.0;
		double lastComplete;
		double cpu_use = 0.0;
	
		Random rand = new Random();
		int burst = 30;
		int ex = 0;

		//create processes
		for (int i = 1; i < 11; i++) {
			ex = (rand.nextInt(120000) + 120000);
			processes.add(new Process("P" + i, 0.0, ex, burst));
			burst += 5;
		}
		
		//tracking queue used to determine when simulation should end
		//processes will be removed from queue as they complete
		for (Process p : processes) {
			tracking.add(p);
			SJF_arrival(p, clock);
		}
		
		Process pr = readyQueue.remove();
		SJF_start(pr, clock);
		
		while (!stop) {
			
			//if tracking is empty, simulation is over
			if (tracking.isEmpty()) {
				stop = true;
			}
			else {
			
				for (Process p : processes) {
					
					String status = p.getStatus();
					switch(status) {
					
					//if process is in readyQueue, update waiting time
					case "cpu_waiting":
						p.updateWaiting();
						break;
					
					/*
					 * if process is running on cpu check if:
					 * execution is complete or,
					 * cpu burst is complete or,
					 * update the time remaining in execution and in that cpu burst
					 */
					case "cpu_running":
						
						if(p.getRemaining() == 0) {
							completion(p, clock);
						}
						else if(p.get_cpu_run() == 0) {
							//System.out.println("Sending process " + p.getPID() + " to io at time " + clock);
							io_request(p, clock);
						}
						else {
							p.update_cpu_run();
							p.updateRemaining();
						}
						break;
						
					/*
					 * if process is running on i/o check if:
					 * i/o time is complete or,
					 * update time in i/o
					 */
					case "io_running":
						if (p.get_io_time() == 0) {
							//System.out.println("Sending process " + p.getPID() + " to cpu at time " + clock);
							SJF_cpu_request(p, clock);
						}
						else {
							p.update_io_time();
						}
						break;
					}
				}
				clock += 1.0;
			}
			
			//for calculating cpu utilization
			for (Process proc : processes) {
				if (proc.getStatus() == "cpu_running") {
					cpu_use++;
					break;
				}
			}
		}
		
		lastComplete = clock;
		cpu.setUtilization(cpu_use, lastComplete);
		System.out.println();
		System.out.println("CPU Utilization: " + cpu.getUtilization() + "%");
		
		System.out.println();
		System.out.println("Turnaround Times");
		for (Process p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getTurnaround());
		}
		
		System.out.println();
		System.out.println("Waiting Times");
		for (Process p : processes) {
			System.out.println("Process " + p.getPID() + ": " + p.getWaiting());
		}
		
		double avgWait = 0;
		for (Process p : processes) {
			avgWait += p.getWaiting();
		}
		avgWait = avgWait / processes.size();
		System.out.println();
		System.out.println("Average Waiting Time: " + avgWait);
		
		double throughput = processes.size() / lastComplete;
		System.out.println();
		System.out.println("Throughput: " + throughput);
		
		processes.clear();
	}

	/**
	 * run the simulation with the Round Robin Algorithm
	 */
	public void run_RR_sim() {
		Boolean stop = false;
		System.out.println();
		System.out.println();
		System.out.println("RR ALGORITHM");
		double clock = 0.0;
		double lastComplete;
		double cpu_use = 0.0;
	
		//BEGIN COMMENT BLOCK HERE TO SEE ROUND ROBIN EXPERIMENTS
		
		Random rand = new Random();
		int burst = 30;
		int ex = 0;
		
		//create processes
		for (int i = 1; i < 11; i++) {
			ex = (rand.nextInt(120000) + 120000);
			processes.add(new Process("P" + i, 0.0, ex, burst));
			burst += 5;
		}
		
		//END COMMENT BLOCK HERE TO SEE ROUND ROBIN EXPERIMENTS
		
		
		//BEGIN COMMENT BLOCK HERE TO SEE ROUND ROBIN WITHOUT EXPERIMENTS
		/*
		Process P1 = new Process ("P1", 0.0, 500.0, 30);
		processes.add(P1);
		Process P2 = new Process ("P2", 0.0, 500.0, 35);
		processes.add(P2);
		Process P3 = new Process ("P3", 0.0, 500.0, 40);
		processes.add(P3);
		Process P4 = new Process ("P4", 0.0, 500.0, 45);
		processes.add(P4);
		Process P5 = new Process ("P5", 0.0, 500.0, 50);
		processes.add(P5);
		Process P6 = new Process ("P6", 0.0, 500.0, 55);
		processes.add(P6);
		Process P7 = new Process ("P7", 0.0, 500.0, 60);
		processes.add(P7);
		Process P8 = new Process ("P8", 0.0, 500.0, 65);
		processes.add(P8);
		Process P9 = new Process ("P9", 0.0, 500.0, 70);
		processes.add(P9);
		Process P10 = new Process ("P10", 0.0, 500.0, 75);
		processes.add(P10);
		
		for (Process p: processes) {
			p.setQuantum(49);	//adjust quantum as needed
		}
		*/
		//END COMMENT BLOCK HERE TO SEE ROUND ROBIN WITHOUT EXPERIMENTS
		
		//tracking queue used to determine when simulation should end
		//processes will be removed from queue as they complete
		for (Process p : processes) {
			tracking.add(p);
			arrival(p, clock);
		}
		
		while (!stop) {
			
			//if tracking is empty, simulation is over
			if (tracking.isEmpty()) {
				stop = true;
			}
			else {
				for (Process p : processes) {
					
					String status = p.getStatus();
					switch(status) {
					
					//if process is in readyQueue, update waiting time
					case "cpu_waiting":
						p.updateWaiting();
						break;
					
					/*
					 * if process is running on cpu check if:
					 * execution is complete or,
					 * cpu burst is complete or,
					 * quantum is complete or,
					 * update the time remaining in execution and in that cpu burst
					 */
					case "cpu_running":
						if (p.getRemaining() == 0) {
							completion(p, clock);
						}
						else if (p.get_cpu_run() == 0) {
							io_request(p, clock);
						}
						else if (clock == p.getInterrupt()) {
							timer_interrupt(p, clock);
						}
						else {
							p.update_cpu_run();
							p.updateRemaining();
						}
						break;
						
					/*
					 * if process is running on i/o check if:
					 * i/o time is complete or,
					 * update time in i/o
					 */	
					case "io_running":
						if (p.get_io_time() == 0) {
							cpu_request(p, clock);
						}
						else {
							p.update_io_time();
						}
						break;
					}
				}
				clock += 1.0;
			}
			
			//for calculating cpu utilization
			for (Process proc : processes) {
				if (proc.getStatus() == "cpu_running") {
					cpu_use++;
					break;
				}
			}
		}
		
		lastComplete = clock;
		cpu.setUtilization(cpu_use, lastComplete);
		System.out.println();
		System.out.println("CPU Utilization: " + cpu.getUtilization() + "%");
		
		System.out.println();
		System.out.println("Turnaround Times");
		for (Process p : processes) {
			p.setTurnaround(p.getComplete() - p.getArrival());
			System.out.println("Process " + p.getPID() + ": " + p.getTurnaround());
		}

		
		System.out.println();
		System.out.println("Waiting Times");
		for (Process p : processes) {
			System.out.println("Process " + p.getPID() + ": " + p.getWaiting());
		}
		
		double avgWait = 0;
		for (Process p : processes) {
			avgWait += p.getWaiting();
		}
		avgWait = avgWait / processes.size();
		System.out.println();
		System.out.println("Average Waiting Time: " + avgWait);
		
		double throughput = processes.size() / lastComplete;
		System.out.println();
		System.out.println("Throughput: " + throughput);
		
		processes.clear();
	}
	
	public void SJF_start(Process p, double clock) {
		cpu.setState("busy");
		pcb.setState("running");
		p.setStatus("cpu_running");
		p.setStart(clock);
	}
	
	/**
	 * process has arrived for execution
	 * if cpu is free, start process execution and calculate completion time
	 * if cpu is busy, add process to readyQueue (sorted by arrival time)
	 * @param p
	 * @param clock
	 */
	public void arrival(Process p, double clock) {
		if (cpu.getState() == "free") {
			cpu.setState("busy");
			pcb.setState("running");
			
			//COMMENT OUT p.setQuantum() FOR ROUND ROBIN EXPERIMENTS
			p.setQuantum(47.0);
			
			p.setStart(clock);
			p.setInterrupt(clock + p.getQuantum());
			p.setStatus("cpu_running");
		}
		else {
			pcb.setState("ready");
			readyQueue.add(p);
			p.setStatus("cpu_waiting");
		}
	}

	/**
	 * process has arrived for execution in Shortest Job First Simulation
	 * add all processes to ready queue in order of increasing execution time
	 * @param p
	 * @param clock
	 */
	public void SJF_arrival(Process p, double clock) {
		pcb.add(p);
		pcb.setState("ready");
		p.setStatus("cpu_waiting");
		if (readyQueue.isEmpty()) {
			readyQueue.add(p);
		}
		else {
			for (int i = 0; i < readyQueue.size(); i++) {
				Process temp = readyQueue.get(i);
				if (p.getExecution() < temp.getExecution()) {
					readyQueue.add(i, p);
					return;
				}
			}
			readyQueue.add(p);
		}
	}
	
	/**
	 * process is requesting i/o transfer
	 * @param p
	 * @param clock
	 */
	public void io_request(Process p, double clock) {
		p.set_cpu_run(p.getBurst());
		
		//if another process is running on i/o, add requesting process to i/o queue
		//otherwise requesting process is now running on i/o
		if (io_channel.getState() == "busy") {
			IO_Queue.add(p);
			p.setStatus("waiting");
		}
		else {
			io_channel.setState("busy");
			p.setStatus("io_running");
		}
		
		//if readyQueue is not empty, remove next process to start cpu burst
		//otherwise mark cpu as free
		if (!readyQueue.isEmpty()) {
			Process next = readyQueue.remove();
			if (next.getRemaining() == next.getExecution()) {
				next.setStart(clock);
			}
			next.setStatus("cpu_running");
			cpu.setState("busy");
			next.setInterrupt(clock + next.getQuantum());
		}
		else {
			cpu.setState("free");
		}
	}
	
	/**
	 * process is requesting cpu transfer
	 * @param p
	 * @param clock
	 */
	public void cpu_request(Process p, double clock) {
		p.set_io_time();
		
		//if cpu is busy, add process to readyQueue
		//otherwise mark process as running on cpu
		if (cpu.getState() == "busy") {
			readyQueue.add(p);
			p.setStatus("cpu_waiting");
		}
		else {
			cpu.setState("busy");
			p.setStatus("cpu_running");
			p.setInterrupt(clock + p.getQuantum());
		}
		
		//if i/o queue is not empty, remove next process and start i/o burst
		//otherwise mark i/o as free
		if (!IO_Queue.isEmpty()) {
			Process next = IO_Queue.remove();
			next.setStatus("io_running");
			io_channel.setState("busy");
		}
		else {
			io_channel.setState("free");
		}
	}
	
	/**
	 * process is requesting cpu transfer in Shortest Job First Algorithm
	 * @param p
	 * @param clock
	 */
	public void SJF_cpu_request(Process p, double clock) {
		p.set_io_time();
		
		//if i/o queue is not empty, remove next process and start i/o burst
		//otherwise mark i/o as free
		if (!IO_Queue.isEmpty()) {
			Process next = IO_Queue.remove();
			next.setStatus("io_running");
			io_channel.setState("busy");
		}
		else {
			io_channel.setState("free");
		}
		
		//if cpu is busy, add process to readyQueue in correct spot
		//otherwise mark process as running on cpu
		if (cpu.getState() == "busy") {
			p.setStatus("cpu_waiting");
			if (readyQueue.isEmpty()) {
				readyQueue.add(p);
			}
			else {
				for (int i = 0; i < readyQueue.size(); i++) {
					Process temp = readyQueue.get(i);
					if (p.getRemaining() < temp.getRemaining()) {
						readyQueue.add(i, p);
						return;
					}
				}
				readyQueue.add(p);
			}
		}
		else {
			cpu.setState("busy");
			p.setStatus("cpu_running");
		}
	}
	
	/**
	 * quantum has expired, so process must stop running on cpu
	 * and next process should start (if one is in readyQueue)
	 * @param p
	 * @param clock
	 */
	public void timer_interrupt(Process p, double clock) {
		
		//if readyQueue is empty, continue running same process on cpu
		//until next interrupt
		if (readyQueue.isEmpty()) {
			p.updateRemaining();
			p.setInterrupt(clock + p.getQuantum());
			return;
		}
		
		//otherwise add process to readyQueue
		//and start next process in readyQueue on cpu
		readyQueue.add(p);
		p.setStatus("cpu_waiting");
		Process pr = readyQueue.remove();
		if (pr.getRemaining() == pr.getExecution()) {
			pr.setStart(clock);
		}
		pr.setInterrupt(clock + pr.getQuantum());
		pr.setStatus("cpu_running");
	}
	
	/**
	 * process has finished executing
	 * if there is another process in readyQueue, start process execution and calculate completion time
	 * if readyQueue is empty, set cpu status to "free"
	 * @param p
	 * @param clock
	 */
	public void completion(Process p, double clock) {
		
		//remove process from pcb and tracking
		pcb.remove(p);
		tracking.remove(p);
		p.setComplete(clock);
		p.setStatus("done");
		//System.out.println("Process " + p.getPID() + " is complete at time " + clock);
		
		//if readyQueue is not empty, remove next process and start cpu execution
		//otherwise set cpu as free
		if (!readyQueue.isEmpty()) {
			Process next = readyQueue.remove();
			pcb.setState("running");
			if (next.getRemaining() == next.getExecution()) {
				next.setStart(clock);
			}
			next.setStatus("cpu_running");
			next.setInterrupt(clock + p.getQuantum());
			cpu.setState("busy");
		}
		else {
			cpu.setState("free");
		}
	}
}