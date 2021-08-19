import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    /**
     * Propagated {@link IOException} here
     * {@link #readFile} and {@link #writeOutput} methods should be called here
     * A {@link Scheduler} instance must be instantiated here
     */
    public static void main(String[] args) throws IOException {
    	
    	Assignment[] array = readFile(args[0]);
    	
    	Scheduler scheduler = new Scheduler(array);
    	
    	writeOutput("solution_dynamic.json",scheduler.scheduleDynamic());
    	
    	writeOutput("solution_greedy.json",scheduler.scheduleGreedy());
    	
    }

    /**
     * @param filename json filename to read
     * @return Returns a list of {@link Assignment}s obtained by reading the given json file
     *  @throws FileNotFoundException If the given file does not exist
     */
    private static Assignment[] readFile(String filename) throws FileNotFoundException {
    	
    	Gson gson = new Gson();
    	
    	FileReader content = new FileReader(filename);
    	
    	Assignment[] array = gson.fromJson(content, Assignment[].class); 
    	
        return array;
    }

    /**
     * @param filename  json filename to write
     * @param arrayList a list of {@link Assignment}s to write into the file
     * @throws IOException If something goes wrong with file creation
     */
    private static void writeOutput(String filename, ArrayList<Assignment> arrayList) throws IOException {
    	
    	Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    	
    	String json = gson.toJson(arrayList);
    	
    	FileWriter fileWriter = new FileWriter(filename);
    	
    	fileWriter.write(json);
    	
    	fileWriter.close();
    }
}
