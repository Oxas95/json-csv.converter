package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.Converter;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.FileFormatException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.TypeFile;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigFileException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvException;

/**
 *
 * @author user
 *
 */
public enum App
{
	APPLICATION;

	/**
	 * Method which asks an input file and a name for the output file and run conversion
	 * @throws FileFormatException if format of file is invalid
	 * @throws IOException if problem to read or write a file during conversion
	 * @throws CsvException if problem to read a csv file
	 * @throws IllegalArgumentException if invalid argument is given for conversion
	 * @throws NullPointerException if null is given for a path
	 * @throws ConfigFileException if problem to configure data
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
		
		Converter c = new Converter(fic_in);
		c.configureData();
		
		System.out.println("Entrez le nom du fichier de sortie pour valider les modifications");
		
		System.out.println("Veuillez saisir un nom pour votre fichier de sortie");
		fic_out = scan.nextLine();
		if (fic_out.isEmpty()) fic_out = fic_in;

		TypeFile[] tf = {TypeFile.CSV,TypeFile.JSON};
		System.out.println("Veuillez sélectionner le produit souhaité : \n1." + tf[0] + "\n2." + tf[1]);
		int choix = -1;
		while(choix < 1 || choix > tf.length) {
			try {
				choix = scan.nextInt();
			}catch(InputMismatchException e) {System.out.println("Entrée invalide");}
		}
		
		System.out.println(choix);
		c.saveAs(fic_out, tf[choix - 1]);
		scan.close();
	}

	/**
	 * run the converter
	 * @param args not used
	 * @throws FileFormatException if format of file is invalid
	 * @throws IOException if problem to read or write a file during conversion
	 * @throws CsvException if problem to read a csv file
	 * @throws IllegalArgumentException if invalid argument is given for conversion
	 * @throws NullPointerException if null is given for a path
	 * @throws ConfigFileException if problem to configure data
	 */
	public static void main( String[] args ) throws IOException, FileFormatException, NullPointerException, IllegalArgumentException, CsvException, ConfigFileException {
		try{
			interact();
		} catch (Exception e) {
			System.err.println("Une erreur est survenu durant la conversion, aucun fichier n'a été créé.");
		}
	}
}
