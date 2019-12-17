package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

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

import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.JsonManager;

public class JsonTest {
	
	private File f;
	
	@Before 
	 public void setUp() throws IOException {
	    f = new File("JsonTest.json");
	   
	  }

	 @After
	  public void tearDown() throws IOException {
		f.delete();
	    
	  }
	 
	 /**
	  * Test avec un fichier null
	  * exception attendue : NullPointeurException au moment de la lecture du fichier retourne l'exception car le fichier est null
	  * @throws FileNotFoundException si le fichier n'existe pas
	  * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	  */
	@Test(expected=NullPointerException.class)
	public void TestConstructeur_Fichiernull_JSON() throws FileNotFoundException, NullPointerException  {
			
		new JsonManager (null);
			
	}
	
	/**
	 * Test avec un fichier txt
	 * exception attendue : IllegalArgumentException au moment de la lecture du fichier retourne l'exception car le fichier n'est pas un json
	 * @throws FileNotFoundException si le fichier n'existe pas
	 * @throws NullPointerException si le chemin d'accès est null pour JsonManager
	 */
	@Test(expected=IllegalArgumentException.class)
	public void TestConstructeur_FichierNonJSON_JSON() throws FileNotFoundException, NullPointerException {
		File t;
		t = new File("JsonTest2.txt");
		new JsonManager ("JsonTest2.txt");
		t.deleteOnExit();
			
	}
	
	/**
	 * Test avec un fichier inexistant
	 * exception attendue : FileNotFoundException au moment de la lecture de fichier
	 * @throws FileNotFoundException si le fichier n'existe pas
	 * @throws NullPointerException si le chemin d'accès est null pour JsonManager
	 */
	@Test(expected=FileNotFoundException.class)
	public void TestConstructeur_FichierInexistant_JSON() throws FileNotFoundException, NullPointerException{
			
		new JsonManager ("Inexist_fichier.json");
			
	}
	
	
	 /**
	  * Test avec un fichier vide
	  * @throws IOException si la lecture ou l'écriture échoue
	  */
	@Test
	public void TestConstructeur_Fichiervide_JSON() throws IOException  {
			
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("{\n\n}");
		  fw.close();
		  
		new JsonManager ("JsonTest.json");

	}
	
	/**
	 * Test si le constructeur fonctionne avec un fichier non vide
	 * @throws IOException si la lecture ou l'écriture échoue
	 */
	@Test
	public void TestConstructeur_FichierNonVide_JSON() throws IOException {

		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		
		  new JsonManager ("JsonTest.json");

	}
	
	/**
	 * Test si le constructeur fonctionne avec un fichier avec une erreur
	 * erreur attendue : JSONException detecte une erreur car le fichier contient une guillement de trop
	 * @throws IOException si la lecture ou l'écriture échoue
	 */
	@Test(expected=JSONException.class)
	public void TestConstructeur_Fichiererreur_JSON() throws IOException{

		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("{\n" + 
		    		"    \"test\": {\"id\": \"file\"},\n" + 
		    		"    \"menu\": {\n" + 
		    		"        \"popup\"\": {\"menuitem\": [\n" + 
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
		
		  new JsonManager ("JsonTest.json");
		
	}
	
	/**
	 * Test le get
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 */
	@Test
	public void TestgetCSV() throws IOException{
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		
		JsonManager json = new JsonManager ("JsonTest.json");
	
		assertTrue(json.get(1,1).equalsIgnoreCase("File"));

	}
	
	/**
	 * Test le get avec une case inexistante
	 * excception attendue : ArrayIndexOutOfBoundsException car la case n'existe pas
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 */
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void Testget_exceptionCaseInexistanteJSON() throws IOException{
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		
		JsonManager json = new JsonManager ("JsonTest.json");
		 
		json.get(100,2);
	
	}
	
	/**
	 * Test le set
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 */
	@Test
	public void TestsetJSON() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		
		JsonManager json = new JsonManager ("JsonTest.json");
		
		json.set("testjson",1,1);
		
		assertTrue(json.get(1,1).equalsIgnoreCase("testjson"));
		
	}
	
