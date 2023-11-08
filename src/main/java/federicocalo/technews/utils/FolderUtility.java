package federicocalo.technews.utils;

import java.io.File;

public class FolderUtility {
    public static final String BASE_PATH ="./articoli";
    public static final String pathMillionair =  BASE_PATH + "/millionaire";
    public static final String pathWiredIt =  BASE_PATH + "/wiredIt";
    public static final String pathWiredItScienza =  pathWiredIt+"/scienza";

    public static final void mkdirArticle(){
        File baseFolder = new File(BASE_PATH);
        if (!baseFolder.exists()){
            baseFolder.mkdir();
        }
        File pathMillionairFolder = new File(pathMillionair);
        if (!pathMillionairFolder.exists()){
            pathMillionairFolder.getParentFile().mkdir();
        }

        File pathWiredItFolder = new File(pathWiredIt);
        if (!pathWiredItFolder.exists()){
            pathWiredItFolder.getParentFile().mkdir();
        }

        File pathWiredItScienzaFolder = new File(pathWiredItScienza);
        if (!pathWiredItScienzaFolder.exists()){
            pathWiredItScienzaFolder.getParentFile().mkdir();
        }
    }

    public static final String mkdirStr(String path){
        File folder = new File(path);
        if (!folder.exists()){
            folder.getParentFile().mkdir();
        }
        return path;
    }
}
