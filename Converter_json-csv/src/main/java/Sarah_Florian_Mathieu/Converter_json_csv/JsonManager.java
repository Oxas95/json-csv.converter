package Sarah_Florian_Mathieu.Converter_json_csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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

    private static void generateJsonObject(){
        String s = "", tj = "traitementJson";
        File f = new File(tj);
        f.listFiles();
        File[] sf = f.listFiles();
        JSONObject jo = new JSONObject();

    }

    public static void parseJsonFile(String newPath, String[][] csv, int width, int height) throws IOException {
        parseTreeJson(csv,width,height);
    }
}