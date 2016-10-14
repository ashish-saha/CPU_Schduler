import java.util.LinkedList;
public class JobQueue implements Comparable <JobQueue> {
	
		int jobID;
		int arrivalTime;
		int numBursts;
		int processingTime;
		int waitingTime;
		int turnaroundTime;
		LinkedList<Integer> bursts; 
	
		public JobQueue () {
			jobID = 0;
			arrivalTime = 0;
			numBursts = 0;
			processingTime = 0;
			waitingTime = 0;
			turnaroundTime = 0;
			bursts = new LinkedList <Integer> ();
		}
	
		public String toString () {
			String s = (jobID + " " + arrivalTime + " " + numBursts );
			while (! bursts.isEmpty()) {
				int x = bursts.remove();
				s = s + " " + x; 
			}
			return s;
		}

		
	public int compareTo(JobQueue jq) {
		
		if 		(this.bursts.peek() < jq.bursts.peek())		return -1;
		else if (this.bursts.peek() > jq.bursts.peek())		return 1;
		else												return 0;
	}
	
}
