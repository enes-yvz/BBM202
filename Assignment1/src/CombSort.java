
public class CombSort implements Sort{

	public void sort(Item[] input) {
		
		int gap = input.length; // Initialize gap size
		
		double shrink = 1.3; // Set the gap shrink factor
		
		boolean sorted = false;
		
		while (sorted == false) {
			
			/*for (Item i:input) {
			
			System.out.print(i.getNumber()+"-"+i.getName() +" ");
			
			}
		
			System.out.println();*/
			
			// Update the gap value for a next comb
			
			gap = (int) Math.floor(gap / shrink);
		
			if (gap <= 1) {
				
				gap = 1;
				sorted = true; // If there are no swaps this pass, we are done	
				
			}
		
			// A single "comb" over the input list 
			
			int i = 0;
		
			// See Shell sort for a similar idea
			
			while (i + gap < input.length) {
			
				if (input[i].compareTo(input[i+gap])==1) {
					
					Item temp = input[i];
					
					input[i]= input[i+gap];
					
					input[i+gap] =temp;
					
					sorted = false;
					
				}
			
				// If this assignment never happens within the loop,
				// Then there have been no swaps and the list is sorted.
				
				i = i + 1;
			
			}
			
		}
		
	}
	
}
