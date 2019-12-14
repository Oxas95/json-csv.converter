package Sarah_Florian_Mathieu.Converter_json_csv.converter;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JsonTest {
	private File f;	
	
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
		public void TestConstructeur1Json() throws IOException {
				
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f));
			    fw.write("{\n\n}");
			  fw.close();

			JsonManager json = new JsonManager("CsvTest.csv");
			
			System.out.print("Test1:\n");
			System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());

		}
		
		@Test(expected=JSONException.class)
		public void TestConstructeur2Json() throws IOException,JSONException{
				
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f));
			    fw.write("");
			  fw.close();

			JsonManager json = new JsonManager("CsvTest.csv");
			
			System.out.print("Test2:\n");
			System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());

		}
		
		@Test
		public void TestConstructeur3CSV() throws IOException {
			
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f));
			    fw.write("{\n \"marque\":{ \n"
			    		+ "\"Andros\",\n \"La laitière\" }"

						+ "\n \"nom\":{\n" 
						+ "\"yaourt au citron\" ,\n \"yaourt à la vanille\"\n }"
						+ "\n \"quantité\":{\n" 
						+ "\"2\",\n \"5\"\n }"
						+ "\n \"produit\":{\n" 
						+ "\"yaourt\",\n \"yaourt\"\n }"
						+ "\n \"prix\":{\n" 
						+ "\"1.50\",\n \"2.50\n }"
						+"\n}");
						
						
			  fw.close();
			
				JsonManager json = new JsonManager("CsvTest.csv");
				
				System.out.print("Test3:\n");
				System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());

		}

	}

