package federicocalo.technews;

import federicocalo.technews.millionaire.MillionaireWS;
import federicocalo.technews.utils.FolderUtility;
import federicocalo.technews.wiredit.WiredItWS;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FolderUtility.mkdirArticle();
        MillionaireWS millionaireWS = new MillionaireWS();
        WiredItWS wiredItWS = new WiredItWS();
        /*try {
            millionaireWS.startWS();
            wiredItWS.startWS();
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        try {
            wiredItWS.startWS();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}