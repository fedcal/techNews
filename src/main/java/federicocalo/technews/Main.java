package federicocalo.technews;

import federicocalo.technews.millionaire.MillionaireWS;
import federicocalo.technews.utils.FolderUtility;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FolderUtility.mkdirArticle();
        MillionaireWS millionaireWS = new MillionaireWS();
        try {
            millionaireWS.startWS();
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}