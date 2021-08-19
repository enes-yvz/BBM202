import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Splitter {
	
	private HashMap<String, Integer> airportMap = new HashMap<String, Integer>(); 
	
	private HashMap<String, String> cityMap = new HashMap <String, String>(); 
	
	private ArrayList<Flight> flightList = new ArrayList<Flight>(); 
	
	public void airport(String[] inputList) {
		
		int k=0;
		
		for(String i: inputList) {
			
			String[] inputParts = i.split("\t");
			
			for(int j=1;j< inputParts.length;j++) {
				
				airportMap.put(inputParts[j],k);
				
				cityMap.put(inputParts[j], inputParts[0]);
				
				k++;
				
			}
			
		}
		
	}
	
	public void flight(String[] inputList) {
		
		for(String i: inputList) {
			
			String[] inputParts = i.split("\t");
			
			String[] dateParts = inputParts[2].split(" ");
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			Date start = new Date(System.currentTimeMillis());
			
			try {
				
				start = formatter.parse(dateParts[0]+" "+dateParts[1]);
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
			int hour = Integer.parseInt(inputParts[3].split(":")[0]);
			
			int minute = Integer.parseInt(inputParts[3].split(":")[1]);
			
			int duration = hour* 60 + minute;
			
			Calendar calendar = Calendar.getInstance();
			
		    calendar.setTime(start);
		    
		    calendar.add(Calendar.MINUTE, duration);
		    
		    Date finish = calendar.getTime();
		    
			Flight flight = new Flight(inputParts[0],inputParts[1],start,finish,Integer.parseInt(inputParts[4]),0);
				
			flightList.add(flight);
			
		}
		
	}
	
	public void commmand(String[] inputList) {
		
		Graph graph = new Graph(airportMap,cityMap,flightList);
		
		graph.fillGraph();
		
		for(String command: inputList) {
			
			graph.controller(command);
			
		}
		
		Writer.writer(graph.getArticle());
	}

}
