package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.NoSuchFileException;

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
	// Test avec un fichier null
	@Test(expected=NullPointerException.class)
	public void TestConstructeur_Fichiernull_JSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
			
		JsonManager json = new JsonManager (null);
			
	}
	// Test avec n'est pas un json
		@Test(expected=IllegalArgumentException.class)
		public void TestConstructeur_FichierNonJSON_JSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
			File t;
			t = new File("JsonTest2.txt");
			JsonManager csv = new JsonManager ("JsonTest2.txt");
			t.deleteOnExit();
				
		}
	// Test avec un fichier inexistant
		@Test(expected=FileNotFoundException.class)
		public void TestConstructeur_FichierInexistant_JSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
				
			JsonManager json = new JsonManager ("Inexist_fichier.json");
				
		}
	 // Test avec un fichier vide
	@Test
	public void TestConstructeur_Fichiervide_JSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
			
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("{\n\n}");
		  fw.close();
		JsonManager json = new JsonManager ("JsonTest.json");
		
		System.out.print("Test1:\n");
		System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());

	}
	// Test si le constructeur fonctionne avec un fichier non vide
	@Test
	public void TestConstructeur_FichierNonVide_JSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {

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
		 
		System.out.print("Test2:\n");
		System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());

	}
	// Test si le constructeur fonctionne avec un fichier avec une erreur
		@Test(expected=JSONException.class)
		public void TestConstructeur_Fichiererreur_JSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {

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
			
			  JsonManager json = new JsonManager ("JsonTest.json");
			 
			System.out.print("Test2:\n");
			System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());

		}
	
	public void TestgetCSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
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
		 
		System.out.print("Test6:\n");
		System.out.println("Width :"+ json.getWidth()+ "  and Height:"+json.getHeight());
		System.out.println("String:"+ json.get(1,1));
	
		assertTrue(json.get(0,0).equalsIgnoreCase("test"));

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void Testget_exceptionCaseInexistanteJSON() throws IOException,ArrayIndexOutOfBoundsException, NullPointerException, IllegalArgumentException, CsvException {
		
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
		 
		System.out.print("Test Inexist:\n");
		System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());
		System.out.println("String:"+ json.get(100,2));
	

	}
	
	@Test
	public void TestsetJSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
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
		
		
		System.out.print("Test7:\n");
		System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());
		System.out.println("String:"+ json.get(1,1));
		
		json.set("testjson",1,1);
		
		System.out.println("String:"+ json.get(1,1));
		assertTrue(json.get(1,1).equalsIgnoreCase("testjson"));
		

	}
	@Test
	public void TestgetJSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
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
		
		
		System.out.print("Test get:\n");
		System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());
		System.out.println("String:"+ json.get(1,1));
		
		assertTrue(json.get(1,1).equalsIgnoreCase("1"));
		

	}
	@Test
	public void Testset_exceptionCaseInexistanteJSON() throws IOException,ArrayIndexOutOfBoundsException, NullPointerException, IllegalArgumentException, CsvException {
		
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
		 
		System.out.print("Test Inexist:\n");
		System.out.println("Width :"+ json.getWidth()+ "  and Height:"+ json.getHeight());
		json.set("testjson",10,1);
	

	}
	@Test
	public void TestcopyJSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
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
		
		
		System.out.print("Test8:\n");
		
		
		String[][] copy = json.getArrayCopy();
		
		System.out.println("String:"+ json.get(1,1));
		System.out.println("String:"+ copy[1][1]);
		
		
		assertTrue(json.get(1,1).equalsIgnoreCase(copy[1][1]));

	}
	
	@Test
	public void TestparserJSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
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
	//test json->json
	@Test
	public void Testparser_Json_JSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
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
		
		JsonManager jsont = new JsonManager ("test2.json");
		if (json.getHeight()==jsont.getHeight() && json.getWidth() == jsont.getWidth())
		{
			int i,j;
			
			for (i=0;i<json.getWidth();i++)
			{
				for(j=0;j<json.getHeight();j++)
				{
					
					if(json.get(i,j).equalsIgnoreCase(jsont.get(i, j))==false)
					{
						fail();
					}
				}
			}
			
		
		}
		
		test.deleteOnExit();
		

	}
	
	//test json->csv->json
		@Test
		public void Testparser_json_csv_JSON() throws IOException, AssertionError, NullPointerException, IllegalArgumentException, CsvException {
			
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
			File testcsv2 =new File("testcsv2.csv");
			
			
			JsonManager js = new JsonManager ("JsonTest.json");
			CsvManager.parseCsvFile("testcsv2.csv", js.getArrayCopy(), js.getWidth(), js.getHeight());
			CsvManager csv = new CsvManager ("testcsv2.csv");
			
			File jst =new File("Test.json");
			JsonManager.parseJsonFile("Test.json", csv.getArrayCopy(), csv.getWidth(), csv.getHeight());

	    	JsonManager jst1 = new JsonManager("Test.json");
			
		
			if (js.getHeight()==jst1.getHeight() && js.getWidth() == jst1.getWidth())
			{
				int i,j;
				
				for (i=0;i<js.getWidth();i++)
				{
					for(j=0;j<js.getHeight();j++)
					{
						
						if(js.get(i,j).equalsIgnoreCase(jst1.get(i, j))==false)
						{
							fail();
						}
					}
				}
				
			
			}
			
			testcsv2.deleteOnExit();
			jst.deleteOnExit();
			
			

		}
		//test parser null
		@Test(expected=NullPointerException.class)
		public void Testparser_null_JSON() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
			
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
