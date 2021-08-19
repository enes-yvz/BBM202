import java.util.Random;

public class Main {
	
	static long[] results = new long[5];
	
	static int count = 8;
	
	public static void main(String[] args) {
		
		Random generator=new Random();
			
		Item[] test = new Item[(int) Math.pow(2, count)];
		
		Item[] warm = new Item[(int) Math.pow(2, 8)];
		
		CombSort combSort = new CombSort();
		GnomeSort gnomeSort = new GnomeSort();
		ShakerSort shakerSort = new ShakerSort();
		StoogeSort stoogeSort = new StoogeSort();
		BitonicSort bitonicSort = new BitonicSort();
		
		// For warming up java compiler I used an extra for loop
		
		 for (int h=0;h<10;h++) {
			
			for (int i =0 ;i<Math.pow(2, 8);i++) {
				
				int number = generator.nextInt(Integer.MAX_VALUE);
				
				int name = i;
				
				Item item = new Item(number,name);
				
				warm[i]=item;
				
			}
			
			controller(warm,combSort);
			
		} 
		
		for (int i =0 ;i<Math.pow(2, count);i++) {
				
			int number = generator.nextInt(Integer.MAX_VALUE);
			
			int name = i;
			
			Item item = new Item(number,name);
			
			test[i]=item;
			
		}
		
		results[0] = controller(test,combSort);
		
		System.gc();
		
		results[1] = controller(test,gnomeSort);
		
		System.gc();
		
		results[2] = controller(test,shakerSort);
		
		System.gc();
		
		results[3] = controller(test,stoogeSort);
		
		System.gc();
		
		results[4] = controller(test,bitonicSort);
		
		System.out.println();
		System.out.println("Time For " + (int)Math.pow(2, count) + " Items");
		System.out.println();
		System.out.format("%-15s%-15s%-15s%-15s%-15s","Comb Sort","Gnome Sort","Shaker Sort","Stooge Sort","Bitonic Sort");
		System.out.println();
		System.out.println();
		System.out.format("%-15s%-15s%-15s%-15s%-15s",results[0]+" ns",results[1]+" ns",results[2]+" ns",results[3]+" ns",results[4]+" ns");
		System.out.println();
			
	}
	
	public static long controller(Item[] test,Sort sort) {
		
		Item[] temp = test.clone();
		
		long start = System.nanoTime();
		
		sort.sort(temp);
		
		long now = System.nanoTime();
		
		return now -start ;
		
	}

}
