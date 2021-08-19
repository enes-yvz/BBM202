import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	
	public static void writer(String article) {
		
		FileWriter fileWriter = null;
		
		try {
			fileWriter = new FileWriter("output.txt");
			fileWriter.write(article);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			
			if(fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
