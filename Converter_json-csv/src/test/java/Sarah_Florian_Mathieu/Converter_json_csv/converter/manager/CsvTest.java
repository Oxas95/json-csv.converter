package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.NoSuchFileException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.JsonManager;


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
	public void TestConstructeur_Fichiernull_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
			
		new CsvManager (null);
			
	}
	
	// Test avec n'est pas un csv
	@Test(expected=IllegalArgumentException.class)
	public void TestConstructeur_FichierNonCSV_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		new CsvManager ("CsvTest2.txt");
			
	}
	/**
	 * Test d'un fichier inexistant 
	 * exception attendue : NoSuchFileException au moment de la lecture du fichier retourne l'exception
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 * @throws IOException si la lecture ou l'écriture échoue
	 */
	
	@Test(expected=NoSuchFileException.class)
	public void TestConstructeur_FichierInexistant_CSV() throws NullPointerException, IllegalArgumentException, IOException, CsvException  {
			
		new CsvManager ("Inexist_fichier.csv");
			
	}
	/**
	 * Test avec un fichier vide
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */

	@Test
	public void TestConstructeur_Fichiervide_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException  {
			
		File f = new File("EmptyFile.csv");
		f.createNewFile();
		f.deleteOnExit();
		new CsvManager ("EmptyFile.csv");
	}
	
	/**
	 * Test si le constructeur fonctionne avec un fichier non vide
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	
	@Test
	public void TestConstructeur_FichierNonVide_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron et fraise,2,yaourt,1.50\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		new CsvManager ("CsvTest.csv");
	}
	
	/**
	 * Test avec un argument en trop
	 * exception attendue : CSVException caril y a trop d'argument dans la première ligne du tableau ("Andros,yaourt au citron,fraise,2,yaourt,1.50")
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test(expected=CsvException.class)
	public void TestConstructeur_FichierLargeurtabDiff_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException  {
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt,1.50\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");

	}
	
	/**
	 * Test avec pas assez d'argument dans le tableau
	 * exception attendue : CsvException car la première ligne du tableau ne contient pas assez d'argument
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test(expected=CsvException.class)
	public void TestConstructeur_PasAssezArgumentTab_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron et fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		new CsvManager ("CsvTest.csv");
	}
	/**
	 * Test avec gestion des ""
	 * erreur attendue : IOException car le fichier retourne une erreur lors de la lecture a cause de guillemet parasite 
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	//
	@Test(expected=IOException.class)
	public void TestConstructeur_erreurLecture_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,\"yaourt au citron\" fraise,2,yaourt,1.50\n"

					+ "\"La laitière\",yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		new CsvManager ("CsvTest.csv");
	}
	/**
	 * Test du get 
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test
	public void TestgetCSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		
		assertTrue(csv.get(0,0).equalsIgnoreCase("marque"));

	}
	/**
	 * Test le get pour une case inexistante
	 * exception attendue : ArrayIndexOutOfBoundException car la case n'existe pas donc le get retourne une exception
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void Testget_exceptionCaseInexistanteCSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron et fraise,2,yaourt,1.5\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
	
		CsvManager csv = new CsvManager ("CsvTest.csv");
			
		csv.get(100,2);

	}
	
	/**
	 * Test le set
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test
	public void TestsetCSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,2,yaourt,1.5\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		
		csv.set("test",1,1);
		
		assertTrue(csv.get(1,1).equalsIgnoreCase("test"));
		

	}
	/**
	 * Test si le set crée bien une nouvelle case 
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test
	public void Testset_exceptionCaseInexistanteCSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,2,yaourt,1.5\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		csv.set("test",10,1);
		
	}
	/**
	 * Test la fonction getArraycopy
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test
	public void TestcopyCSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		
		String[][] copy = csv.getArrayCopy();
		
		assertTrue(csv.get(1,1).equalsIgnoreCase(copy[1][1]));
	}
	/**
	 * Test si le parser Csv fonctionne
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test
	public void TestparserCSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron et fraise,2,yaourt,1.5\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		CsvManager.parseCsvFile("test", csv.getArrayCopy(), csv.getWidth(), csv.getHeight());
		File t =new File("test.csv");
		t.delete();
		

	}
	/**
	 * Test le parser pour vérifier que la convertion du csv vers un csv ne modifie pas le fichier
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test
	public void Testparser_csv_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		CsvManager.parseCsvFile("test2", csv.getArrayCopy(), csv.getWidth(), csv.getHeight());
		File test =new File("test2.csv");
		CsvManager csvt = new CsvManager ("test2.csv");
		if (csv.getHeight()==csvt.getHeight() && csv.getWidth() == csvt.getWidth())
		{
			int i,j;
			
			for (i=0;i<csv.getWidth();i++)
			{
				for(j=0;j<csv.getHeight();j++)
				{
					if(csv.get(i,j).equalsIgnoreCase(csvt.get(i, j))==false)
					{
						fail();
					}
				}
			}
		}
		
		test.delete();
	}
	/**
	 * Test si la transformation du csv en json pour a nouveau en csv passède bien les mêmes informations
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test
	public void Testparser_csv_json_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,2,yaourt,1.5\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		File testcsv =new File("testcsv.csv");
		File jst =new File("jsonTestt.json");
		File testcsv2 =new File("testcsv2.csv");
		File jst2 =new File("jsonTestt2.json");
		  
		CsvManager csv = new CsvManager ("CsvTest.csv");
		JsonManager.parseJsonFile("jsonTestt.json", csv.getArrayCopy(), csv.getWidth(), csv.getHeight());
		
    	JsonManager js = new JsonManager("jsonTestt.json");
		CsvManager.parseCsvFile("testcsv.csv", js.getArrayCopy(), js.getWidth(), js.getHeight());
		
		CsvManager csvt = new CsvManager ("testcsv.csv");
		JsonManager.parseJsonFile("jsonTestt2.json", csv.getArrayCopy(), csv.getWidth(), csv.getHeight());
		
		new JsonManager("jsonTestt2.json");
		CsvManager.parseCsvFile("testcsv2.csv", js.getArrayCopy(), js.getWidth(), js.getHeight());
		
		CsvManager csvt2 = new CsvManager ("testcsv2.csv");
		if (csvt2.getHeight()==csvt.getHeight() && csvt2.getWidth() == csvt.getWidth())
		{
			int i,j;
			
			for (i=0;i<csvt2.getWidth();i++)
			{
				for(j=0;j<csvt2.getHeight();j++)
				{
					
					if(csvt2.get(i,j).equalsIgnoreCase(csvt.get(i, j))==false)
					{
						fail();
					}
				}
			}
		}
		
		testcsv.delete();
		jst.delete();
		testcsv2.delete();
		jst2.delete();
	}
	
	/**
	 * Test si le fichier donner au parser est null
	 * exception attendue : NullPointerException car la fichier donner en entrée du parser est null
	 * @throws IOException si la lecture ou l'écriture échoue au niveau du new file et du csv mnager
	 * @throws NullPointerException si le chemin d'accès est null pour CsvManager
	 * @throws IllegalArgumentException si l'argument donnée à CsvManager est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test(expected=NullPointerException.class)
	public void Testparser_null_CSV() throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("CsvTest.csv");
		CsvManager.parseCsvFile(null, csv.getArrayCopy(), csv.getWidth(), csv.getHeight());
	}
}
