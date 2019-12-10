package Sarah_Florian_Mathieu.Converter_json_csv.converter;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.CsvManager;


public class CsvTest {
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
	public void TestConstructeur1CSV() throws IOException {
			
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
	public void TestConstructeur2CSV() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt,1.50\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		 
		System.out.print("Test2:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());

	}
	@Test
	public void TestConstructeur3CSV() throws IOException {
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
	
	@Test
	public void TestConstructeur4CSV() throws IOException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		 
		System.out.print("Test4:\n");
		System.out.println("Width :"+ csv.getWidth()+ "  and Height:"+ csv.getHeight());

	}
	//test avec gestion des ""
	@Test(expected=IOException.class)
	public void TestConstructeur5CSV() throws IOException {
		
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
	public void TestConstructeur6CSV() throws IOException {
	

	}
	
	
	

}
