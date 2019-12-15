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
	
	ArrayList <String> attributs;
	ArrayList <ArrayList <String>> values;
	ArrayList <String> operations;
	
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
	
	private void splitData(ArrayList <String> cible, ArrayList <String> operations) throws FileNotFoundException, ConfigFileException {
		ArrayList <String> line = fileToListString(configFileName);
		values = new ArrayList <ArrayList <String>> ();
		String[] tmp;
		for(String currentLine : line) {
			tmp = currentLine.split("=");
			if(tmp.length != 2) throw new ConfigFileException();
			else {
				cible.add(tmp[0].trim());
				values.add(setValues(tmp[0].trim()));
				operations.add(tmp[1].trim());
			}
		}
	}
	
	private ArrayList<String> splitOnOperator(String s){
		ArrayList<String> split = new ArrayList<String> ();
		
		return split;
	}
	
	private void evaluate() { //calcul linéaire sans ordre de priorité sur les différents opérateurs
		ArrayList<String> split;
		ArrayList<String> res;
		int j;
		for(int i = 0; i < operations.size(); i++) {
			split = splitOnOperator(operations.get(i));
			res = getValues(split.get(0));
			split.remove(0);
			while(split.isEmpty() == false && split != null) {
				try{
					if(split.get(0).equalsIgnoreCase("+")) {
						res = add(res,split.get(1));
					}
					if(split.get(0).equalsIgnoreCase("-")) {
						res = substract(res,split.get(1));
					}
					if(split.get(0).equalsIgnoreCase("*")) {
						res = multiply(res,split.get(1));
					}
					if(split.get(0).equalsIgnoreCase("/")) {
						res = divide(res,split.get(1));
					}
					if(split.get(0).equalsIgnoreCase("|")) {
						res = concatenate(res,split.get(1));
					}
					split.remove(0);
					split.remove(0);
				}catch (ClassCastException | NumberFormatException | IndexOutOfBoundsException e) { //calcul impossible, suppression de l'attribut dans les données
					split = null;
					attributs.remove(i);
					values.remove(i);
					i--;
				}
			}
			
			if(split != null) {
				String s = attributs.get(i);
				attributs.remove(i);
				values.remove(i);
				attributs.add(s);
				values.add(res);
			}
		}
	}
	
	private void updateData() {
		largeur = attributs.size();
		int max = 0;
		for(int i = 0; i < values.size(); i++) max = ((max > values.get(i).size() + 1)? max : values.get(i).size() + 1);
		hauteur = max;
		data = new String[largeur][hauteur];
		for(int i = 0; i < attributs.size(); i++) {
			data[i][0] = attributs.get(i);
		}
		int j;
		for(int i = 0; i < attributs.size(); i++) {
			j = 1;
			for(String s : values.get(i)) {
				data[i][j] = s;
				j++;
			}
		}
	}
	
	public void ProcessFile() throws FileNotFoundException, ConfigFileException {
		attributs = new ArrayList <String> ();
		operations = new ArrayList <String> ();
		splitData(attributs, operations);
		evaluate();
		updateData();
	}
	
	private ArrayList<String> getValues(String att_src){
		for(int i = 0; i < attributs.size(); i++) {
			if(att_src.equalsIgnoreCase(attributs.get(i))) {
				return values.get(i);
			}
		}
		return null;
	}
	
	private ArrayList<String> setValues(String attribut){
		ArrayList<String> vals = null;
		int j;
		for(int i = 0; i < largeur; i++) {
			if(attribut.equalsIgnoreCase(data[i][0])) {
				vals = new ArrayList<String> ();
				for(j = 1; j < hauteur; j++) {
					vals.add(data[i][j]);
				}
				return vals;
			}
		}
		return vals;
	}
	
	private ArrayList<String> concatenate (ArrayList<String> res, String att_src) {
		ArrayList<String> vals = getValues(att_src);
		ArrayList<String> tmp;
		
		if(vals != null) { //att_src existe
			if(vals.size() > res.size()) {
				tmp = res;
				res = vals;
				vals = tmp;
			}
			
			for(int i = 0; i < vals.size(); i++) {
				res.set(i, res.get(i) + vals.get(i));
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				res.set(i, res.get(i) + att_src);
			}
		}
		
		return res;
	}
	
	private ArrayList<String> add (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = getValues(att_src);
		ArrayList<String> tmp;
		Object o1, o2;
		
		if(vals != null) { //att_src existe
			if(vals.size() > res.size()) {
				tmp = res;
				res = vals;
				vals = tmp;
			}
			
			for(int i = 0; i < vals.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(vals.get(i));
				o1 = (double)o1 + (double)o2;
				res.set(i, Double.toString((double) o1));
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(att_src);
				o1 = (double)o1 + (double)o2;
				res.set(i, Double.toString((double) o1));
			}
		}
		
		return res;
	}
	
	private ArrayList<String> substract (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = getValues(att_src);
		ArrayList<String> tmp;
		Object o1, o2;
		
		if(vals != null) { //att_src existe
			if(vals.size() > res.size()) {
				tmp = res;
				res = vals;
				vals = tmp;
			}
			
			for(int i = 0; i < vals.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(vals.get(i));
				o1 = (double)o1 - (double)o2;
				res.set(i, Double.toString((double) o1));
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(att_src);
				o1 = (double)o1 - (double)o2;
				res.set(i, Double.toString((double) o1));
			}
		}
		
		return res;
	}
	
	private ArrayList<String> divide (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = getValues(att_src);
		ArrayList<String> tmp;
		Object o1, o2;
		
		if(vals != null) { //att_src existe
			if(vals.size() > res.size()) {
				tmp = res;
				res = vals;
				vals = tmp;
			}
			int min = (res.size() < vals.size())? res.size() : vals.size();
			for(int i = 0; i < min; i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(vals.get(i));
				o1 = (double)o1 / (double)o2;
				res.set(i, Double.toString((double) o1));
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(att_src);
				o1 = (double)o1 / (double)o2;
				res.set(i, Double.toString((double) o1));
			}
		}
		
		return res;
	}
	
	private ArrayList<String> multiply(ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = getValues(att_src);
		ArrayList<String> tmp;
		Object o1, o2;
		int i,j;
		if(vals != null) { //att_src existe
			if(vals.size() > res.size()) {
				tmp = res;
				res = vals;
				vals = tmp;
			}
			for(i = 0; i < vals.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(vals.get(i));
				try{
					o1 = (double)o1 * (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch(ClassCastException e) {//multiplier String par un nombre
					String res2 = "";
					if(o2.getClass() == String.class && o1.getClass() == String.class) throw new NumberFormatException ();
					else if(o2.getClass() == String.class) {
						for(j = 0; j < (double) o1; j++) {
							res2 += (String) o2;
						}
						res.set(i, res2);
					}
					else {
						for(j = 0; j < (double) o2; j++) {
							res2 += (String) o1;
						}
						res.set(i, res2);
					}
				}
			}
		}
		else { //att_src n'existe pas
			for(i = 0; i < res.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(att_src);
				try{
					o1 = (double)o1 * (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch(ClassCastException e) { //multiplier String par un nombre
					if(o2.getClass() == String.class && o1.getClass() == String.class) throw new NumberFormatException ();
					else if(o2.getClass() == String.class) {
						String res2 = "";
						for(j = 0; j < (double) o1; j++) {
							res2 += (String) o2;
						}
						res.set(i, res2);
					}
					else {
						String res2 = "";
						for(j = 0; j < (double) o2; j++) {
							res2 += (String) o1;
						}
						res.set(i, res2);
					}
				}
			}
		}
		return res;
	}
}
