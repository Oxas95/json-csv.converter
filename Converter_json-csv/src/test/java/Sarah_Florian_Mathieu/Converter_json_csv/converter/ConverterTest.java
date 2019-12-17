package Sarah_Florian_Mathieu.Converter_json_csv.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.NoSuchFileException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigFileException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvException;



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
	 /**
	  * Test le constructeur avec un csv
	  * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	  * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	  * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	  * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	  * @throws FileFormatException si le format n'est ni un csv ni un json
	  */
	// Test avec un fichier 
	@Test
	public void TestConstructeur_Csv_Convert() throws IOException, NullPointerException, IllegalArgumentException, CsvException, FileFormatException {
			
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		  
		  new Converter("TestConvert.csv"); 

	}
	
	 /**
	  * Test le constructeur avec un json
	  * @throws IOException si la lecture ou l'écriture échoue
	  * @throws NullPointerException si le chemin d'accès est null
	  * @throws IllegalArgumentException si l'argument donnée est invalide
	  * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	  * @throws FileFormatException si le format n'est ni un csv ni un json
	  */
	@Test
	public void TestConstructeur_Json_Convert() throws IOException, NullPointerException, IllegalArgumentException, CsvException, FileFormatException{
			
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
		  new Converter("TestConvert.json"); 
		
			
	}
	
	 /**
	  * Test le constructeur avec un fichier txt
	  * exception attendue : FileFormatException car le fichier n'est pas un json ni un csv
	  * @throws NullPointerException si le chemin d'accès est null
	  * @throws IllegalArgumentException si l'argument donnée est invalide
	  * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	  * @throws FileFormatException si le format n'est ni un csv ni un json
	  */
	@Test(expected=FileFormatException.class)
	public void TestConstructeur_txt_Convert() throws NullPointerException, IllegalArgumentException, IOException, CsvException, FileFormatException{
		File f3;
		f3 = new File ("TestConvert.txt");
		
		new Converter("TestConvert.txt"); 
		
		f3.delete();
			
	}

	/**
	  * Test le constructeur avec un fichier inexistant
	  * exception attendue : NoSuchFileException car le fichier n'existe pas
	  * @throws NullPointerException si le chemin d'accès est null
	  * @throws IllegalArgumentException si l'argument donnée est invalide
	  * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	  * @throws FileFormatException si le format n'est ni un csv ni un json
	  */
	@Test(expected = NoSuchFileException.class)
	public void TestConstructeur_Inexistant_Convert() throws NullPointerException, IllegalArgumentException, IOException, CsvException, FileFormatException {

		  new Converter("TestConvertir.csv"); 
		
	}
	
	/**
	 * Test la fonction configureDAta pour le csv
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 * @throws NullPointerException si le chemin d'accès est null
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 * @throws FileFormatException si le format n'est ni un csv ni un json
	 * @throws ConfigFileException si la configuration n'est pas possible
	 */
	@Test
	public void TestconfigureData_Csv_Convert() throws IOException, NullPointerException, IllegalArgumentException, CsvException, FileFormatException, ConfigFileException {
			
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		  
		  Converter conv = new Converter("TestConvert.csv"); 
		  conv.configureData();
			
	}
	
	/**
	 * Test la fonction configureData pour Json
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 * @throws NullPointerException si le chemin d'accès est null
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 * @throws FileFormatException si le format n'est ni un csv ni un json
	 * @throws ConfigFileException si la configuration n'est pas possible
	 */
	@Test
	public void TestconfigureData_Json_Convert() throws IOException, NullPointerException, IllegalArgumentException, CsvException, FileFormatException, ConfigFileException {
			
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
	
	/**
	 * Test la fonction csaveAs pour un fichier csv
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 * @throws NullPointerException si le chemin d'accès est null
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 * @throws FileFormatException si le format n'est ni un csv ni un json
	 * @throws ConfigFileException si la configuration n'est pas possible
	 */
	@Test
	public void TestsaveAs_Csv_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException, ConfigFileException{
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
		  conv.configureData();
		  conv.saveAs("Test_save.csv", tf);
		  test_csv.delete();
		
			
	}
	
	/**
	 * Test la fonction saveAs pour Json
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 * @throws NullPointerException si le chemin d'accès est null
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 * @throws FileFormatException si le format n'est ni un csv ni un json
	 * @throws ConfigFileException si la configuration n'est pas possible
	 */
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
		  conv.configureData();
		  conv.saveAs("Test_save.json", tf);
		  test_json.delete();
			
	}
		//save as json from csv
	/**
	 * Test la fonction csaveAs json a partir d'un fichier csv
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 * @throws NullPointerException si le chemin d'accès est null
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 * @throws FileFormatException si le format n'est ni un csv ni un json
	 * @throws ConfigFileException si la configuration n'est pas possible
	 */
	@Test
	public void TestsaveAs_Csv_Json_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException, ConfigFileException{
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
		  conv.configureData();
		  conv.saveAs("Test_save.json", tf);
		  test_json.delete();
		
			
	}
	
	/**
	 * Test la fonction saveAs json a partir d'un fichier json
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 * @throws NullPointerException si le chemin d'accès est null
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 * @throws FileFormatException si le format n'est ni un csv ni un json
	 * @throws ConfigFileException si la configuration n'est pas possible
	 */
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
		  conv.configureData();
		  conv.saveAs("Test_save.csv", tf);
		  
		  test_csv.delete();
			
	}
		
	/**
	 * Test la fonction saveAS a partir d'un fichier null
	 * exception attendue : NullPointeurException car le fichier donner en entrée est null
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 * @throws NullPointerException si le chemin d'accès est null
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 * @throws FileFormatException si le format n'est ni un csv ni un json
	 * @throws ConfigFileException si la configuration n'est pas possible
	 */
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
		  conv.configureData();
		  conv.saveAs(null, tf);
		  
			
	}
	
	/**
	 * Test la fonction saveAS avec un type null
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 * @throws NullPointerException si le chemin d'accès est null
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 * @throws FileFormatException si le format n'est ni un csv ni un json
	 * @throws ConfigFileException si la configuration n'est pas possible
	 */
	// Attention ici il devrai retourner une erreur
	@Test
	public void TestsaveAs_null_Convert() throws IOException, NullPointerException, FileFormatException, IllegalArgumentException, CsvException, ConfigFileException{
		File test_json;
		test_json = new File("Test_save.json");
		
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		  Converter conv = new Converter("TestConvert.csv"); 
		  conv.configureData();
		  conv.saveAs("Test_save.json", null);
		  test_json.delete();
		
			
	}
		
		
}