package Sarah_Florian_Mathieu.Converter_json_csv.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class CsvManager {
	
	/**
	 * how many lines
	 */
	private int hauteur = 0;
	
	/**
	 * how many values by line
	 */
	private int largeur = 0;
	
	/**
	 * store values of csv file
	 */
	private String [][] csv = null;
	
	/**
	 * read a csv file and store values in an array
	 * @param existingFile which file to read
	 * @throws IOException if there is a problem when reading file
	 */
	public CsvManager(String existingFile) throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(existingFile));
        CSVParser header = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim().withIgnoreEmptyLines());
        List<CSVRecord> values = header.getRecords();
        
        largeur = header.getHeaderNames().size();
        hauteur = values.size() + 1;
        
        
        csv = new String[largeur][hauteur];
        
        
        int i,j;
        for(j = 1; j < hauteur; j++) {
        	for(i = 0; i < largeur; i++) {
        		csv[i][j] = new String(values.get(j-1).get(i));
        	}
        }
        
        for(i = 0; i < largeur; i++) {
        	csv[i][0] = new String(header.getHeaderNames().get(i));
        }
        
        reader.close();
        header.close();
        
	}
	
	/**
	 * all values of array separated with ',' and lines with '\n'
	 */
	public String toString() {
		int i, j;
		String s = "";
		for(j = 0; j < hauteur; j++) {
        	for(i = 0; i < largeur - 1; i++) {
        		s += csv[i][j] + ',';
        	}
        	s += csv[i][j] + '\n';
        }
		return s;
	}
	
	/**
	 * getter for width of array
	 * @return width of array
	 */
	public int getWidth() {
		return largeur;
	}
	
	/**
	 * getter for height of array
	 * @return height of array
	 */
	public int getHeight() {
		return hauteur;
	}
	
	/**
	 * get a value in the array
	 * @param i which row
	 * @param j which line
	 * @return value in the array at row i, line j if possible. Else return null
	 */
	public String get(int i, int j) {
		try {
			return csv[i][j];	
		}
		catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * set a value in the array
	 * @param newString string to store
	 * @param i which row
	 * @param j which line
	 */
	public void set(String newString, int i, int j) {
        System.out.println(csv);
        try {
        	csv[i][j] = newString;
        }catch (Exception e) {}
	}
	
	/**
	 * copy the array and return it
	 * @return copy of array
	 */
	public String[][] getArrayCopy(){
		String[][] copy = new String[largeur][hauteur];
		int i,j;
		for(j = 0; j < hauteur; j++) {
			for(i = 0; i <largeur; i++) {
				copy[i][j] = new String(csv[i][j]);
			}
		}
		return copy;
	}
	
	/**
	 * take the path of new csv file, array containing values and dimensions of the array
	 * @param newPath in which file to write
	 * @param csv array containing values
	 * @param width how many values in a line
	 * @param height how many lines
	 * @throws IOException if there is a problem when writing file
	 * @throws IllegalArgumentException newPath is null pointer or is an existing file
	 */
	public static void parseCsvFile(String newPath, String[][] csv, int width, int height) throws IOException, IllegalArgumentException {
		if(newPath == null) throw new IllegalArgumentException ();
		if(newPath.endsWith(".csv") == false) newPath += ".csv";
		File f = new File(newPath);
		if(f.exists()) {
			System.err.println(newPath + " already exists, save undone.");
			throw new IllegalArgumentException ();
		}
		int i;
		ArrayList <String> l;
		
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(newPath));
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
		
		for(int j = 0; j < height; j++) {
			l = new ArrayList <String> ();
			for(i = 0; i < width; i++) {
				l.add(csv[i][j]);
			}
			csvPrinter.printRecord(l);
		}
		csvPrinter.close(true);
	}
}
