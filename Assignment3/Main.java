
public class Main {

	public static void main(String[] args) {
		
		Splitter splitter = new Splitter();
		
		String[] inputList = ReadFromFile.readFile(args[0]);
		
		splitter.airport(inputList);
		
		inputList = ReadFromFile.readFile(args[1]);
		
		splitter.flight(inputList);
		
		inputList = ReadFromFile.readFile(args[2]);
		
		splitter.commmand(inputList);
		
	}

}
