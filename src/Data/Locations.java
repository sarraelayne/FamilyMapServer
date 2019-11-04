package Data;

import DAO.DatabaseAccessException;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;

public class Locations {
    public class singleLoc {
        public String lat;
        public String longitude;
        public String city;
        public String country;
    }
    public singleLoc[] data;

    public singleLoc[] getData() {
        return data;
    }
    public singleLoc randomLoc() throws DatabaseAccessException {
        try {
            Gson gson = new Gson();
            Reader read = new FileReader("json\\locations.json");
            Locations locData = gson.fromJson(read, Locations.class);
            data = locData.getData();
        }
        catch (FileNotFoundException e) {
            throw new DatabaseAccessException("Locations.json not found.");
        }
        int i = new Random().nextInt(data.length);
        return data[i];
    }

}
