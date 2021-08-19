
public class GnomeSort implements Sort {
	
	public void sort(Item[] input) {
		
		int pos = 0;
		
		while (pos < input.length) {
			
			if (pos == 0 || ((input[pos].compareTo(input[pos-1])==1) || (input[pos].compareTo(input[pos-1])==0))) {
				
				pos = pos + 1;
				
			}
			
			else {
				
				Item temp = input[pos];
				
				input[pos]= input[pos-1];
				
				input[pos-1]=temp;
				
				pos = pos - 1;
				
			}
			
		}
		
	}

}
