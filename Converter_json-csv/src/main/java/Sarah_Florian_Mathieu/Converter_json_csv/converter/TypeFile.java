package Sarah_Florian_Mathieu.Converter_json_csv.converter;

/**
 * enumeration of possible format for conversion
 */
public enum TypeFile {
	JSON("json"),
	CSV("csv");
	
	String type;
	
	TypeFile(String type){
		if(type == null) throw new IllegalArgumentException();
		this.type = type;
	}
	
	public String toString() {
		return type;
	}
}
