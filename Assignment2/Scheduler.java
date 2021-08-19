import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Scheduler {

    private Assignment[] assignmentArray;
    private Integer[] C;
    private Double[] max;
    private ArrayList<Assignment> solutionDynamic;
    private ArrayList<Assignment> solutionGreedy;

    /**
     * @throws IllegalArgumentException when the given array is empty
     */
    public Scheduler(Assignment[] assignmentArray) throws IllegalArgumentException {
    	
    	/* 
    	
    	All the properties of this class should be initialized here
    	
    	 */
    	
    	if (assignmentArray.length==0) {
    		
    		throw new IllegalArgumentException();
    	}
    	
    	this.max = new Double[assignmentArray.length];
    	
    	Arrays.fill(max, 0.0);
    	
    	this.C = new Integer[assignmentArray.length];
    	
    	this.solutionDynamic = new ArrayList<Assignment>();
    	
    	this.solutionGreedy = new ArrayList<Assignment>();
    	
    	Arrays.sort(assignmentArray);
        
    	this.assignmentArray = assignmentArray;
    	
    	calculateC();
    	
    	calculateMax(assignmentArray.length-1);
        
    }

    /**
     * @param index of the {@link Assignment}
     * @return Returns the index of the last compatible {@link Assignment},
     * returns -1 if there are no compatible assignments
     */
    private int binarySearch(int index) {
    	
    	/* 
    	
    	Customized version of binary search
    	
    	We are store two values low and high While high is bigger than low continue our while loop else return low-1 
    	
    	Actually we are searching an index after our searched element such as if we insert an item this index our array is still sorted 
    	
    	Decrease this index one and return this 
    	
    	*/
    	
    	int high = assignmentArray.length;
    	
    	int low = 0;
    	
		while (low < high) {
			
			int now = (low+high)/2;
	        
	        if (LocalTime.parse(assignmentArray[now].getFinishTime()).isAfter((LocalTime.parse(assignmentArray[index].getStartTime())))) {
	        	
	        	high = now;
	        	
	        }
	        
	        else { 
	        	
	        	low = now +1; 
	        }
	        
		}
		
	    return low -1;
	    
    }


    /**
     * {@link #C} must be filled after calling this method
     */
    private void calculateC() {
    	
    	/* 
    	
    	Binary search each integer from zero to length of assignment array and pushing them to the C array
    	
    	*/
    	
    	for (int i=0;i<assignmentArray.length;i++) {
    		
    		C[i] = binarySearch(i);
    		
    	}
    	

    }


    /**
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleDynamic() {
    	
    	findSolutionDynamic(assignmentArray.length -1);
    	
    	Collections.reverse(solutionDynamic);
    	
        return solutionDynamic;
    }

    /**
     * {@link #solutionDynamic} must be filled after calling this method
     */
    private void findSolutionDynamic(int i) {
    	
    	/* 
    	 
    	After filling the max array properly, another pass is required to find a solution.
    	 
    	If last element profit plus compatible remaining part profit is bigger than one before the last element add this last element to the solutions
    	
    	Else calculate same function with one before element from the last element
    	 
    	 */
    	
    	if (i == -1) {
        	
        	return;
        	
        }
    	
    	System.out.println("findSolutionDynamic("+i+")");
    	
    	int n = C[i];
        
        double incl = assignmentArray[i].getWeight();
        
        if (n != -1) {
        	
        	incl += max[n];
        	
        }
    	
        double  excl = max[i];
        
        if (incl >= excl) {
        	
        	solutionDynamic.add(assignmentArray[i]);
        	
        	System.out.println("Adding " + assignmentArray[i].toString() + " to the dynamic schedule");
        	
        	findSolutionDynamic(n);
        	
        }
        
        else {
        	
        	findSolutionDynamic(i-1);
        	
        }

    }

    /**
     * {@link #max} must be filled after calling this method
     */
    private Double calculateMax(int i) {
    	
    	/* 
    	
    	Dynamic recursive function This function try all different combination of works and calculate maximum profit
    	
    	But while doing them if a subproblem is calculated it isn't need to calculate this one more time
    	
    	So I stored them in max array and if they are already calculated basically call them 
    	
    	This is called as dynamic programming
    	
    	*/
    	
    	if (i == -1) {
    		
    		return 0.0;
        	
        }
    	
    	System.out.print("calculateMax(" +i + "):" + " ");
    	
    	if (max[i]!= 0.0) { 
    		
    		if (i == 0) {
        		
        		System.out.println("Zero");
        		
        	}
    		
    		else {
    			
    			System.out.println("Present");
    			
    		}
    		
        	return max[i];
        	
        }
    	
    	else if (i == 0) {
    		
    		System.out.println("Zero");
    		
    	}
        
        else {
        	
        	System.out.println("Prepare");
        }
        
        int n = C[i];
        
        double incl = assignmentArray[i].getWeight();
        
        if (n != -1) {
        	
        	incl += calculateMax(n);
        	
        }
        
        double  excl = calculateMax(i-1);
        
        max[i]= Math.max(incl, excl);
     
        return max[i] ;
    }

    /**
     * {@link #solutionGreedy} must be filled after calling this method
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleGreedy() {
    	
    	/* 
    	
    	Add the first element to the solution and find the next compatible job and continue this procedure  until the last element
    	
    	This is called as greedy solution
    	
    	*/
    	
    	int k=-2;
    	
    	for (int i=0;i<C.length;i++) {
    		 
    		if (C[i]>=k) {
    			
    			k= i;
    			
    			System.out.println("Adding " + assignmentArray[i].toString() + " to the greedy schedule" );
    			
    			solutionGreedy.add(assignmentArray[i]);
    			
    		}
    		
    	}
    	
    	return solutionGreedy;
    	
    }
}
