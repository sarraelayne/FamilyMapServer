package Data;

import DAO.DatabaseAccessException;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;

public class sNames {
    public String[] data;
    public String[] getSnames() {
        return data;
    }
    public String randomSurname() throws DatabaseAccessException {
        try {
            Gson gson = new Gson();
            Reader read = new FileReader("json\\snames.json");
            sNames snames = gson.fromJson(read, sNames.class);
            data = snames.getSnames();
        }
        catch (FileNotFoundException e) {
            throw new DatabaseAccessException("snames.json not found");
        }
        int i = new Random().nextInt(data.length);
        return data[i];
    }
}
