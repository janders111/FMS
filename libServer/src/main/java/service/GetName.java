package service;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ThreadLocalRandom;

public class GetName {
    static String[] fNamesArray = null;
    static String[] mNamesArray = null;
    static String[] sNamesArray = null;

    public class namesArrClass {
        String[] data;
    }

    static {
        if(fNamesArray == null || mNamesArray == null || sNamesArray == null){
            try {
                fNamesArray = jsonToClass("json" + File.separator + "fnames.json").data;
                mNamesArray = jsonToClass("json" + File.separator + "mnames.json").data;
                sNamesArray = jsonToClass("json" + File.separator + "snames.json").data;
            }
            catch(Exception e) {
                e.printStackTrace();;
            }
        }
    }

    static String getFemaleName() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, fNamesArray.length);
        return fNamesArray[randomNum];
    }
    static String getMaleName() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, mNamesArray.length);
        return mNamesArray[randomNum];
    }
    static String getSurname() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, sNamesArray.length);
        return sNamesArray[randomNum];
    }

    public static namesArrClass jsonToClass(String fileName) throws Exception{
        File f = new File(fileName);
        if(!f.exists()) {
            throw new Exception("File not found");
        }

        FileReader fReader = new FileReader(f);
        Gson gson = new Gson();
        namesArrClass result = gson.fromJson(fReader, namesArrClass.class);
        fReader.close();
        return result;
    }
}
