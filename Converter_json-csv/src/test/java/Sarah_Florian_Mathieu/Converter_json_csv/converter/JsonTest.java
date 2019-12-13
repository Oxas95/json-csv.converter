package Sarah_Florian_Mathieu.Converter_json_csv.converter;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JsonTest {
	private File f;
	private JsonManager json;
	
	
	@Before 
	 public void initialiser() throws IOException {
	    f = new File("CsvTest.csv");
	   
	  }

	 @After
	  public void nettoyer() throws IOException {
		f.delete();
	    
	  }
	 
	 // Test avec un fichier vide
	@Test
	public void TestConstructeur1JSON() throws IOException {
			
		json = new JsonManager();
		
	}
}
