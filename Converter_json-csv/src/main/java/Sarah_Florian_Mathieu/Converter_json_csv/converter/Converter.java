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
	
	/**
	 * store the configManager used in multiple methods for this converter
	 */
	ConfigManager cm;
	
	/**
	 * 
	 * @param toConvert name of file to convert
	 * @throws IOException if problem occur in CsvManager or JsonManager
	 * @throws NullPointerException if toConvert param is null;
	 * @throws IllegalArgumentException if name of file is invalid
	 * @throws CsvException if problem to read a csv file
	 * @throws FileFormatException if format of file is invalid
	 */
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
	
	/**
	 * Initialize ConfigManager and generate configFile for modifications 
	 * @throws ConfigFileException if can't configure
	 * @throws IOException if problem to write the configFile
	 */
	public void configureData() throws ConfigFileException, IOException {
		cm = new ConfigManager(data, largeur, hauteur);
		cm.generateConfigFile();
	}
	
	/**
	 * save changes in a new file with the desired format 
	 * @param name name of the new file 
	 * @param tf the desired format of file
	 * @throws IllegalArgumentException if name is invalid
	 * @throws IOException if problem to save the new file
	 * @throws ConfigFileException if configFile contain invalid data
	 * @throws NullPointerException if name is null
	 * @throws fileFormatException if tf is invalid
	 */
	public void saveAs(String name, TypeFile tf) throws IllegalArgumentException, IOException, ConfigFileException, NullPointerException {
		cm.ProcessFile();
		this.data = cm.getArrayCopy();
		if(tf == TypeFile.CSV) {
			CsvManager.parseCsvFile(name, data, largeur, hauteur);
		}
		else if(tf == TypeFile.JSON) {
			JsonManager.parseJsonFile(name, data, largeur, hauteur);
		}
		else throw new fileFormatException ();
	}
}
