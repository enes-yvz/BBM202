
public class StoogeSort implements Sort {
	
	public Item[] sort(Item[] input, int i, int j) {
		
		// If the leftmost element is larger than the rightmost element
		
		if (input[i].compareTo(input[j])==1) {
			
			// Swap the leftmost element and the rightmost element
			
			Item temp=input[i];
			
			input[i]=input[j];
			
			input[j]=temp;
			
		}
		
		// If there are at least 3 elements in the array
			
		if ((j - i + 1) > 2) {
			
			int t = (int) Math.floor((j - i + 1) / 3);
			
			sort(input, i , j-t); // Sort the first 2/3 of the array
			
			sort(input, i+t, j); // Sort the last 2/3 of the array
			
			sort(input, i , j-t); // Sort the first 2/3 of the array again
			
		}
		
		return input;
		
	}
	
	// For default parameters method overloading
	
	public void sort(Item[] input) {
		
		sort(input, 0, input.length-1);
		
		
	}
}
