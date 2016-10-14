import java.io.*;
import java.util.*;

public class cpuscheduler {

	public static void main(String[] args)  {
		
		Queue<JobQueue> pq = new LinkedList<JobQueue>();
		Scanner sc = null;
		try {
			sc = new Scanner (new FileReader (args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (sc.hasNextLine() ) {
			String line = sc.nextLine ();
			JobQueue jq = new JobQueue ();
			
			String [] tokens = line.split(" ") ;
			int counter = 0;
			for (String token: tokens) {
				if (counter == 0)		jq.jobID = Integer.parseInt(token);
				else if (counter == 1)		jq.arrivalTime = Integer.parseInt(token);
				else if (counter == 2)		jq.numBursts = Integer.parseInt(token);
				else 					jq.bursts.add( Integer.parseInt(token));
				counter++;
			}
			pq.add(jq);
		}
		sc.close();
		String schduler = args[1];
		if (schduler.equals("FCFS"))			FCFS.run(pq, schduler);
		else if (schduler.equals("SJF"))		SJF.run(pq, schduler);
		else if (schduler.equals("RR")){
			String quantum = args[2];
			RR.run(pq, Integer.parseInt(quantum), schduler);
		}	
	}
}
