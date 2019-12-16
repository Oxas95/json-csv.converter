package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    
	private static JSONObject parseJson(String[][] csv, int width, int height) {
        String[] s;
        int i,j;
        
        JSONObject jo = (JSONObject) parseJson2(csv[0][0].split(separator), 0, 0, height, csv);
        Object tmp = jo, tmp2;
        for(i = 1; i < width; i++){
            s = csv[i][0].split(separator);
            j = 0;
            tmp = jo;
            while(j < s.length) {
	            try {
	            	tmp2 = ((JSONObject) tmp).get(s[j]);
	            	if(tmp2.getClass() == JSONObject.class) {
	            		if(j == s.length - 1) { //toutes les clés existent déjà
	            			Object o = parseJson2(s, j + 1, i, height, csv);
		            		((JSONObject) tmp).accumulate(s[j], o);
		            		j = s.length;
		            	}
	            		else { //aller au sous JSONObject
		            		tmp = tmp2;
		            		j++;
		            	}
	            	}
	            	
	            	else { //contient un tableau
	            		if(tmp2.getClass() == JSONArray.class) {
	            			j++;
	            			Object o = parseJson2(s, j, i, height, csv);
	            			((JSONArray) tmp2).put(o);
	            			j = s.length;
	            		}
	            		else { //contient une simple valeur
		            		j++;
		            		Object o = parseJson2(s, j, i, height, csv);
		            		((JSONObject) tmp).accumulate(s[j], o);
		            		j = s.length;
	            		}
	            	}
	            }catch (JSONException e) { //aucune valeur pour la clé
	            	Object o = parseJson2(s, j + 1, i, height, csv);
	            	((JSONObject) tmp).put(s[j], o);
	            	j = s.length;
	            }
            }
        }
        return jo;
    }
	
	private static Object parseJson2(String[] s, int k, int i, int height, String[][] csv) {
		if(k < s.length) {
			JSONObject jo = new JSONObject();
			jo.put(s[k], parseJson2(s, k + 1, i, height, csv));
			return jo;
		}
		else { //création du tableau pour stocker les valeurs
			JSONArray ja = new JSONArray();
			for(int j = 1; j < height; j++) {
				if(csv[i][j].isEmpty() == false) ja.put(JsonManager.cast(csv[i][j]));
			}
			if(ja.length() == 1) { //s'il n'y a qu'une seule valeur
				return ja.get(0);
			}
			else return ja;
		}
	}

    public static Object cast(String s) {
        try {
        	int i = Integer.parseInt(s);
            return i;
        }
        catch(java.lang.NumberFormatException e) {
            try{
            	double d = Double.parseDouble(s);
                return d;
            }
            catch(java.lang.NumberFormatException e2) {
                return s;
            }
        }
    }

    public static void parseJsonFile(String newPath, String[][] csv, int width, int height) throws IOException, NullPointerException {
    	if(newPath == null) throw new NullPointerException ();
		if(newPath.endsWith(".json") == false) newPath += ".json";
		File f = new File(newPath);
		if(f.exists()) {
			System.err.println(newPath + " already exists, save undone.");
			throw new IllegalArgumentException ();
		}
        JSONObject jo = parseJson(csv,width,height);
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(newPath));
        fw.write(jo.toString(4));
        fw.close();
    }
}