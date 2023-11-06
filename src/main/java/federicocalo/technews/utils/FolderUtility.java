package federicocalo.technews.utils;

import java.io.File;

public class FolderUtility {
    public static final String BASE_PATH ="./articoli";
    public static final String pathMillionair =  BASE_PATH + "/millionaire";

    public static final void mkdirArticle(){
        File baseFolder = new File(BASE_PATH);
        if (!baseFolder.exists()){
            baseFolder.mkdir();
            File pathMillionairFolder = new File(pathMillionair);
            if (!pathMillionairFolder.exists()){
                pathMillionairFolder.mkdir();
            }
        }
    }

    public static final String mkdirStr(String path){
        File folder = new File(path);
        if (!folder.exists()){
            folder.mkdir();
        }
        return path;
    }
}