	/**
	 * Test si le set si la case n'existait pas
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 */
	@Test
	public void Testset_exceptionCaseInexistanteJSON() throws IOException{
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		
		JsonManager json = new JsonManager ("JsonTest.json");
		 
		json.set("testjson",10,1);

	}
	
	/**
	 * Test la fonction getArrayCopy()
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 */
	@Test
	public void TestcopyJSON() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		
		JsonManager json = new JsonManager ("JsonTest.json");
		
		String[][] copy = json.getArrayCopy();		
		
		assertTrue(json.get(1,1).equalsIgnoreCase(copy[1][1]));

	}
	
	/**
	 * Test le parser
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 */
	@Test
	public void TestparserJSON() throws IOException{
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		File t =new File("test.json");
		JsonManager json = new JsonManager ("JsonTest.json");
		JsonManager.parseJsonFile("test.json", json.getArrayCopy(), json.getWidth(), json.getHeight());
		
		t.deleteOnExit();
		

	}
	
	/**
	 * Test le parser si le fichier de fin contient les mêmes éléments
	 * @throws IOException si la lecture ou l'écriture du fichier échoue
	 */
	@Test
	public void Testparser_Json_JSON() throws IOException{
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		
		JsonManager json = new JsonManager ("JsonTest.json");
		
		File test =new File("test2.json");
		JsonManager.parseJsonFile("test2.json", json.getArrayCopy(), json.getWidth(), json.getHeight());
		JsonManager json2 = new JsonManager ("JsonTest.json");
		

		boolean b = true;
		if (json2.getHeight()==json.getHeight() && json2.getWidth() == json.getWidth())
		{
			int i;
			for (i=0;i<json2.getWidth();i++)
			{
				b = b && json2.getValues(json2.get(i, 0)).equals(json.getValues(json2.get(i,  0)));
			}
			
		}
		test.delete();
		assertTrue(b);

	}
	
	/**
	 * Test le parseur en passant par le csv
	 * @throws IOException si la lecture ou l'écriture fchier échoue
	 * @throws CsvException 
	 * @throws IllegalArgumentException 
	 * @throws NullPointerException 
	 */
	@Test
	public void Testparser_json_csv_JSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException{
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		
		JsonManager js1 = new JsonManager ("JsonTest.json");
		
		File testcsvt =new File("testcsvt.csv");
		CsvManager.parseCsvFile("testcsvt.csv", js1.getArrayCopy(), js1.getWidth(), js1.getHeight());
		CsvManager csvt = new CsvManager ("testcsvt.csv");
		
		File jst =new File("Test.json");
		JsonManager.parseJsonFile("Test.json", csvt.getArrayCopy(), csvt.getWidth(), csvt.getHeight());

    	JsonManager jst1 = new JsonManager("Test.json");
			
		boolean b = true;
		if (js1.getHeight()==jst1.getHeight() && js1.getWidth() == jst1.getWidth())
		{
			int i;
			for (i=0;i<js1.getWidth();i++)
			{
				b = b && js1.getValues(js1.get(i, 0)).equals(jst1.getValues(js1.get(i,  0)));
			}
			
		}
		
		testcsvt.delete();
		jst.delete();
		assertTrue(b);

	}
	
	/**
	 * Test le parser avec un fichier Null
	 * exception attendue : NullPointerException car le fichier passer en argument du parser est null
	 * @throws IOException
	 */
	@Test(expected=NullPointerException.class)
	public void Testparser_null_JSON() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
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
		
		JsonManager json = new JsonManager ("JsonTest.json");
		CsvManager.parseCsvFile(null, json.getArrayCopy(), json.getWidth(), json.getHeight());
		
		

	}
	

}
