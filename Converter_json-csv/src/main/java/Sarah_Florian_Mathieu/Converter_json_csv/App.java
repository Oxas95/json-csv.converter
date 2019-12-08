package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public enum App 
{
	APPLICATION;

    public static void main( String[] args ) throws IOException
    {
    	CsvManager cm = new CsvManager("exemple.csv");
    	CsvManager.parseCsvFile("test1", cm.getArrayCopy(), cm.getWidth(), cm.getHeight());
    	JsonManager.parseJsonFile("lol",cm.getArrayCopy(),cm.getWidth(),cm.getHeight());
    }
}
