
public class BitonicSort implements Sort {
	
	//The sequence to be sorted starts at index position low,
	
	public void compAndSwap(Item[] input, int i, int j, int dire) {
		
		if ( (dire==1 && input[i].compareTo(input[j])==1) || (dire==0 && input[i].compareTo(input[j])==-1) ){
			
			Item temp = input[i];
			
			input[i]=input[j];
			
			input[j]=temp;
			
		}
		
	}
	
	//The parameter cnt is the number of elements to be sorted.
	
	public void bitonicMerge(Item[]input, int low, int cnt, int dire) {
		
		if (cnt > 1) {
			
			int k = cnt/2;
			
			for(int i = low;i < low+k;i++) {
				
				compAndSwap(input, i, i+k, dire);
				
			}

			bitonicMerge(input, low, k, dire);
			bitonicMerge(input, low+k, k, dire);
		}	
		
	}
	
	public void bitonicSort(Item[] input, int low, int cnt, int dire) {
		
		if (cnt > 1) {
			
			int k = cnt/2;
			
			bitonicSort(input, low, k, 1);
			bitonicSort(input, low+k, k, 0);
			bitonicMerge(input, low, cnt, dire);
		
		}
		
	}
	
	// Caller of bitonicSort for sorting an array of length N in ASCENDING order
	
	public void sort(Item[] input, int N, int up) {
		
		bitonicSort(input,0,N,up);
		
	}
	
	// For default parameters method overloading
	
	public void sort(Item[] input) {
		
		sort(input,input.length,1);
	}
	
}
