package Sarah_Florian_Mathieu.Converter_json_csv.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonManager {

    public static final char separator = '/';

    JSONObject jo = null;

    public JsonManager() {
        jo = new JSONObject();
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

    private static Object cast(String s) {
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

    public static void parseJsonFile(String newPath, String[][] csv, int width, int height) throws IOException {
    	if(newPath == null) throw new IllegalArgumentException ();
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