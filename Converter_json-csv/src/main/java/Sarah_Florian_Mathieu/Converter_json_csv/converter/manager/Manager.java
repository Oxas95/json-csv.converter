package Sarah_Florian_Mathieu.Converter_json_csv.converter.manager;

public class Manager {
	/**
	 * separator between a key and his subKey
	 */
    public static final String separator = "__";

	/**
	 * how many lines
	 */
	protected int hauteur = 2;
	
	/**
	 * how many values by line
	 */
	protected int largeur = 0;
	
	/**
	 * store values of data file like in csv
	 */
	protected String [][] data = null;
    
	/**
	 * all values of array separated with ',' and lines with '\n'
	 */
	public String toString() {
		int i, j;
		String s = "";
		for(j = 0; j < hauteur; j++) {
        	for(i = 0; i < largeur - 1; i++) {
        		s += data[i][j] + ',';
        	}
        	s += data[i][j] + '\n';
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
	 * @throws IndexOutOfBoundsException if i or j is invalid
	 */
	public String get(int i, int j) throws IndexOutOfBoundsException {
		return data[i][j];
	}
	
	/**
	 * set a value in the array
	 * @param newString string to store
	 * @param i which row
	 * @param j which line
	 */
	public void set(String newString, int i, int j) {
		try {
        	data[i][j] = newString;
        }catch (Exception e) {}
	}
	
	/**
	 * copy the array and return it
	 * @return copy of array
	 */
	public String[][] getArrayCopy(){
		if(data == null) return null;
		String[][] copy = new String[largeur][hauteur];
		int i,j;
		for(j = 0; j < hauteur; j++) {
			for(i = 0; i <largeur; i++) {
				copy[i][j] = (data[i][j] == null)? "" : data[i][j];
			}
		}
		return copy;
	}
	
}
