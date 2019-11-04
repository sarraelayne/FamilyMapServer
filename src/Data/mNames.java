package Data;

import DAO.DatabaseAccessException;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;

public class mNames {
    public String[] data;

    public String[] getMales() {
        return data;
    }
    public String randomMName() throws DatabaseAccessException {
        try {
            Gson gson = new Gson();
            Reader read = new FileReader("json\\mnames.json");
            mNames males = gson.fromJson(read, mNames.class);
            data = males.getMales();
        }
        catch (FileNotFoundException e) {
            throw new DatabaseAccessException("mnames.json not found.");
        }
        int i = new Random().nextInt(data.length);
        return data[i];
    }
}
