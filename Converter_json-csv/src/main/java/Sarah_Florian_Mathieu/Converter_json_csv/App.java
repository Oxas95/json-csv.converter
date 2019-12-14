package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.IOException;

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
	 * 
	 * @param args not used
	 * @throws IOException if problem to read a file used by the converter
	 */
    public static void main( String[] args ) throws IOException {
    	//exemple csv->json->csv->csv
    	CsvManager cm = new CsvManager("exemple.csv");
    	JsonManager.parseJsonFile("csv->json", cm.getArrayCopy(), cm.getWidth(), cm.getHeight());
    	JsonManager jm = new JsonManager("csv->json.json");
    	CsvManager.parseCsvFile("csv->json->csv", jm.getArrayCopy(), jm.getWidth(), jm.getHeight());
    	CsvManager cm2 = new CsvManager("csv->json->csv.csv");
    	CsvManager.parseCsvFile("csv->json->csv->csv", cm2.getArrayCopy(), cm2.getWidth(), cm2.getHeight());
    }
}
