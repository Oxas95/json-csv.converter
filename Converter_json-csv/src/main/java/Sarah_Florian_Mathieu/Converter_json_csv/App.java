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
    	String path = args[0];
    	if(path.isEmpty() || args[0].isEmpty() || !args[0].endsWith(".csv")) throw new IllegalArgumentException();
    	CsvManager cm = new CsvManager(path);
    	CsvManager.parseCsvFile("test1", cm.getArrayCopy(), cm.getWidth(), cm.getHeight());
    	JsonManager.parseJsonFile("lol",cm.getArrayCopy(),cm.getWidth(),cm.getHeight());

    }
}
