package Sarah_Florian_Mathieu.Converter_json_csv.converter;

@SuppressWarnings("serial")
public class FileFormatException extends Exception{
    public FileFormatException(){
        System.err.println("File Format not valid");
    }
}
