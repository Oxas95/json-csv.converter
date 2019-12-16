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

import com.ibm.icu.text.DisplayContext.Type;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigFileException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.JsonManager;


public class ConverterTest {
private File f;
private File f2;
	
	@Before 
	 public void setUp() throws IOException {
	    f = new File("TestConvert.csv");
	    f2 = new File("TestConvert.json");
	   
	  }

	 @After
	  public void tearDown() throws IOException {
		f.delete();
		f2.delete();
	    
	  }
	// Test avec un fichier 
	@Test
	public void TestConstructeur_Csv_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException {
			
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		  Converter conv = new Converter("TestConvert.csv"); 
		
			
	}
	@Test
	public void TestConstructeur_Json_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException {
			
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f2));
		    fw.write("{\n" + 
		    		"    \"test\": {\"id\": \"file\"},\n" + 
		    		"    \"menu\": {\n" + 
		    		"        \"popup\": {\"menuitem\": [\n" + 
		    		"            [\n" + 
		    		"                [1,2,3],\n" + 
		    		"                [1,2],\n" + 
		    		"                3\n" + 
		    		"            ],\n" + 
		    		"            {\n" + 
		    		"                \"onclick\": [\n" + 
		    		"                    \"CreateNewDoc()\",\n" + 
		    		"                    \"OpenDoc()\",\n" + 
		    		"                    \"CloseDoc()\"\n" + 
		    		"                ],\n" + 
		    		"                \"value\": [\n" + 
		    		"                    \"New\",\n" + 
		    		"                    \"New\",\n" + 
		    		"                    \"Close\"\n" + 
		    		"                ]\n" + 
		    		"            }\n" + 
		    		"        ]},\n" + 
		    		"        \"id\": \"file\",\n" + 
		    		"        \"value\": \"File\"\n" + 
		    		"    }\n" + 
		    		"}");
		  fw.close();
		  Converter conv = new Converter("TestConvert.json"); 
		
			
	}
	//Test avec un fichier ni csv ni json
	@Test(expected=FileFormatException.class)
	public void TestConstructeur_txt_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException {
		File f3;
		f3 = new File ("TestConvert.txt");
		
		  Converter conv = new Converter("TestConvert.txt"); 
		f3.deleteOnExit();
			
	}
	// Test avec un fichier inexistant
		
		@Test(expected = NoSuchFileException.class)
		public void TestConstructeur_Inexistant_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException {
				
			
			  Converter conv = new Converter("TestConvertir.csv"); 
			
				
		}
		@Test
		public void TestconfigureData_Csv_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException, ConfigFileException {
				
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f));
			    fw.write("marque,nom,quantité,produit,prix\n"

						+ "Andros,yaourt au citron,fraise,2,yaourt\n"

						+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
			  fw.close();
			  Converter conv = new Converter("TestConvert.csv"); 
			  conv.configureData();
			
				
		}
		@Test
		public void TestconfigureData_Json_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException, ConfigFileException {
				
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f2));
			    fw.write("{\n" + 
			    		"    \"test\": {\"id\": \"file\"},\n" + 
			    		"    \"menu\": {\n" + 
			    		"        \"popup\": {\"menuitem\": [\n" + 
			    		"            [\n" + 
			    		"                [1,2,3],\n" + 
			    		"                [1,2],\n" + 
			    		"                3\n" + 
			    		"            ],\n" + 
			    		"            {\n" + 
			    		"                \"onclick\": [\n" + 
			    		"                    \"CreateNewDoc()\",\n" + 
			    		"                    \"OpenDoc()\",\n" + 
			    		"                    \"CloseDoc()\"\n" + 
			    		"                ],\n" + 
			    		"                \"value\": [\n" + 
			    		"                    \"New\",\n" + 
			    		"                    \"New\",\n" + 
			    		"                    \"Close\"\n" + 
			    		"                ]\n" + 
			    		"            }\n" + 
			    		"        ]},\n" + 
			    		"        \"id\": \"file\",\n" + 
			    		"        \"value\": \"File\"\n" + 
			    		"    }\n" + 
			    		"}");
			  fw.close();
			  Converter conv = new Converter("TestConvert.json"); 
			  conv.configureData();
				
		}
		//save as csv from csv
		@Test
		public void TestsaveAs_Csv_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException{
			File test_csv;
			test_csv = new File("Test_save.csv");
			TypeFile tf;
			tf = TypeFile.CSV;
			
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f));
			    fw.write("marque,nom,quantité,produit,prix\n"

						+ "Andros,yaourt au citron,fraise,2,yaourt\n"

						+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
			  fw.close();
			  Converter conv = new Converter("TestConvert.csv"); 
			  conv.saveAs("Test_save.csv", tf);
			  test_csv.delete();
			
				
		}
		//save as json from json
		@Test
		public void TestsaveAs_Json_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException, ConfigFileException {
			File test_json;
			test_json = new File("Test_save.json");
			TypeFile tf;
			tf = TypeFile.JSON;
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f2));
			    fw.write("{\n" + 
			    		"    \"test\": {\"id\": \"file\"},\n" + 
			    		"    \"menu\": {\n" + 
			    		"        \"popup\": {\"menuitem\": [\n" + 
			    		"            [\n" + 
			    		"                [1,2,3],\n" + 
			    		"                [1,2],\n" + 
			    		"                3\n" + 
			    		"            ],\n" + 
			    		"            {\n" + 
			    		"                \"onclick\": [\n" + 
			    		"                    \"CreateNewDoc()\",\n" + 
			    		"                    \"OpenDoc()\",\n" + 
			    		"                    \"CloseDoc()\"\n" + 
			    		"                ],\n" + 
			    		"                \"value\": [\n" + 
			    		"                    \"New\",\n" + 
			    		"                    \"New\",\n" + 
			    		"                    \"Close\"\n" + 
			    		"                ]\n" + 
			    		"            }\n" + 
			    		"        ]},\n" + 
			    		"        \"id\": \"file\",\n" + 
			    		"        \"value\": \"File\"\n" + 
			    		"    }\n" + 
			    		"}");
			  fw.close();
			  Converter conv = new Converter("TestConvert.json"); 
			  conv.saveAs("Test_save.json", tf);
			  test_json.delete();
				
		}
		//save as json from csv
		@Test
		public void TestsaveAs_Csv_Json_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException{
			File test_json;
			test_json = new File("Test_save.json");
			TypeFile tf;
			tf = TypeFile.JSON;
			
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f));
			    fw.write("marque,nom,quantité,produit,prix\n"

						+ "Andros,yaourt au citron,fraise,2,yaourt\n"

						+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
			  fw.close();
			  Converter conv = new Converter("TestConvert.csv"); 
			  conv.saveAs("Test_save.json", tf);
			  test_json.delete();
			
				
		}
		//save from json as csv
		@Test
		public void TestsaveAs_Json_Csv_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException, ConfigFileException {
			File test_csv;
			test_csv= new File("Test_save.csv");
			TypeFile tf;
			tf = TypeFile.CSV;
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f2));
			    fw.write("{\n" + 
			    		"    \"test\": {\"id\": \"file\"},\n" + 
			    		"    \"menu\": {\n" + 
			    		"        \"popup\": {\"menuitem\": [\n" + 
			    		"            [\n" + 
			    		"                [1,2,3],\n" + 
			    		"                [1,2],\n" + 
			    		"                3\n" + 
			    		"            ],\n" + 
			    		"            {\n" + 
			    		"                \"onclick\": [\n" + 
			    		"                    \"CreateNewDoc()\",\n" + 
			    		"                    \"OpenDoc()\",\n" + 
			    		"                    \"CloseDoc()\"\n" + 
			    		"                ],\n" + 
			    		"                \"value\": [\n" + 
			    		"                    \"New\",\n" + 
			    		"                    \"New\",\n" + 
			    		"                    \"Close\"\n" + 
			    		"                ]\n" + 
			    		"            }\n" + 
			    		"        ]},\n" + 
			    		"        \"id\": \"file\",\n" + 
			    		"        \"value\": \"File\"\n" + 
			    		"    }\n" + 
			    		"}");
			  fw.close();
			  Converter conv = new Converter("TestConvert.json"); 
			  conv.saveAs("Test_save.csv", tf);
			  test_csv.delete();
				
		}
		
		@Test(expected=NullPointerException.class)
		public void TestsaveAs_erreur_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException, ConfigFileException {
			
			TypeFile tf;
			tf = TypeFile.CSV;
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f2));
			    fw.write("{\n" + 
			    		"    \"test\": {\"id\": \"file\"},\n" + 
			    		"    \"menu\": {\n" + 
			    		"        \"popup\": {\"menuitem\": [\n" + 
			    		"            [\n" + 
			    		"                [1,2,3],\n" + 
			    		"                [1,2],\n" + 
			    		"                3\n" + 
			    		"            ],\n" + 
			    		"            {\n" + 
			    		"                \"onclick\": [\n" + 
			    		"                    \"CreateNewDoc()\",\n" + 
			    		"                    \"OpenDoc()\",\n" + 
			    		"                    \"CloseDoc()\"\n" + 
			    		"                ],\n" + 
			    		"                \"value\": [\n" + 
			    		"                    \"New\",\n" + 
			    		"                    \"New\",\n" + 
			    		"                    \"Close\"\n" + 
			    		"                ]\n" + 
			    		"            }\n" + 
			    		"        ]},\n" + 
			    		"        \"id\": \"file\",\n" + 
			    		"        \"value\": \"File\"\n" + 
			    		"    }\n" + 
			    		"}");
			  fw.close();
			  Converter conv = new Converter("TestConvert.json"); 
			  conv.saveAs(null, tf);
			  
				
		}
		// Attention ici il devrai retourner une erreur
		@Test
		public void TestsaveAs_null_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException{
			File test_json;
			test_json = new File("Test_save.json");
			
			
			OutputStreamWriter fw;
			 fw = new OutputStreamWriter(new FileOutputStream(f));
			    fw.write("marque,nom,quantité,produit,prix\n"

						+ "Andros,yaourt au citron,fraise,2,yaourt\n"

						+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
			  fw.close();
			  Converter conv = new Converter("TestConvert.csv"); 
			  conv.saveAs("Test_save.json", null);
			  test_json.delete();
			
				
		}
		
		
}