package service;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ThreadLocalRandom;

public class GetLocation {
    static Location[] eventsArray = null;

    public class eventsArrayClass {
        Location[] data;
    }

    public class Location {
        String country;
        String city;
        float latitude;
        float longitude;
    }

    static {
        if(eventsArray == null){
            try {
                eventsArray = jsonToClass("json" + File.separator + "locations.json").data;
            }
            catch(Exception e) {
                e.toString();
                e.printStackTrace();;
            }
        }
    }

    public static eventsArrayClass jsonToClass(String fileName) throws Exception{
        File f = new File(fileName);
        FileReader fReader = new FileReader(f);
        Gson gson = new Gson();
        return gson.fromJson(fReader, eventsArrayClass.class);
    }

    static Location getLocation() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, eventsArray.length);
        return eventsArray[randomNum];
    }
}
