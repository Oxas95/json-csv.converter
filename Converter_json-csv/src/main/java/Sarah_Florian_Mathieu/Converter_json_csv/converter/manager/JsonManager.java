package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonManager extends Manager{
    
	public static String fileToString(String path) throws FileNotFoundException {
		Scanner s = new Scanner(new File(path));
		String file = "";
		while(s.hasNext()) {
    		file += s.nextLine();
    	}
    	s.close();
    	return file;
	}
	
    @SuppressWarnings("unchecked")
	public JsonManager(String existingFile) throws FileNotFoundException, NullPointerException {
    	if(existingFile != null) {
			if(existingFile.endsWith(".json") == false) throw new IllegalArgumentException();
		}
		else throw new NullPointerException ();
    	
    	Map<String,Object> data = new HashMap<String, Object> ();
    	
    	String key;
    	ArrayList<Object> ao;
    	JSONObject jo = new JSONObject(JsonManager.fileToString(existingFile));
        String tj = "traitementJson";
        
        parseJsonList(jo,tj, data);
        
        //remplis le tableau avec la liste et initialise les valeurs largeur et hauteur
        largeur = data.size();
        //hauteur défini durant la construction de data
        
        this.data = new String[largeur][hauteur];
        
        int i = 0, j;
        Iterator<String> is = data.keySet().iterator();
        while(is.hasNext()) {
        	key = is.next();
        	ao = (ArrayList<Object>) data.get(key);
        	this.data[i][0] = new String(key);
        	j = 1;
        	for(Object o : ao) {
        		this.data[i][j] = o.toString();
        		j++;
        	}
        	i++;
        }
        
      //remplir les cases à null par une chaine vide
        for(i = 0; i < largeur; i++) { 
	        for(j = 1; j < hauteur; j++) {
	        	if(this.data[i][j] == null) this.data[i][j] = "";
	        }
        }
    }

    private void parseJsonList(JSONObject jo, String path, Map<String, Object> data) { 
    	Object o;
    	String subPath;
    	Iterator<String> is = jo.keys();
    	while(is.hasNext()) { //parcours des clés
    		subPath = is.next();
    		o = jo.get(subPath);
    		if(o.getClass() != JSONObject.class) {
    			addValues(path + separator + subPath, o, data);
    		}
    		else {
    			parseJsonList(jo.getJSONObject(subPath), path + separator + subPath, data); //traitement du sous-JSONObject
    		}
    	}
    }

    private void addValues(String path, Object o, Map<String, Object> data) {  
    	if(o.getClass() != JSONArray.class) { //une valeur quelconque
    		addToList(o, path, data);
    	}
		else { //traitement de chaque valeur du tableau
			JSONArray ja = (JSONArray) o;
			int i;
			for(i = 0; i < ja.length(); i++) {
				if(ja.get(i).getClass() != JSONObject.class && ja.get(i).getClass() != JSONArray.class) { //une valeur quelconque
					addToList(ja.get(i), path, data);
				}
				else if (ja.get(i).getClass() == JSONObject.class){ //le tableau contient un JSONObject à traiter
					parseJsonList((JSONObject) ja.get(i), path, data);
				}
				else if (ja.get(i).getClass() == JSONArray.class){ //tableau dans un tableau
					JSONObject jo = new JSONObject();
					int j = 0;
					String us;
					do { //génération d'une clé sans nom qui n'a pas déjà été généré pour identifier un tableau dans un tableau
						us = "undefined" + j++;
					}while(data.containsKey(path.substring(("traitementJson" + separator).length(),path.length()) + separator + us));
					jo.put(us, ja.get(i));
					parseJsonList(jo, path, data); //traitement du nouveau JSONObject contenant la clé généré associé au sous-tableau du tableau courant
				}
			}
		}
	}
    
    @SuppressWarnings("unchecked")
	private void addToList(Object o, String path, Map<String, Object> data) {
    	String key = path.substring(("traitementJson" + separator).length(),path.length());
    	if(data.containsKey(key)) { //stocke la valeur dans la liste correspondant à la clé
    		
    		((ArrayList<Object>) data.get(key)).add(o);
    		int size = ((ArrayList<Object>) data.get(key)).size();
    		
    		if(hauteur < size + 1) hauteur = size + 1; //calcule la hauteur maximale à chaque mise à jour de la liste de valeurs pour plus tard
    	}
    	else { //génère une liste associé à la clé et y insère la valeur puis stocke
    		ArrayList<Object> values = new ArrayList<Object>();
    		values.add(o);
    		data.put(key, values);
    	}
    }

	private static void parseTreeJson(String[][] csv, int width, int height) throws IOException {
        String s = "", tj = "traitementJson";

        File f = new File(tj);
        if(f.exists()) f.delete();

        OutputStreamWriter fw;
        int i,j;

        for(i = 0; i < width; i++){
            s = tj + separator + csv[i][0];
            f = new File(s);
            f.mkdirs();
            s += separator + "valeur.txt";
            fw = new OutputStreamWriter(new FileOutputStream(s));
            for(j = 1; j < height; j++){
                fw.write(csv[i][j] + '\n');
            }
            fw.close();
        }
    }

    public static Object cast(String s) {
        try {
            double d = Double.parseDouble(s);
            return d;
        }
        catch(java.lang.NumberFormatException e) {
            try{
                int i = Integer.parseInt(s);
                return i;
            }
            catch(java.lang.NumberFormatException e2) {
                return s;
            }
        }
    }

    private static Object valeurTxt(File f) throws IOException {
        ArrayList<Object> ls = new ArrayList <Object> ();
        FileReader fr = new FileReader(f.toPath().toString());
        String s;
        int c = 0;
        while(c != -1) {
            s = "";
            c = 0;
            while(c != -1 && c != '\n') {
            	c = fr.read();
                if(c != -1 && c != '\n') s += (char)c;
            }
            if(s.length() > 0) ls.add(cast(s));
        }
        fr.close();
        if(ls.size() == 1) {
        	f.delete();
        	return ls.get(0);
        }
        else {
            JSONArray ja = new JSONArray();
            for(int i = 0; i < ls.size(); i ++) {
                ja.put(ls.get(i));
            }
            f.delete();
            return ja;
        }
    }
    
    private static Object getValeurTxt(File f) throws IOException {
    	File[] sf = f.listFiles();
    	if(sf != null) {
    		for(int i = 0; i < sf.length; i++) {
    			if(sf[i].getName().equalsIgnoreCase("valeur.txt")) {
    				return valeurTxt(sf[i]);
    			}
    		}
    	}
		return null;
    }
    
    private static Object subObject(File f) throws IOException {
    	File[] sf = f.listFiles();
    	int i;
    	
    	JSONObject jo = new JSONObject();
    	Object obj;
    	JSONArray ja;
    	
    	for(i = 0; i < sf.length; i++) {
    		if(sf[i].getName().equalsIgnoreCase("valeur.txt") == false) {
				obj = getValeurTxt(sf[i]);
				Object o = subObject(sf[i]);
				if(obj != null && o != null) {
        			ja = new JSONArray();
        			ja.put(obj);
        			ja.put(o);
        			jo.put(sf[i].getName(), ja);
        		}
				else if (obj != null && o == null) jo.put(sf[i].getName(), obj);
    			
				else if (o != null) jo.put(sf[i].getName(), o);
				sf[i].delete();
    		}
		}
    	f.delete();
    	if(jo.isEmpty()) return null;
    	return jo;
    }
    
    private static JSONObject generateJsonObject() throws JSONException, IOException{
        String tj = "traitementJson";
        File f = new File(tj);
        f.listFiles();
        File[] sf = f.listFiles();
        JSONObject jo = new JSONObject();
        JSONArray ja;
        Object obj;
        int i;
        
		for(i = 0; i < sf.length; i++) {
			if(sf[i].getName().equalsIgnoreCase("valeur.txt") == false) {
				obj = getValeurTxt(sf[i]);
				Object o = subObject(sf[i]);
				if(obj != null && o != null) {
        			ja = new JSONArray();
        			ja.put(obj);
        			ja.put(o);
        			jo.put(sf[i].getName(), ja);
        		}
				else if (obj != null && o == null) jo.put(sf[i].getName(), obj);
    			
				else if (o != null) jo.put(sf[i].getName(), o);
				sf[i].delete();
    		}
		}
		f.delete();
		return jo;
    }

    public static void parseJsonFile(String newPath, String[][] csv, int width, int height) throws IOException, NullPointerException {
    	if(newPath == null) throw new NullPointerException ();
		if(newPath.endsWith(".json") == false) newPath += ".json";
		File f = new File(newPath);
		if(f.exists()) {
			System.err.println(newPath + " already exists, save undone.");
			throw new IllegalArgumentException ();
		}
        parseTreeJson(csv,width,height);
        JSONObject jo = generateJsonObject();
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(newPath));
        fw.write(jo.toString(4));
        fw.close();
    }
}