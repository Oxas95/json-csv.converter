package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

class ConfigManager extends Manager {
	
	public static final String configFileName = "Config.cfg";
	
	ConfigManager(String [][] data, int width, int height) throws IOException{
		this.data = data;
		largeur = width;
		hauteur = height;
		generateConfigFile(data, width);
	}
	
	private void generateConfigFile(String [][] data, int width) throws IOException {
		File f = new File("Config.cfg");
		if(f.exists()) f.delete();
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(configFileName));
		for(int i = 0; i < width; i ++) {
	        fw.write(data[i][0] + " = " + data[i][0] + '\n');
		}
		fw.close();
		System.out.println("consultez le fichier " + f.getAbsolutePath() + " pour modifier les données");
	}
	
	public static ArrayList<String> fileToListString(String path) throws FileNotFoundException {
		Scanner s = new Scanner(new File(path));
		ArrayList <String> list = new ArrayList <String> ();
		while(s.hasNext()) {
    		list.add(s.nextLine());
    	}
    	s.close();
    	return list;
	}
	
	private void dataChecker() throws FileNotFoundException {
		ArrayList <String> line = fileToListString(configFileName);
		ArrayList <String> cible = new ArrayList <String> ();
		ArrayList <String> operations = new ArrayList <String> ();
		
	}
	
	public void ProcessFile() {
		
	}
}
