package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.IOException;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.JsonManager;

/**
 * Enum which contains main function
 * 
 */
public enum App 
{
	APPLICATION;

    /**
     * @return void
     * main function which launches the converter by calling CsvManager or JsonManager methods
     */
    public static void main( String[] args ) throws IOException
    {
    	//CsvManager cm = new CsvManager("toJson.csv");
    	//JsonManager.parseJsonFile("jsonTest",cm.getArrayCopy(),cm.getWidth(),cm.getHeight());
    	JsonManager jm = new JsonManager("jsonTest2.json");
    }
}
