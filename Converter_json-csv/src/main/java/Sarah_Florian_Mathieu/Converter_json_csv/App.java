package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.IOException;

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
    public static void main( String[] args ) throws IOException
    {
    	//CsvManager cm = new CsvManager("toJson.csv");
    	//JsonManager.parseJsonFile("jsonTest",cm.getArrayCopy(),cm.getWidth(),cm.getHeight());
    	JsonManager jm = new JsonManager("jsonTest2.json");
    	System.out.println(jm);
    }
}
