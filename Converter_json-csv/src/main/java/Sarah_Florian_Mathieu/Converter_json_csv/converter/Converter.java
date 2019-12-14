package Sarah_Florian_Mathieu.Converter_json_csv.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
	
	public Converter(String toConvert) throws IOException, NullPointerException{
		if(toConvert == null)
			throw new NullPointerException ();
		else if(!toConvert.endsWith(".csv") && !toConvert.endsWith(".json"))
			throw new IllegalArgumentException();
		
		else {
			if(toConvert.endsWith(".csv")) {
				CsvManager cm = new CsvManager(toConvert);
				data = cm.getArrayCopy();
				largeur = cm.getWidth();
				hauteur = cm.getHeight();
			}
			else {
				JsonManager jm = new JsonManager(toConvert);
				data = jm.getArrayCopy();
				largeur = jm.getWidth();
				hauteur = jm.getHeight();
			}
		}
	}
	
	public void generateConfigFile() throws IOException {
		File f = new File("Config.cfg");
		if(f.exists()) f.delete();
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream("Config.cfg"));
		for(int i = 0; i < largeur; i ++) {
	        fw.write(data[i][0] + " = " + data[i][0] + '\n');
		}
		fw.close();
	}
	
	public void ParseConfigFileToData() {
		
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
