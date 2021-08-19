
public class ShakerSort implements Sort {
	
	public void sort(Item[] input) {
		
		boolean swapped = false ;
		
		do {
		
			for (int i= 0 ; i < input.length- 1; i++) {
				
				if (input[i].compareTo(input[i+1])==1) {
					
					// test whether the two elements are in the wrong order
					
					Item temp = input[i];
					
					input[i]=input[i+1];
					
					input[i+1]= temp;
					
					swapped = true;
				}
				
			}
			
			if (swapped == false) {
				
				break; // we can exit the outer loop here if no swaps occurred.
			}
			
			swapped = false;
			
			for (int i= input.length- 2; i > -1 ; i--) {
				
				if (input[i].compareTo(input[i+1])==1) {
					
					Item temp = input[i];
					
					input[i]=input[i+1];
					
					input[i+1]= temp;
					
					swapped = true;
					
				}
				
			}
			
		} while(swapped==true); // if no elements have been swapped, then the list is sorted
	}

}
