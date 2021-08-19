import java.time.LocalTime;

public class Assignment implements Comparable {
	
    private String name;
    private String start;
    private int duration;
    private int importance;
    private boolean maellard;

    /*
        
    Getter methods
        
     */
    
    public String getName() {
        return name;
    }

    public String getStartTime() {
        return start;
    }

    public int getDuration() {
        return duration;
    }

    public int getImportance() {
        return importance;
    }

    public boolean isMaellard() {
        return maellard;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
    	
    	/* 
    	 
    	I used LocalTime for adding duration to the start time and getting finish time
    	
    	While doing this first I get hour in integer increase it with duration convert it to time and finally get time as a string
    	
    	*/
    	
    	LocalTime time = LocalTime.parse(this.start);
    	
    	int hour = time.getHour() + this.duration;
    	
    	int minute = time.getMinute();
    	
    	time = LocalTime.of(hour, minute);
    	
        return String.valueOf(time);
        
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
       
    	return this.importance * (this.maellard ? 1001 :1) / (double) this.duration;
    	
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
    	
    	/* 
    	
    	I compared LocalTime objects based on instructions
    	
    	*/
    	
    	if (LocalTime.parse(this.getFinishTime()).isBefore(LocalTime.parse(((Assignment) o).getFinishTime()))) {
			
			return -1;
		}
		
		else if (LocalTime.parse(this.getFinishTime()).equals(LocalTime.parse(((Assignment) o).getFinishTime()))) {
			
			return 0;
		}
		
		else {
			
			return 1;
		}
    }

    /**
     * @return Should return a string in the following form:
     * Assignment{name='Refill vending machines', start='12:00', duration=1, importance=45, maellard=false, finish='13:00', weight=45.0}
     */
    @Override
    public String toString() {
        return "Assignment{name='"+name+ "', start='"+ start + "', duration=" + duration +", importance=" +importance +
        	   ", maellard="+ maellard + ", finish='" + this.getFinishTime() + "', weight="+ this.getWeight()+"}";
    }
}
