import java.util.*;

public class SJF  {
	
	static int numJobs = 0;
	static int totalProcessingTime = 0;
	static int totalWaitingTime = 0;
	static int totalTurnaroundTime = 0;
	static int IOcounter = 0;
	static int timer = 0;
	static PriorityQueue <JobQueue> readyQueue = new PriorityQueue <JobQueue> ();
	static PriorityQueue <JobQueue> IOqueue = new PriorityQueue <JobQueue> ();
  	public static void run (Queue<JobQueue> pq, String algorithm) {
		
  		//long term scheduler filling up memory
		for (int i=0; i<10; i++) {
			if (pq.peek().arrivalTime <= timer)		
				readyQueue.add(pq.remove());
		}		
		
		//keep working the cpu until the IOqueue readyQueue and the original jobQueu is finished
		while (!readyQueue.isEmpty() || !pq.isEmpty() || !IOqueue.isEmpty()) {
			//special case when the last couple of cpu bursts was less than 10 and all the remaining jobs end up in the IOqueue
			//or its the last job and it has complete its I/O
			//usually happens towards the end
			if (readyQueue.isEmpty ()) {
				while (IOcounter != 10) {
					timer++;
					if (timer%200 == 0)		System.out.println( "Total jobs in Ready queue " + readyQueue.size() + " Total jobs in Blocked Queue " + IOqueue.size() + " Total jobs completed " + numJobs );
					IOcounter++;
				}		
				IOcounter = 0;
				readyQueue.add(IOqueue.remove());
			}
			
			JobQueue temp = readyQueue.remove ();
//			System.out.println(temp.jobID + " " + temp.bursts);
			int x = temp.bursts.remove();
			//finish the current cpu bursts			
			while (x != 0) {	
				x--;
				temp.processingTime++;
				timer++;
				if (timer%200 == 0)		System.out.println( "Total jobs in Ready queue " + readyQueue.size() + " Total jobs in Blocked Queue " + IOqueue.size() + " Total jobs completed " + numJobs );
				if (IOcounter < 10 && !IOqueue.isEmpty())			IOcounter++;
				if (x == 0 && !IOqueue.isEmpty() && IOcounter>=10) {		
					readyQueue.add(IOqueue.remove());
					IOcounter = 0;
				}
			}
			//when a job finishes all its bursts
			if (temp.bursts.isEmpty ())	{
				if (!pq.isEmpty() && (pq.peek().arrivalTime <= timer)) 
					readyQueue.add(pq.remove());
				temp.waitingTime = timer -temp.processingTime - temp.arrivalTime;
				temp.turnaroundTime = temp.processingTime + temp.waitingTime;
				System.out.println("job ID " + temp.jobID + " arrival time " + temp.arrivalTime + " completion time " + timer + " processing time " + temp.processingTime + " waiting Time " + temp.waitingTime + " turnaround time " + temp.turnaroundTime);
				numJobs++;
				totalProcessingTime += temp.processingTime;
				totalWaitingTime += temp.waitingTime;
				totalTurnaroundTime += temp.turnaroundTime;
				continue;
			}
			temp.processingTime += 10;
			IOqueue.add(temp);
		}
		
		System.out.println("\n" +"schduling algorithm " + algorithm + " current CPU clock " + timer + " average processing time " + (totalProcessingTime/numJobs) + " average waiting time " + (totalWaitingTime/numJobs) + " average tunaround time " + (totalTurnaroundTime/numJobs));

	}
}
