package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;
import java.util.IllegalFormatException;
import java.util.Scanner;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.JsonManager;

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
	 */
	public static void launchApp(String fic_in, String fic_out) throws FileFormatException, IOException {
		if(fic_in.endsWith(".csv")){
			CsvManager cm = new CsvManager(fic_in);
			JsonManager.parseJsonFile(fic_out, cm.getArrayCopy(), cm.getWidth(), cm.getHeight());
		}
		else if(fic_in.endsWith(".json")){
			JsonManager jm = new JsonManager(fic_in);
			CsvManager.parseCsvFile(fic_out, jm.getArrayCopy(), jm.getWidth(), jm.getHeight());
		}
		else throw new FileFormatException();
	}

	/**
	 * Method which asks an input file and a name for the output file and calls method launchApp()
	 * @throws FileFormatException
	 * @throws IOException
	 */
	public static void interact() throws FileFormatException, IOException{
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

		launchApp(fic_in,fic_out);
	}
	
	/**
	 * 
	 * @param args not used
	 * @throws IOException if problem to read a file used by the converter
	 */
    public static void main( String[] args ) throws IOException, FileFormatException {
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
