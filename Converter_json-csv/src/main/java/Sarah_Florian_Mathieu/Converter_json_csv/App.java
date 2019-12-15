package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.Converter;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.FileFormatException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.TypeFile;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigFileException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.JsonManager;

/**
 *
 * @author user
 *
 */
public enum App
{
	APPLICATION;

	/**
	 * Method which detects input file format and launches the converter
	 * Method called by interact()
	 * @param fic_in
	 * @param fic_out
	 * @throws FileFormatException if file format isn't csv or json
	 * @throws IOException
	 * @throws CsvException
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 * @throws ConfigFileException 
	 */
	public static void launchApp(String fic_in, String fic_out, TypeFile tf) throws FileFormatException, IOException, NullPointerException, IllegalArgumentException, CsvException, ConfigFileException {
		Converter c = new Converter(fic_in);
		
		c.configureData();
		
		c.saveAs(fic_out, tf);
	}

	/**
	 * Method which asks an input file and a name for the output file and calls method launchApp()
	 * @throws FileFormatException
	 * @throws IOException
	 * @throws CsvException
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 * @throws ConfigFileException 
	 */
	public static void interact() throws FileFormatException, IOException, NullPointerException, IllegalArgumentException, CsvException, ConfigFileException{
		System.out.println("******CSV/JSON CONVERTER******");
		Scanner scan = new Scanner(System.in);
		System.out.println("Veuillez saisir un fichier d'entrée :");
		String fic_in = new String();
		String fic_out = new String();

		while(fic_in.isEmpty()) {
			fic_in = scan.nextLine();
		}

		System.out.println("Veuillez saisir un nom pour votre fichier de sortie");
		fic_out = scan.nextLine();
		if (fic_out.isEmpty()) fic_out = fic_in;

		TypeFile[] tf = {TypeFile.CSV,TypeFile.JSON};
		System.out.println("Veuillez sélectionner le produit souhaité : \n1." + tf[0] + "\n2." + tf[1]);
		int choix = -1;
		while(choix < 0 || choix > tf.length - 1) {
			try {
				choix = scan.nextInt();
			}catch(InputMismatchException e) {System.out.println("Entrée invalide");}
		}
		
		launchApp(fic_in,fic_out, tf[choix - 1]);
		scan.close();
	}

	/**
	 *
	 * @param args not used
	 * @throws IOException if problem to read a file used by the converter
	 * @throws FileFormatException
	 * @throws CsvException
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 * @throws ConfigFileException 
	 */
	public static void main( String[] args ) throws IOException, FileFormatException, NullPointerException, IllegalArgumentException, CsvException, ConfigFileException {
		//interact();
		JsonManager jm = new JsonManager("jsonTest2.json");
		System.out.println(jm);
		CsvManager.parseCsvFile("jsonTest3", jm.getArrayCopy(), jm.getWidth(), jm.getHeight());
		JsonManager.parseJsonFile("jsonTest3", jm.getArrayCopy(), jm.getWidth(), jm.getHeight());
	}
}
