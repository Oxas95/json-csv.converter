package Sarah_Florian_Mathieu.Converter_json_csv.converter;

import java.io.IOException;
import java.util.Scanner;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigFileException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.ConfigManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvException;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.manager.JsonManager;

public class Converter {

	/**
	 * how many lines
	 */
	private int hauteur = 0;
	
	/**
	 * how many values by line
	 */
	private int largeur = 0;
	
	/**
	 * store the string[][] returned by (Json/Csv)Manager
	 */
	private String [][] data = null;
	
	public Converter(String toConvert) throws IOException, NullPointerException, IllegalArgumentException, CsvException, FileFormatException{
		
		try{
			CsvManager cm = new CsvManager(toConvert);
			data = cm.getArrayCopy();
			largeur = cm.getWidth();
			hauteur = cm.getHeight();
		}catch(IllegalArgumentException e){
			try{
				JsonManager jm = new JsonManager(toConvert);
				data = jm.getArrayCopy();
				largeur = jm.getWidth();
				hauteur = jm.getHeight();
			}catch(IllegalArgumentException e1) {
				throw new FileFormatException();
			}
		}
	}
	
	public void configureData() throws ConfigFileException, IOException {
		ConfigManager cm = new ConfigManager(data, largeur, hauteur);
		cm.generateConfigFile();
		System.out.println("Ecrire \"continue\" pour valider les changements");
		Scanner s = new Scanner(System.in);
		while(s.nextLine().equalsIgnoreCase("continue"))
		
		cm.ProcessFile();
	}
	
	public void saveAs(String name, TypeFile tf) throws IllegalArgumentException, IOException {
		if(tf == TypeFile.CSV) {
			CsvManager.parseCsvFile(name, data, largeur, hauteur);
		}
		else if(tf == TypeFile.JSON) {
			JsonManager.parseJsonFile(name, data, largeur, hauteur);
		}
	}
}
