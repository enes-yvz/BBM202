import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Graph {
	
	private HashMap<String, Integer> airportMap = new HashMap<String, Integer>();  
	
	private HashMap<String, String> cityMap = new HashMap <String, String>(); 
	
	private HashMap<String, Boolean> isVisited = new HashMap <String, Boolean>(); 
	
	private ArrayList<Flight> flightList = new ArrayList<Flight>(); 
	
	private ArrayList<Flight>[] graph;
	
	private ArrayList<ArrayList<Flight>> myList;
	
	private ArrayList<Long> timeList;
	
	private ArrayList<Integer> priceList ;
	
	private boolean[] indexControl;
	
	int[][] matrix;
	
	private ArrayList<ArrayList<Integer>> second;
	
	private ArrayList<ArrayList<Integer>> third;
	
	String article;

	@SuppressWarnings("unchecked")
	public Graph(HashMap<String, Integer> airportMap,HashMap<String, String> cityMap ,ArrayList<Flight> flightList) {
		
		this.airportMap = airportMap;
		this.cityMap = cityMap;
		this.flightList = flightList;
		
		graph = new ArrayList[airportMap.size()]; 
		
		second = new ArrayList<ArrayList<Integer>>();
		
		third = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < airportMap.size(); i++) {
				
				graph[i] = new ArrayList<Flight>();
				
				ArrayList<Integer> tmpF = new ArrayList<Integer>() ;
				
				ArrayList<Integer> tmpS = new ArrayList<Integer>() ;
				
				second.add(tmpF);
				
				third.add(tmpS);
				
		}
		
		matrix = new int[airportMap.size()][airportMap.size()];
		
		for (int i = 0; i < airportMap.size(); i++) {
			
			Arrays.fill(matrix[i], Integer.MAX_VALUE);
			
		}
		
		for (String name : cityMap.keySet()) {
			
			isVisited.put(cityMap.get(name),false);
		}
		
		article = "";
		
		    
	}
	
	public void fillGraph() {
		
		for (int i=0;i<flightList.size();i++) {
			
			Flight edge = flightList.get(i);
			
			String dept = edge.getDirection().split("->")[0];
			
			int deptIndex = airportMap.get(dept);
			
			String arr = edge.getDirection().split("->")[1];
			
			int arrIndex = airportMap.get(arr);
			
			edge.setIndex(arrIndex);
			
			graph[deptIndex].add(edge);
			
			second.get(arrIndex).add(deptIndex);
			
			third.get(deptIndex).add(arrIndex);
			
			if (matrix[deptIndex][arrIndex]==Integer.MAX_VALUE) {
				
				matrix[deptIndex][arrIndex] = edge.getPrice();
				
			}
			
			else {
				
				if (matrix[deptIndex][arrIndex] < edge.getPrice() ) {
					
					matrix[deptIndex][arrIndex] = edge.getPrice();
					
				}
			}
			
		}
	}
	
	public void controller(String command) {
		
		String[] commandList = command.split("\t");
		
		article += "command : " + command + "\n";
		
		if (1<commandList.length) {
			
			ArrayList<Flight> path = new ArrayList<Flight>();
			
			ArrayList<Integer> arrAirportCodes = new ArrayList<Integer>();
			
			ArrayList<Integer> deptAirportCodes = new ArrayList<Integer>();
			
			myList = new ArrayList<ArrayList<Flight>>();
			
			timeList = new ArrayList<Long>();
			
			priceList = new ArrayList<Integer>();
			
			boolean[] marked = new boolean[airportMap.size()];
			
			for (String name : cityMap.keySet()) {
				
				if (cityMap.get(name).equals(commandList[1].split("->")[0])) {
					
					arrAirportCodes.add(airportMap.get(name));
					
				}
				
				else if (cityMap.get(name).equals(commandList[1].split("->")[1])) {
					
					deptAirportCodes.add(airportMap.get(name));
					
				}
			}
			
			for (int i:arrAirportCodes) {
				
				for (int j:deptAirportCodes) {
					
					listAll(i,j,marked,path);
					
				}
				
			}
			
			indexControl = new boolean[myList.size()];
			
			Arrays.fill(indexControl, false);
			
		}
		
		
		if (commandList[0].equals("listAll")) {
			
			Arrays.fill(indexControl, true);
			
			if(printer(indexControl)) {
				
				article += "No suitable flight plan is found" + "\n";
			}
			
		}
		
		else if(commandList[0].equals("listProper")) {
			if(printer(listProper())) {
				
				article += "No suitable flight plan is found" + "\n";
			}
			
		}
		
		else if (commandList[0].equals("listCheapest")) {
			
			ArrayList<Integer> tmpPriceList = new ArrayList<Integer> (priceList);
			
			Collections.sort(tmpPriceList);
			
			int tmp = tmpPriceList.get(0);
			
			for (int i=0;i<myList.size();i++) {
					
				if (priceList.get(i)==tmp) {
					
					indexControl[i] = true;
					
				}
				
			}
			
			if(printer(indexControl)) {
				
				article += "No suitable flight plan is found" + "\n";
			}
		}
		
		else if (commandList[0].equals("listQuickest")) {
			
			ArrayList<Long> tmpTimeList = new ArrayList<Long> (timeList);
			
			Collections.sort(tmpTimeList);
			
			long tmp = tmpTimeList.get(0);
			
			for (int i=0;i<myList.size();i++) {
					
				if (timeList.get(i)==tmp) {
					
					indexControl[i] = true;
					
				}
				
			}
			
			if(printer(indexControl)) {
				
				article += "No suitable flight plan is found" + "\n";
			}
		}
		
		else if (commandList[0].equals("listCheaper")) {
			
			listProper();
			
			int limit = Integer.valueOf(commandList[3]);
			
			for (int i=0;i<myList.size();i++) {
					
				if (priceList.get(i)>=limit && indexControl[i] == true) {
					
					indexControl[i] = false;
				}
				
			}
			
			if(printer(indexControl)) {
				
				article += "No suitable flight plan is found" + "\n";
			}
		}
		
		else if (commandList[0].equals("listQuicker")) {
			
			listProper();
			
			String[] dateParts = commandList[3].split(" ");
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			Date limit = new Date(System.currentTimeMillis());
			
			try {
				
				limit = formatter.parse(dateParts[0]+" "+dateParts[1]);
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
			for (int i=0;i<myList.size();i++) {
				
				if (0<myList.get(i).size() && !myList.get(i).get(myList.get(i).size()-1).getFinish().before(limit) && indexControl[i] == true) {
					
					indexControl[i] = false;
					
				}
				
			}
			
			if(printer(indexControl)) {
				
				article += "No suitable flight plan is found" + "\n";
			}
			
		}
		
		else if (commandList[0].equals("listExcluding")) {
			
			listProper();
			
			String airlaneCode = commandList[3];
			
			for (int i=0;i<myList.size();i++) {
				
				boolean control = true;
				
				for (Flight f: myList.get(i)) {
					
					if (f.getCode().contains(airlaneCode) && indexControl[i] == true) {
						
						control = false;
					}
					
				}
				
				if (control==false) {
					
					indexControl[i] = false;
				}
			}
			
			if(printer(indexControl)) {
				
				article += "No suitable flight plan is found" + "\n";
			}
			
		}
		
		else if (commandList[0].equals("listOnlyFrom")) {
			
			listProper();
			
			String airlaneCode = commandList[3];
			
			for (int i=0;i<myList.size();i++) {
				
				boolean control = true;
				
				for (Flight f: myList.get(i)) {
					
					if (!f.getCode().contains(airlaneCode) && indexControl[i] == true) {
						
						control = false;
					}
					
				}
				
				if (control==false) {
					
					indexControl[i] = false;
				}
			}
			
			if(printer(indexControl)) {
				
				article += "No suitable flight plan is found" + "\n";
			}
			
		}
		
		else if (commandList[0].equals("diameterOfGraph")) {
			
			int diameter=-1;
			
	        for (int k=0;k<matrix.length;k++){
	        	
	            for (int i=0;i<matrix.length;i++){
	                	
                    for (int j=0;j<matrix.length;j++){
                    	
                        if (matrix[i][k]!=Integer.MAX_VALUE && matrix[k][j]!=Integer.MAX_VALUE && matrix[i][k]+matrix[k][j]<matrix[i][j]){
                        	
                            matrix[i][j]= matrix[i][k]+matrix[k][j];
                        }
                    }
                }
	        }
	        
	        for (int i=0;i< matrix.length;i++){
	        	
	            for (int j=0;j< matrix.length;j++){
	            	
	                if (diameter < matrix[i][j] && matrix[i][j] != Integer.MAX_VALUE){
	                	
	                    diameter=matrix[i][j];
	                }
	            }
	        }
	        
	        article += "The diameter of graph : " + diameter + "\n";
		}
		
		else if (commandList[0].equals("pageRankOfNodes")) {
			
			
			/* 
			
			detect all of the sink nodes and connect them to the remain graph 
			
			third is adjacency list of outgoing edges for each page
			
			second is adjacency list of incoming edges for each page
			
			*/
			
			for (int i=0;i<third.size();i++) {
				
				if (third.get(i).size()==0) {
					
					for (int f=0;f<second.size();f++) {
						
						third.get(i).add(f);
							
						second.get(f).add(i);
						
					}
					
				}
				
			}
			
			double[] prev = new double[second.size()]; //stores previous ranks
			
			double[] curr = new double[second.size()]; //stores current ranks
			
			Arrays.fill(curr, 1 / (double)second.size()); // initialize all ranks to the 1 / page count
			
			for (int k=0;k<100;k++) { // iterate 100 times
				
				prev = curr.clone();  // store current ranks in previous array
				
				for (int i=0;i<second.size();i++) { 
					
					double tmp = 0;
					
					for (int n:second.get(i)) {  // for each incoming airport for all of the airports
							
						tmp += (double) prev[n] / third.get(n).size();  // add (incoming links rank / outgoing edge count of incoming page) to the new rank 
						
					}
					
					curr[i] = 0.15 / second.size() + 0.85 * tmp ; // calculate the new pagerank and store them in current array
					 
				}
				
			}
			
			for (String name : airportMap.keySet()) {
				
				article += name + ": " + String.format(Locale.US, "%.3f", curr[airportMap.get(name)]) + "\n";
			}
		}
		
		article += "\n";
		
		article += "\n";
		
	}
	
	public void listAll(int dept,int arr,boolean[] marked,ArrayList<Flight> path) {
		
		if (dept==arr) {
			
			HashMap<String, Boolean> isVisitedTmp = new HashMap <String, Boolean>(isVisited);
			
			ArrayList<Flight> tmp = new ArrayList<Flight>();
			
			int price = 0;
            
			for (int i=0;i<path.size();i++) {
				
				isVisitedTmp.put(cityMap.get(path.get(i).getDirection().split("->")[0]),true);
				
				if (isVisitedTmp.get(cityMap.get(path.get(i).getDirection().split("->")[1]))) {
        			
        			isVisitedTmp = new HashMap <String, Boolean>(isVisited);
        			
        			tmp.clear();
                	
                	return;
        			
        		}
				
				tmp.add(path.get(i));
				
				price += path.get(i).getPrice();
				
			}
			
			long diffInMillies = tmp.get(path.size()-1).getFinish().getTime() - tmp.get(0).getStart().getTime();
			
			long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
			
			myList.add(tmp);
			
			timeList.add(diff);
			
			priceList.add(price);
			
			price = 0;
			
            return;
          
		}
		
		marked[dept] = true;
		
		for (Flight f: graph[dept]) {
			
            if (!marked[f.getIndex()]) {
            		
            		path.add(f);
            		
            		if ((1<path.size()) && (f.getStart().before(path.get(path.size()-2).getFinish()) || 
            			
            			cityMap.get(f.getDirection().split("->")[0]).equals(cityMap.get(f.getDirection().split("->")[1])))) {
            			
            			path.remove(f);
                    	
                    	continue;
                    }
                    
                    listAll(f.getIndex(),arr,marked,path);
                    
                    path.remove(f);
            }
            
        }
		
		marked[dept] = false;
		
	}
	
	public boolean[] listProper() {
		
		for (int i=0;i<myList.size();i++) {
			
			boolean control = true;
			
			for (int j=0;j<myList.size();j++) {
				
				if (priceList.get(i)>priceList.get(j) && timeList.get(i)>timeList.get(j)) {
					
					control = false;
				}
				
			}
			
			indexControl[i] = control;
			
		}
		
		return indexControl;
		
	}

	public boolean printer(boolean[] indexControl) {
		
		boolean control = true;
		
		for (int a=0;a<myList.size();a++) { 
			
			ArrayList <Flight>  k = myList.get(a);
			
			if (indexControl.length !=0 && indexControl[a]) {
				
				for (int i=0;i<k.size();i++) {
					
					Flight f = k.get(i);
					
					if (i==k.size()-1) {
						
						article += f.getCode() +"\t" + f.getDirection() + "\t";
					}
					
					else {
						
						article += f.getCode() +"\t" + f.getDirection() + "||";
						
					}
					
				}
				
				if (timeList.get(a)/60<10) {
				
					 article += "0" + timeList.get(a)/60;
					
				}
				
				else {
					
					article  += timeList.get(a)/60 ;
					
				}
				
				article += ":";
				
				if (timeList.get(a)%60<10) {
					
					article += "0" + timeList.get(a)%60;
					
				}
				
				else {
					
					article  += timeList.get(a)%60 ;
					
				}
				
				article += "/"+ priceList.get(a);
				
				if (k.size()!=0) {
					
					control = false;
					
					article += "\n";
					
				}
			}
			
		}
		
		return control;
		
	}

	public String getArticle() {
		
		return article;
		
	}
}
