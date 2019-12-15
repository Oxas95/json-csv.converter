package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

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

public class CsvManager extends Manager{
	
	/**
	 * read a csv file and store values in an array
	 * @param existingFile which file to read
	 * @throws IOException if there is a problem when reading file
	 * @throws NullPointerException existingFile is null
	 * @throws IllegalArgumentException existingFile is an existing file
	 * @throws CsvException if too many values in a line
	 */
	public CsvManager(String existingFile) throws IOException, NullPointerException, IllegalArgumentException, CsvException {
		if(existingFile != null) {
			if(existingFile.endsWith(".csv") == false) throw new IllegalArgumentException();
		}
		else throw new NullPointerException ();
		
		Reader reader = Files.newBufferedReader(Paths.get(existingFile));
        CSVParser header = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim().withIgnoreEmptyLines());
        List<CSVRecord> values = header.getRecords();
        
        largeur = header.getHeaderNames().size();
        hauteur = values.size() + 1;
        
        
        this.data = new String[largeur][hauteur];
        
        int i,j;
        for(j = 1; j < hauteur; j++) {
        	if(values.get(j-1).size() != largeur) throw new CsvException();
        	for(i = 0; i < largeur; i++) {
        		this.data[i][j] = new String(values.get(j-1).get(i));
        	}
        }
        
        for(i = 0; i < largeur; i++) {
        	this.data[i][0] = new String(header.getHeaderNames().get(i));
        }
        
        header.close();
        reader.close();
        header.close();
        
	}
	
	/**
	 * take the path of new csv file, array containing values and dimensions of the array
	 * @param newPath in which file to write
	 * @param csv array containing values
	 * @param width how many values in a line
	 * @param height how many lines
	 * @throws IOException if there is a problem when writing file
	 * @throws IllegalArgumentException newPath is an existing file
	 * @throws NullPointerException newPath is null
	 */
	public static void parseCsvFile(String newPath, String[][] csv, int width, int height) throws IOException, IllegalArgumentException, NullPointerException {
		if(newPath == null) throw new NullPointerException ();
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
