package Data;

import DAO.DatabaseAccessException;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;

public class fNames {
    public String[] data;
    private String[] getFemales() {
        return data;
    }
    public String getRandomName() throws DatabaseAccessException {
        try {
            Gson gson = new Gson();
            Reader read = new FileReader("json\\fnames.json");
            fNames femNames = gson.fromJson(read, fNames.class);
            data = femNames.getFemales();
        }
        catch (FileNotFoundException e) {
            throw new DatabaseAccessException("fNames.json not found.");
        }
        int i = new Random().nextInt(data.length);
        return data[i];
    }
}
