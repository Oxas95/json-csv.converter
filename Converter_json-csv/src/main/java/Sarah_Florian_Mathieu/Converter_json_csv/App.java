package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.Converter;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.FileFormatException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.TypeFile;
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
	 */
	public static void launchApp(String fic_in, String fic_out, TypeFile tf) throws FileFormatException, IOException, NullPointerException, IllegalArgumentException, CsvException {
		Converter c = new Converter(fic_in);
		
		
		
		c.saveAs(fic_out, tf);
	}

	/**
	 * Method which asks an input file and a name for the output file and calls method launchApp()
	 * @throws FileFormatException
	 * @throws IOException
	 * @throws CsvException
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static void interact() throws FileFormatException, IOException, NullPointerException, IllegalArgumentException, CsvException{
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
		while(choix < 0 && choix > tf.length - 1) {
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
	 * @throws CsvException
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static void main( String[] args ) throws IOException, FileFormatException, NullPointerException, IllegalArgumentException, CsvException {
		//interact();
		//exemple csv->json->csv->csv
		CsvManager cm = new CsvManager("exemple.csv");
		JsonManager.parseJsonFile("csv->json", cm.getArrayCopy(), cm.getWidth(), cm.getHeight());
		JsonManager jm = new JsonManager("csv->json.json");
		CsvManager.parseCsvFile("csv->json->csv", jm.getArrayCopy(), jm.getWidth(), jm.getHeight());
		CsvManager cm2 = new CsvManager("csv->json->csv.csv");
		CsvManager.parseCsvFile("csv->json->csv->csv", cm2.getArrayCopy(), cm2.getWidth(), cm2.getHeight());
	}
}
