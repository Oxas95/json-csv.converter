package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.Test;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigFileException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.JsonManager;



public class ConfigTest {
	
	/**
	 * Test du constructeur
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test
	public void TestConstructeur_Config() throws IOException, NullPointerException, IllegalArgumentException, CsvException{
		
		File f;
		f = new File("Test.csv");
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("Test.csv");
		
		new ConfigManager(csv.getArrayCopy(),csv.getWidth(),csv.getHeight());
		f.delete();
		
			
	}

	/**
	 * Test du constructeur
	 * exception attendu : NullPointerException car le fichier donner au ConfigManager est null
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws IllegalArgumentException si l'argument donnée est invalide
	 * @throws CsvException si une erreur est détectée durant la lecture du fichier csv
	 */
	@Test(expected = NullPointerException.class)
	public void TestConstructeur_Null_Config() throws IOException, NullPointerException, IllegalArgumentException, CsvException{
		
		File f;
		f = new File("Test.csv");
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream(f));
		    fw.write("marque,nom,quantité,produit,prix\n"

					+ "Andros,yaourt au citron,fraise,2,yaourt\n"

					+ "La laitière,yaourt à la vanille,5,yaourt,2.50\n");
		  fw.close();
		
		CsvManager csv = new CsvManager ("Test.csv");
		
