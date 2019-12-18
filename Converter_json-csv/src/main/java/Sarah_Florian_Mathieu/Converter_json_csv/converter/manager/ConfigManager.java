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
	
	/**
	 * name of the configFile name
	 */
	public static final String configFileName = "Config.cfg";
	
	/**
	 * contain the sets of keys during evaluation
	 */
	ArrayList <String> attributs;
	
	/**
	 * contain lists of values associated to keys during evaluation 
	 */
	Map<String,ArrayList<String>> values;
	
	/**
	 * contain operations of which attribute during evaluation
	 */
	ArrayList <String> operations;
	
	/**
	 * copy pointer of data and dimensions
	 * @param data is the table containing data to configure
	 * @param width number of rows
	 * @param height number of lines 
	 * @throws NullPointerException if no data is given
	 */
	public ConfigManager(String [][] data, int width, int height) throws NullPointerException {
		if(data == null) throw new NullPointerException ();
		this.data = data;
		largeur = width;
		hauteur = height;
	}
	
	/**
	 * create and write the configFile
	 * @throws IOException if problem to write in the file
	 */
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
	
	/**
	 * create list of strings containing each line of the file
	 * @param path is the path of the file to store in the list
	 * @return the list containing each line of the file in elements of the file
	 * @throws FileNotFoundException if file is not found
	 */
	public static ArrayList<String> fileToListString(String path) throws FileNotFoundException {
		Scanner s = new Scanner(new File(path));
		ArrayList <String> list = new ArrayList <String> ();
		while(s.hasNext()) {
    		list.add(s.nextLine());
    	}
    	s.close();
    	return list;
	}
	
	/**
	 * separate keys storage and calculs
	 * @param cible to store keys
	 * @param operations to stores calculs of each keys
	 * @throws FileNotFoundException if file is not found
	 * @throws ConfigFileException if there is more than one '=' in a line 
	 */
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

	/**
	 * split each elements of the string and store it in a list
	 * @param s string to split
	 * @return the list containing each elements splited 
	 */
	private  ArrayList<String> splitOnOperator(String s){
		//String s = "att + 2 * 3 & 4 + petite lol kfc";
		s = s.trim();
		/*System.out.println(s);
		System.out.println("*****************************");*/
		int i;
		String[] tmp, tmp2 = null;
		tmp = s.split("[^a-zA-Z][*/+&]");
		tmp2 = s.split("[^*/+&]");
		String tmp22 = "";

		for(i=0; i<tmp2.length;i++){
			tmp22 = tmp22 + tmp2[i];
		}
		tmp22.trim();
		//System.out.println("TEST!: "+ tmp22);

		for(i = 0; i<tmp22.length();i++){
			tmp2[i] = String.valueOf(tmp22.charAt(i));
		}

		ArrayList<String> split = new ArrayList<String> (tmp.length);
		ArrayList<String> split2 = new ArrayList<String> (tmp2.length);

		for(i = 0; i < tmp.length ; i++){
			split.add(i, tmp[i].trim());
		}
		for(i = 0; i < tmp22.length() ; i++){
			split2.add(i, tmp2[i].trim());
		}

		/*for(i = 0; i < tmp.length ; i++){
			System.out.println(split.get(i));
		}*/
		/*for(i = 0; i < tmp22.length() ; i++){
			System.out.println(split2.get(i));
		}
		System.out.println(tmp22.length()+tmp.length);*/
		ArrayList<String> splitfinal = new ArrayList<String> (tmp.length + tmp22.length());
		int cmptmp = 0, cmptmp2 = 0;

		for(i = 0; i < tmp.length + tmp22.length() ; i++){
			if(i%2 == 0){
				splitfinal.add(i, split.get(cmptmp));
				cmptmp++;
			}
			else{
				splitfinal.add(i, split2.get(cmptmp2));
				cmptmp2++;
			}
		}
		/*for(i = 0; i < splitfinal.size() ; i++){
			System.out.println(splitfinal.get(i));
		}*/
		return splitfinal;
	}
	
	/**
	 * calcul all operations and store the result and his key associated
	 * a key is removed from the list of results if calcul is impossible
	 */
	private void evaluate() { //calcul linéaire sans ordre de priorité sur les différents opérateurs
		ArrayList<String> split;
		ArrayList<String> res;
		for(int i = 0; i < operations.size(); i++) {
			split = splitOnOperator(operations.get(i));
			System.out.println(split);
			res = values.get(split.get(0));
			if(res == null) {
				res = new ArrayList<String>();
				res.add(split.get(0));
			}
			split.remove(0);
			boolean continuer = split != null;
			if(continuer == true) continuer = continuer && split.isEmpty() == false;
			while(continuer) {
				try{
					if(split.get(0).charAt(0) == '/') {
						res = divide(res,split.get(1));
					}
					else if(split.get(0).charAt(0) == '&') {
						res = insert(res,split.get(1));
					}
					else if(split.get(0).charAt(0) == '-') {
						res = substract(res,split.get(1));
					}
					else if(split.get(0).charAt(0) == '*') {
						res = multiply(res,split.get(1));
					}
					else if(split.get(0).charAt(0) == '+') {
						res = add(res,split.get(1));
					}
					else if(split.get(0).charAt(0) == '|') {
						res = concatenate(res,split.get(1));
					}
					else { //ConfigFileException qui ne peut etre capturé si l'operateur est invalide
						System.out.println("fail invalid operator : " + split.get(0));
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
					System.out.println("fail, unable to calcul");
					split = null;
					removeAttribut(attributs.get(i));
					operations.remove(i);
					i--;
					e.printStackTrace();
					
				}
				continuer = split != null;
				if(continuer == true) continuer = continuer && split.isEmpty() == false;
			}
			
			if(split != null) {
				if(res.isEmpty()) {
					values.remove(attributs.get(i));
					attributs.remove(i);
				}
				else {
					String s = attributs.get(i);
					if(attributs.contains(s) == false) attributs.add(s);
					values.put(s, res);
					System.out.println("final " + res);
				}
			}
		}
	}

	/**
	 * store the new data in the data table
	 */
	private void updateData() {
		largeur = attributs.size();
		int max = 0;
		for(int i = 0; i < attributs.size(); i++) max = ((max > values.get(attributs.get(i)).size() + 1)? max : values.get(attributs.get(i)).size() + 1);
		hauteur = max;
		this.data = new String[largeur][hauteur];
		for(int i = 0; i < attributs.size(); i++) {
			this.data[i][0] = attributs.get(i);
		}
		int j;
		for(int i = 0; i < attributs.size(); i++) {
			j = 1;
			for(String s : values.get(attributs.get(i))) {
				this.data[i][j] = s;
				j++;
			}
		}
	}
	
	/**
	 * read the configFile, evaluate all calculs and store all results in the table data
	 * @throws FileNotFoundException
	 * @throws ConfigFileException
	 */
	public void ProcessFile() throws FileNotFoundException, ConfigFileException {
		attributs = new ArrayList <String> ();
		operations = new ArrayList <String> ();
		splitData(attributs, operations);
		evaluate();
		updateData();
	}
	
	/**
	 * remove the key and his values in the list of values and list of keys
	 * @param att_src the key to remove and his values
	 * @return if can remove
	 */
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
	
	/**
	 * generate a list of values associated with a key in the data table
	 * @param attribut the key wanted for storage of values in the list
	 * @return the list containing values of the key
	 */
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
	
	/**
	 * cast if possible a string in Integer or double, cast empty string by 0
	 * @param s string to cast
	 * @return the Object containing cast of the string
	 */
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
            	if(s.trim().isEmpty()) return 0;
                return s;
            }
        }
    }
	
	/**
	 * set the concatenation between the res and att_src or values of att_src
	 * @param res the current res
	 * @param att_src the right operand to concatenate
	 * @return the result of concatenation
	 */
	private ArrayList<String> concatenate (ArrayList<String> res, String att_src) {
		ArrayList<String> vals = values.get(att_src);
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
	
	/**
	 * add values of res with values of att_src or just by att_src
	 * @param res the current result
	 * @param att_src the right operand to add on res
	 * @return the result of additions
	 * @throws ClassCastException if a value of an operand is not a number
	 * @throws NumberFormatException if a value of an operand is not a number
	 */
	private ArrayList<String> add (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = values.get(att_src);
		ArrayList<String> tmp;
		Object o1, o2;
		
		if(vals != null) { //att_src existe
			if(vals.size() > res.size()) {
				tmp = res;
				res = vals;
				vals = tmp;
			}
			
			for(int i = 0; i < vals.size(); i++) {
				o1 = ConfigManager.cast(res.get(i));
				o2 = ConfigManager.cast(vals.get(i));
				try {
					o1 = (double)o1 + (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 + (int)o2;
					res.set(i, Integer.toString((int) o1));
				}
				
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = ConfigManager.cast(res.get(i));
				o2 = ConfigManager.cast(att_src);
				try {
					o1 = (double)o1 + (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 + (int)o2;
					res.set(i, Integer.toString((int) o1));
				}
			}
		}
		
		return res;
	}
	
	/**
	 * substract values of res by values of att_src or just by att_src
	 * @param res the current result
	 * @param att_src the right operand to substract on res
	 * @return the result of additions
	 * @throws ClassCastException if a value of an operand is not a number
	 * @throws NumberFormatException if a value of an operand is not a number
	 */
	private ArrayList<String> substract (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = values.get(att_src);
		Object o1, o2;
		
		if(vals != null) { //att_src existe
			int min = (res.size() < vals.size())? res.size() : vals.size();
			for(int i = 0; i < min; i++) {
				o1 = ConfigManager.cast(res.get(i));
				o2 = ConfigManager.cast(vals.get(i));
				try {
					o1 = (double)o1 - (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 - (int)o2;
					res.set(i, Integer.toString((int) o1));
				}
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = ConfigManager.cast(res.get(i));
				o2 = ConfigManager.cast(att_src);
				try {
					o1 = (double)o1 - (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 - (int)o2;
					res.set(i, Integer.toString((int) o1));
				}
			}
		}
		
		return res;
	}
	
	/**
	 * divide values of res by values of att_src or just by att_src
	 * @param res the current result
	 * @param att_src the right operand to divide res
	 * @return the result of division
	 * @throws ClassCastException if a value of an operand is not a number
	 * @throws NumberFormatException if a value of an operand is not a number
	 */
	private ArrayList<String> divide (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = values.get(att_src);
		Object o1, o2;
		
		if(vals != null) { //att_src existe
			int min = (res.size() < vals.size())? res.size() : vals.size();
			for(int i = 0; i < min; i++) {
				o1 = ConfigManager.cast(res.get(i));
				o2 = ConfigManager.cast(vals.get(i));
				try {
					o1 = (double)o1 / (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 / (int)o2;
					res.set(i, Integer.toString((int) o1));
				}
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = ConfigManager.cast(res.get(i));
				o2 = ConfigManager.cast(att_src);
				try {
					o1 = (double)o1 / (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 / (int)o2;
					res.set(i, Integer.toString((int) o1));
				}
			}
		}
		
		return res;
	}
	
	/**
	 * multiply values of res by values of att_src or just by att_src
	 * @param res the current result
	 * @param att_src the right operand to multiply with values of res
	 * @return the result of multiplication
	 * @throws ClassCastException if a value of an operand is not a number
	 * @throws NumberFormatException if a value of an operand is not a number
	 */
	private ArrayList<String> multiply (ArrayList<String> res, String att_src) throws ClassCastException, NumberFormatException {
		ArrayList<String> vals = values.get(att_src);
		ArrayList<String> tmp;
		Object o1, o2;
		
		if(vals != null) { //att_src existe
			if(vals.size() > res.size()) {
				tmp = res;
				res = vals;
				vals = tmp;
			}
			for(int i = 0; i < vals.size(); i++) {
				o1 = ConfigManager.cast(res.get(i));
				o2 = ConfigManager.cast(vals.get(i));
				try {
					o1 = (double)o1 * (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 * (int)o2;
					res.set(i, Integer.toString((int) o1));
				}
			}
		}
		else { //att_src n'existe pas
			for(int i = 0; i < res.size(); i++) {
				o1 = ConfigManager.cast(res.get(i));
				o2 = ConfigManager.cast(att_src);
				try {
					o1 = (double)o1 * (double)o2;
					res.set(i, Double.toString((double) o1));
				}catch (ClassCastException e) {
					o1 = (int)o1 * (int)o2;
					res.set(i, Integer.toString((int) o1));
				}
			}
		}
		
		return res;
	}
	
	/**
	 * @param res the current result
	 * @param att_src the right operand to store in res
	 * @return the result containing att_src in addition
	*/
	private ArrayList<String> insert(ArrayList<String> res, String att_src) {
		ArrayList<String> vals = values.get(att_src);
		ArrayList<String> tmp;
		
		if(vals != null) { //att_src existe
			for(int i = 0; i < vals.size(); i++) {
				res.add(vals.get(i));
			}
		}
		else { //att_src n'existe pas
			res.add(att_src);
		}
		
		return res;
	}
}
