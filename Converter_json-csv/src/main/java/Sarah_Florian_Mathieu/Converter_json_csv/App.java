package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.IOException;

import Sarah_Florian_Mathieu.Converter_json_csv.converter.CsvManager;
import Sarah_Florian_Mathieu.Converter_json_csv.converter.JsonManager;

/**
 * Hello world!
 *
 */
public enum App 
{
	APPLICATION;

    public static void main( String[] args ) throws IOException
    {
    	CsvManager cm = new CsvManager("toJson.csv");
    	JsonManager.parseJsonFile("jsonTest",cm.getArrayCopy(),cm.getWidth(),cm.getHeight());
    	
    }
}