		new ConfigManager(null,csv.getWidth(),csv.getHeight());
		f.delete();
			
	}
	
	/**
	 * Test la fonction generateConfigFile
	 * @throws IOException si la lecture ou l'écriture échoue 
	 */
	@Test
	public void TestgenerateFile_Config() throws IOException  {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		
		cm.generateConfigFile();
					
	}
	
	/**
	 * Test la fonction ProcessFile
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu__popup__menuitem__value = menu__popup__menuitem__value\n" + 
		    		"menu__value = menu__value | jambon | menu__value\n" + 
		    		"menu__popup__menuitem__undefined0__undefined1 = menu__popup__menuitem__undefined0__undefined1 * 2\n" + 
		    		"test__id = test__id | test__id | test__id | 123456789\n" + 
		    		"menu__popup__menuitem__undefined0__undefined0 = menu__popup__menuitem__undefined0__undefined0 * 10 / 2\n" + 
		    		"menu__popup__menuitem__onclick = menu__popup__menuitem__onclick | menu__id | menu__popup__menuitem__undefined0\n" + 
		    		"menu__id = \n" + 
		    		"menu__popup__menuitem__undefined0 = banane\n" + 
		    		"nenu__produit__jambon = test__id | menu__popup__menuitem__value\n" + 
		    		"");
		fw.close();
		cm.ProcessFile();
		
			
	}
	
	/**
	 * Test l'erreur si il y a deux égales à la suite
	 * exception attendue : ConfigFileException car l'erreur des deux égal est détecter au niveau des données du config
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test(expected=ConfigFileException.class)
	public void TestProcess_Erreur2egales_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu__popup__menuitem__value = = menu__popup__menuitem__value\n" + 
		    		"menu__value = =menu__value | jambon | menu__value\n" + 
		    		"menu__popup__menuitem__undefined0__undefined1 = menu__popup__menuitem__undefined0__undefined1 * 2\n" + 
		    		"test__id = test__id | test__id | test__id | 123456789\n" + 
		    		"menu__popup__menuitem__undefined0__undefined0 = menu__popup__menuitem__undefined0__undefined0 * 10 / 2\n" + 
		    		"menu__popup__menuitem__onclick = menu__popup__menuitem__onclick | menu__id | menu__popup__menuitem__undefined0\n" + 
		    		"menu__id = \n" + 
		    		"menu__popup__menuitem__undefined0 = banane\n" + 
		    		"nenu__produit__jambon = test__id | menu__popup__menuitem__value\n" + 
		    		"");
		fw.close();

		cm.ProcessFile();
			
	}
	
	/**
	 * Test l'erreur si il y a 0 égale à la suite
	 * exception attendue : ConfigFileException car l'erreur est détecter au niveau des données du config
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test(expected=ConfigFileException.class)
	public void TestProcess_Erreur0egale__Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu__popup__menuitem__value menu__popup__menuitem__value\n" + 
		    		"menu__value = menu__value | jambon | menu__value\n" + 
		    		"menu__popup__menuitem__undefined0__undefined1 = menu__popup__menuitem__undefined0__undefined1 * 2\n" + 
		    		"test__id = test__id | test__id | test__id | 123456789\n" + 
		    		"menu__popup__menuitem__undefined0__undefined0 = menu__popup__menuitem__undefined0__undefined0 * 10 / 2\n" + 
		    		"menu__popup__menuitem__onclick = menu__popup__menuitem__onclick | menu__id | menu__popup__menuitem__undefined0\n" + 
		    		"menu__id = \n" + 
		    		"menu__popup__menuitem__undefined0 = banane\n" + 
		    		"nenu__produit__jambon = test__id | menu__popup__menuitem__value\n" + 
		    		"");
		fw.close();

		cm.ProcessFile();
			
	}
	
	/**
	 * Test l'erreur il existe une variable stocker null part 
	 * exception attendue : ConfigFileException car les données entrées ne sont pas conforme
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_Argumentoublie_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu__popup__menuitem__value = menu__popup__menuitem__value\n" + 
		    		"menu__value = menu__value | jambon | menu__value\n" + 
		    		"menu__popup__menuitem__undefined0__undefined1 = menu__popup__menuitem__undefined0__undefined1 * 2\n" + 
		    		"test__id = test__id | test__id | test__id | 123456789\n" + 
		    		"menu__popup__menuitem__undefined0__undefined0 = menu__popup__menuitem__undefined0__undefined0 * 10 / 2\n" + 
		    		"menu__popup__menuitem__onclick = menu__popup__menuitem__onclick | menu__id | menu__popup__menuitem__undefined0\n" + 
		    		"menu__id = \n" + 
		    		"= banane\n" + 
		    		"nenu__produit__jambon = test__id | menu__popup__menuitem__value\n" + 
		    		"");
		fw.close();

		cm.ProcessFile();
		
	}
	
	/**
	 * Test les donées sont enregistrer correctement
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_testData_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu__popup__menuitem__value = menu__popup__menuitem__value\n");
		fw.close();

		cm.ProcessFile();
		assertTrue(cm.getWidth() == 1 && cm.getHeight() == 4);
		
	}
	
	/**
	 * Test si la longueur et la largeur du tableau donner en entrer est faux
	 * exception attendue ArrayIndexOutOfBoundsException car la taille n'est pas la bonne
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void TestProcess_invertion_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getHeight(),json.getWidth());
		cm.generateConfigFile();
		cm.get(cm.getWidth(), cm.getHeight());
		
	}
	
	/**
	 * Test la fonction evaluate fonctionne pour le et
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_EvaluateET_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu = menu & test\n");
		fw.close();
		cm.ProcessFile();
		assertTrue(cm.get(0, 1).equalsIgnoreCase("menu") && cm.get(0, 2).equalsIgnoreCase("test"));
		
	}
	
	/**
	 * Test la fonction evaluate fonctionne pour l'addition
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_EvaluateAdd_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu = 1 + 1\n");
		fw.close();
		cm.ProcessFile();
		assertTrue(cm.get(0, 1).equalsIgnoreCase("2"));
		
	}

	/**
	 * Test la fonction evaluate fonctionne pour la soustraction
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_EvaluateMin_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu = 1 - 1\n");
		fw.close();
		cm.ProcessFile();
		assertTrue(cm.get(0, 1).equalsIgnoreCase("0"));
		
	}
	
	/**
	 * Test la fonction evaluate fonctionne pour la multiplication
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_EvaluateMult_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu = 1 * 2\n");
		fw.close();
		cm.ProcessFile();
		assertTrue(cm.get(0, 1).equalsIgnoreCase("2"));
		
	}
	
	/**
	 * Test la fonction evaluate fonctionne pour la division
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_EvaluateDiv_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu = 4 / 2\n");
		fw.close();
		cm.ProcessFile();
		assertTrue(cm.get(0, 1).equalsIgnoreCase("2"));
		
	}
	
	/**
	 * Test la fonction evaluate fonctionne pour la concatenation
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_EvaluateConcat_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu = 4 | 2\n");
		fw.close();
		cm.ProcessFile();
		assertTrue(cm.get(0, 1).equalsIgnoreCase("42"));
		
	}
	
	/**
	 * Test la fonction evaluate fonctionne avec plusieurs opération
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_EvaluateMultitest_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("menu = 4 | 2\n"
		    		+ "test2 = 2+3-4");
		fw.close();
		cm.ProcessFile();
		assertTrue(cm.get(0, 1).equalsIgnoreCase("42") && cm.get(1, 1).equalsIgnoreCase("1"));
		
	}
	
	/**
	 * Test la fonction evaluate ne fonctionne pas avec une erreur de calcul
	 * erreur attendue : retour des get a zéro
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test
	public void TestProcess_Evaluate_Erreurcalcul_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("test = 2+*34");
		fw.close();
		cm.ProcessFile();
		assertTrue(cm.getHeight() == 0 && cm.getWidth() == 0);
		
	}
	
	/**
	 * Test la fonction evaluate ne fonctionne pas avec une opération contenant un caractère
	 * erreur attendue : ArrayIndexOutOfBoundsException car le paramètre est un caractère au niveau du get
	 * @throws IOException si la lecture ou l'écriture échoue
	 * @throws NullPointerException si le chemin d'accès est null 
	 * @throws ConfigFileException si une erreur est détecter durant la gestion des donnée du ConfigManager
	 */
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void TestProcess_Evaluate_ErreurChar_Config() throws IOException, NullPointerException, ConfigFileException {
		
		JsonManager json = new JsonManager ("jsonTest2.json");
		
		ConfigManager cm = new ConfigManager(json.getArrayCopy(),json.getWidth(),json.getHeight());
		cm.generateConfigFile();
		
		OutputStreamWriter fw;
		 fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		    fw.write("test = 2+A");
		fw.close();
		cm.ProcessFile();
		cm.get(0, 1);
		
	}
}