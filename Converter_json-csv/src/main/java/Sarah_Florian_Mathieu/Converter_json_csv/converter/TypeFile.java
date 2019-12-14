package Sarah_Florian_Mathieu.Converter_json_csv.converter;

public enum TypeFile {
	JSON("json"),
	CSV("csv");
	
	String type;
	
	TypeFile(String type){
		this.type = type;
	}
	
	public String toString() {
		return type;
	}
}
