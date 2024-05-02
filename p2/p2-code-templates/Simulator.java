//TODO: None. No changes needed. 
// You should not modify this class in order to match sample runs.


import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * A class simulating cache accesses and maintenance.
 * @author Y. Zhong
 */
public class Simulator{

	/**
	 * This is the main method as the entrance of simulation. 
	 * It sets the paramaters used in simulation.
	 * @param args command line args 
	 */
	public static void main(String[] args) {
				
		if (args.length == 1 || (args.length==2 && args[1].equals("-d"))){
		
			Scanner stdIn = new Scanner(System.in);
			
			//pick which cache replacement policy to simulate
			System.out.println("Select the cache to simulate: ");
			System.out.print(" 1-FIFO Cache; 2-LRU Cache; 3-LFU Cache.\n Option: ");
			
			int option = stdIn.nextInt();
			stdIn.nextLine();
			
			while (option<1 || option>3){
				System.out.println("You can only select option 1-3.");
				System.out.println("Select the cache to simulate: ");
				System.out.print(" 1-FIFO Cache; 2-LRU Cache; 3-LFU Cache.\n Option: ");
				option = stdIn.nextInt();
				stdIn.nextLine();
			}

			//pick a cache size to simulate
			System.out.print("Select cache size to simulate (positive integer in [1,256]): ");
			
			int size = stdIn.nextInt();
			stdIn.nextLine();
			
			while (size<1 || size>256){
				System.out.println("You can only select a size in [1,256].");
				System.out.print("Select cache size to simulate (positive integer in [1,256]): ");
				size = stdIn.nextInt();
				stdIn.nextLine();
			}
					
			try {
				//simulate with the specified cache replacement, cache size, 
				//and a file for a seq of accesses
				simulate(args[0], option, size, (args.length==2 && args[1].equals("-d")));
			}
			catch(IOException e) {
				//System.out.println(e.toString());
				e.printStackTrace();
				return;
			}
						
		}
		
		else {
			//Usage for incorrect format
			System.out.println("Usage: java Simulator InputFile [-d]");
		}
		
	}
	
	/**
	 * This is the method of the actual step-by-step simulation.
	 * @param filename the name (path to) an input file with the access sequence
	 * @param cacheType cache replacement policy to simulate
	 * @param size cache size to simulate
	 * @param detailed whether the detailed record of each access would be recorded
	 * @throws IOException If an input exception occurred with input filename
	 */
	public static void simulate(String filename, int cacheType, int size, boolean detailed) throws IOException{
	
		BasicList<String> addrQueue = fileToAddrQueue(filename);	
		Cache cache;
		BasicMap<String, Integer> accRecord=null;
		BasicMap<String, Integer> hitRecord=null;
		int hits=0;
		
		switch (cacheType){
			case 1: 
				cache = new FifoCache(size);
				System.out.format("Simulating an FIFO Cache of size %d.\n", size);
				break;
			case 2:
				cache = new LruCache(size);
				System.out.format("Simulating a LRU Cache of size %d.\n", size);
				break;
			case 3:
				cache = new LfuCache(size);
				System.out.format("Simulating an LFU Cache of size %d.\n", size);
				break;
			default:
				throw new IllegalArgumentException("cache option can only be 1-3.");
		}
		System.out.println("---------------------------------------------");
		
		if (detailed){
			accRecord = new BasicMap<>();
			hitRecord = new BasicMap<>();
		}
		int index = 0;
		for (String addr: addrQueue){
			System.out.format("Access %d: %s", index, addr);
			if (detailed)
				updateRecord(accRecord, addr);
				
			if (cache.access(addr)){			
				System.out.println(" - Hit");
				hits++;
				if (detailed)
					updateRecord(hitRecord, addr);
			}
			else{
				System.out.println(" - Miss");
			}
			System.out.println("cache content after access: ");
			System.out.println(cache);
			System.out.print(cache.isFull()?"cache full, ":"cache not full, ");
			System.out.println("next to replace: "+ cache.nextToReplace());
			System.out.println("---------------------------------------------");
			index++;
		}
		
		System.out.format("Hit Rate: %.2f%%\n", (double)hits/(index+1)*100 );

		if (detailed){
			System.out.println("Accesses: "+ accRecord);
			System.out.println("Hits: "+ hitRecord);
		}
	
	}
	
	/**
	 * This is the method that construct a list of accesses from the input file.
	 * @param filename the name (path to) an input file with a sequence of accesses
	 * @return a list of Strings, each representing one access from the file
	 * @throws IOException If an input exception occurred with input filename
	 */
	public static BasicList<String> fileToAddrQueue(String filename) throws IOException {
		
		Scanner s = new Scanner(new File(filename));		
		BasicList<String> queue = new BasicList<>();
		
		while(s.hasNext()) {
			queue.addLast(s.next());			
		}
		
		return queue;
	}
	
	/**
	 * This is the method that update the record of an address. 
	 * Always increment the counter value of addr by 1 in the record.
	 * @param record the record to be updated (a hash map)
	 * @param addr the address involved in the updating	 
	 */
	public static void updateRecord(BasicMap<String, Integer> record, String addr){
		if (record==null || addr==null)
			return;
			
		Integer count = record.get(addr);
		if (count==null){
			record.put(addr, 1);
		}
		else{
			record.put(addr,count+1);
		}
	
	}
	
	
}
