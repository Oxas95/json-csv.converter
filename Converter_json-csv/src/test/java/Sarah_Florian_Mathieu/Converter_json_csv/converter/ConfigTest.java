package Sarah_Florian_Mathieu.Converter_json_csv.converter;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigFileException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.JsonManager;


public class ConfigTest {
private File f;
	
	
	@Before 
	 public void setUp() throws IOException {
	    f = new File("Test.csv");
	   
	  }

	 @After
	  public void tearDown() throws IOException {
		f.delete();
	    
	  }
	// Test avec un fichier 
	@Test
	public void TestConstructeur_Config() throws IOException, NullPointerException, IllegalArgumentException, CsvException, ConfigFileException {
			
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("Test.csv");
		
		ConfigManager cm = new ConfigManager(csv.getArrayCopy(),csv.getWidth(),csv.getHeight());
		
			
	}

	@Test(expected = ConfigFileException.class)
	public void TestConstructeur_Null_Config() throws IOException, NullPointerException, IllegalArgumentException, CsvException, ConfigFileException {
			
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("Test.csv");
		
		ConfigManager cm = new ConfigManager(null,csv.getWidth(),csv.getHeight());
		
			
	}
}