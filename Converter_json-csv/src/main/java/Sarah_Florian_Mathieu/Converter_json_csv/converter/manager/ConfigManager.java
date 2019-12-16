package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfigManager extends Manager {
	
	public static final String configFileName = "Config.cfg";
	
	ArrayList <String> attributs;
	Map<String,ArrayList<String>> values;
	ArrayList <String> operations;
	
	public ConfigManager(String [][] data, int width, int height) throws ConfigFileException {
		if(data == null) throw new ConfigFileException ();
		this.data = data;
		largeur = width;
		hauteur = height;
	}
	
	public void generateConfigFile() throws IOException {
		File f = new File("Config.cfg");
		if(f.exists()) f.delete();
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(configFileName));
		for(int i = 0; i < largeur; i ++) {
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
		values = new HashMap<String,ArrayList<String>> ();
		String[] tmp;
		for(String currentLine : line) {
			tmp = currentLine.split("=");
			if(tmp.length != 2) throw new ConfigFileException();
			else {
				cible.add(tmp[0].trim());
				values.put(tmp[0].trim(),setValues(tmp[0].trim()));
				operations.add(tmp[1].trim());
			}
		}
	}

	private ArrayList<String> splitOnOperator(String s){
		s = s.trim();
		int i;
		String[] tmp = null;
		tmp = s.split("(?!^)\\b");
		ArrayList<String> split = new ArrayList<String> (tmp.length);

		for(i = 0; i < tmp.length ; i++){
			split.add(i, tmp[i].trim());
		}
		return split;
	}
	
	private void evaluate() { //calcul linéaire sans ordre de priorité sur les différents opérateurs
		ArrayList<String> split;
		ArrayList<String> res;
		for(int i = 0; i < operations.size(); i++) {
			split = splitOnOperator(operations.get(i));
			//System.out.println("getValues of " + split.get(0) + " : " + getValues(split.get(0)));
			res = getValues(split.get(0));
			if(res == null) {
				res = new ArrayList<String>();
				res.add(split.get(0));
			}
			split.remove(0);
			boolean continuer = split != null;
			if(continuer == true) continuer = continuer && split.isEmpty() == false;
			while(continuer) {
				//System.out.println(i + " : " + res);
				try{
					if(split.get(0).equalsIgnoreCase("+")) {
						res = add(res,split.get(1));
					}
					else if(split.get(0).equalsIgnoreCase("-")) {
						res = substract(res,split.get(1));
					}
					else if(split.get(0).equalsIgnoreCase("*")) {
						res = multiply(res,split.get(1));
					}
					else if(split.get(0).equalsIgnoreCase("\\")) {
						res = divide(res,split.get(1));
					}
					else if(split.get(0).equalsIgnoreCase("|")) {
						res = concatenate(res,split.get(1));
					}
					else { //ConfigFileException qui ne peut etre capturé si l'operateur est invalide
						System.out.println("fail");
						split = null;
						if(removeAttribut(attributs.get(i))) ;
						operations.remove(i);
						i--;
					}
					
					if(split != null) {
						split.remove(0);
						split.remove(0);
					}
				}catch (ClassCastException | NumberFormatException | IndexOutOfBoundsException e) { //calcul impossible, suppression de l'attribut dans les données
					System.out.println("fail");
					split = null;
					removeAttribut(attributs.get(i));
					operations.remove(i);
					i--;
					//e.printStackTrace();
					
				}
				continuer = split != null;
				if(continuer == true) continuer = continuer && split.isEmpty() == false;
			}
			
			if(split != null) {
				values.put(attributs.get(i), res);
				//System.out.println("final " + res);
			}
		}
	}
	
	private void updateData() {
		largeur = attributs.size();
		int max = 0;
		for(int i = 0; i < attributs.size(); i++) max = ((max > values.get(attributs.get(i)).size() + 1)? max : values.get(attributs.get(i)).size() + 1);
		hauteur = max;
		data = new String[largeur][hauteur];
		for(int i = 0; i < attributs.size(); i++) {
			data[i][0] = attributs.get(i);
		}
		int j;
		for(int i = 0; i < attributs.size(); i++) {
			j = 1;
			for(String s : values.get(attributs.get(i))) {
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
		return values.get(att_src);
	}
	
	private boolean removeAttribut(String att_src) {
		for(int i = 0; i < attributs.size(); i++) {
			if(att_src.equalsIgnoreCase(attributs.get(i))) {
				attributs.remove(i);
				values.remove(att_src);
				return true;
			}
		}
		return false;
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
				try {
					o1 = (double)o1 + (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 + (int)o2;
					res.set(i, Double.toString((int) o1));
				}
				
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(att_src);
				try {
					o1 = (double)o1 + (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 + (int)o2;
					res.set(i, Double.toString((int) o1));
				}
			}
		}
		
		return res;
	}
	
	private ArrayList<String> substract (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = getValues(att_src);
		Object o1, o2;
		
		if(vals != null) { //att_src existe
			int min = (res.size() < vals.size())? res.size() : vals.size();
			for(int i = 0; i < min; i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(vals.get(i));
				try {
					o1 = (double)o1 - (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 - (int)o2;
					res.set(i, Double.toString((int) o1));
				}
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(att_src);
				try {
					o1 = (double)o1 - (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 - (int)o2;
					res.set(i, Double.toString((int) o1));
				}
			}
		}
		
		return res;
	}
	
	private ArrayList<String> divide (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = getValues(att_src);
		Object o1, o2;
		
		if(vals != null) { //att_src existe
			int min = (res.size() < vals.size())? res.size() : vals.size();
			for(int i = 0; i < min; i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(vals.get(i));
				try {
					o1 = (double)o1 / (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 / (int)o2;
					res.set(i, Double.toString((int) o1));
				}
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(att_src);
				try {
					o1 = (double)o1 / (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 / (int)o2;
					res.set(i, Double.toString((int) o1));
				}
			}
		}
		
		return res;
	}
	
	private ArrayList<String> multiply (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
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
				try {
					o1 = (double)o1 * (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 * (int)o2;
					res.set(i, Double.toString((int) o1));
				}
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = JsonManager.cast(res.get(i));
				o2 = JsonManager.cast(att_src);
				try {
					o1 = (double)o1 * (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 * (int)o2;
					res.set(i, Double.toString((int) o1));
				}
			}
		}
		
		return res;
	}
}
