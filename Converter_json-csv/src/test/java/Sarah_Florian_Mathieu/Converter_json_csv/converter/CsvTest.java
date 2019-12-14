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


import Sarah_Florian_Mathieu.Converter_json_csv.converter.CsvManager;


public class CsvTest {
	
	private File f;
	
	
	@Before 
	 public void setUp() throws IOException {
	    f = new File("CsvTest.csv");
	   
	  }

	 @After
	  public void tearDown() throws IOException {
		f.delete();
	    
	  }
	// Test avec un fichier null
	@Test(expected=NullPointerException.class)
	public void TestConstructeur_Fichiernull_CSV() throws IOException {
			
		CsvManager csv = new CsvManager (null);
			
	}
	// Test avec un fichier inexistant
		@Test(expected=NoSuchFileException.class)
		public void TestConstructeur_FichierInexistant_CSV() throws IOException {
				
			CsvManager csv = new CsvManager ("Inexist_fichier.csv");
				
		}
	 // Test avec un fichier vide
	@Test
	public void TestConstructeur_Fichiervide_CSV() throws IOException {
			
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("");
		  fw.close();
		CsvManager csv = new CsvManager ("CsvTest.csv");
		
		System.out.print("Test1:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());

	}
	// Test si le constructeur fonctionne avec un fichier non vide
	@Test
	public void TestConstructeur_FichierNonVide_CSV() throws IOException {

		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron et fraise,2,yaourt,1.50\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		 
		System.out.print("Test2:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());

	}
	//Test avec un argument en trop 
	@Test
	public void TestConstructeur_FichierLargeurtabDiff_CSV() throws IOException {
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt,1.50\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		 
		System.out.print("Test3:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());

	}
	//Test avec pas assez d'argument dans le tableau
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void TestConstructeur_PasAssezArgumentTab_CSV() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron et fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		 
		System.out.print("Test4:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());

	}
	//test avec gestion des ""
	@Test(expected=IOException.class)
	public void TestConstructeur_erreurEcriture_CSV() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,\"yaourt au citron\" fraise,2,yaourt,1.50\n"

					+ "\"La laitière\",yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		 
		System.out.print("Test5:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());

	}
	
	@Test
	public void TestgetCSV() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		 
		System.out.print("Test6:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());
		System.out.println("String:"+ csv.get(4,2));
		
		assertTrue(csv.get(0,0).equalsIgnoreCase("marque"));

	}
	
	@Test
	public void Testget_exceptionCaseInexistanteCSV() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		 
		System.out.print("Test Inexist:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());
		System.out.println("String:"+ csv.get(100,2));
	

	}
	
	@Test
	public void TestsetCSV() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		
		
		System.out.print("Test7:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());
		System.out.println("String:"+ csv.get(1,1));
		
		csv.set("test",1,1);
		
		System.out.println("String:"+ csv.get(1,1));
		assertTrue(csv.get(1,1).equalsIgnoreCase("test"));
		

	}
	@Test
	public void TestcopyCSV() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		
		
		System.out.print("Test8:\n");
		
		
		String[][] copy = csv.getArrayCopy();
		
		System.out.println("String:"+ csv.get(1,1));
		System.out.println("String:"+ copy[1][1]);
		
		
		assertTrue(csv.get(1,1).equalsIgnoreCase(copy[1][1]));

	}
	
	@Test
	public void TestparserCSV() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		CsvManager.parseCsvFile("test", csv.getArrayCopy(), csv.getWidth(), csv.getHeight());
		File t =new File("test.csv");
		t.deleteOnExit();
		

	}
	
	

}
