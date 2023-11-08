package federicocalo.technews.wiredit;

import federicocalo.technews.utils.FolderUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WiredItWS {
    private String urlSiteScienza = "https://www.wired.it/scienza";
    private List<String> linksArticle = new ArrayList<>();

    public WiredItWS(){}

    public void startWS() throws IOException {
        WebDriver mainSite = new ChromeDriver();
        mainSite.get(urlSiteScienza);

        getArticles(mainSite);
        saveArticle();

        
    }

    private void saveArticle() throws IOException {
        int countArticle = 1;
        for (String url : linksArticle){

            if(url.contains(urlSiteScienza))
                continue;

            System.out.println("===============================================================================\n");
            System.out.println("WiredItScienza: "+countArticle+"/"+linksArticle.size()+"\n");
            System.out.println(url);
            System.out.println("===============================================================================\n");

            WebDriver article = new ChromeDriver();
            article.get(url);
            try {
                String titleArticle = article.findElement(By.cssSelector("h1.BaseWrap-sc-gjQpdd.BaseText-ewhhUZ.ContentHeaderHed-NCyCC.iUEiRd.kKjIpR.kctZMs")).getText();
                String subTitle = article.findElement(By.cssSelector("div.ContentHeaderDek-bIqFFZ.dHmGIY")).getText();

                WebElement bodyArticle = article.findElement(By.cssSelector("div.body__inner-container"));
                List<WebElement> h2Elements = bodyArticle.findElements(By.cssSelector("h2"));
                List<WebElement> pElements = bodyArticle.findElements(By.cssSelector("p"));
                downloadArticle(url,titleArticle,subTitle,h2Elements,pElements,countArticle);
            } catch (RuntimeException de){
                article.close();
                continue;
            } catch (Exception e){
                article.close();
                continue;
            }


            article.close();
            countArticle++;
        }
    }

    private void downloadArticle(String url,String titleArticle, String subTitle,
                                 List<WebElement> h2Elements, List<WebElement> pElements,
                                 int countArticle) throws IOException {
        createFolder(FolderUtility.pathWiredItScienza+"/"+countArticle);
        FileWriter articleWriter = new FileWriter(FolderUtility.pathWiredItScienza+"/"+countArticle+"/"+countArticle+".txt");
        articleWriter.write(url+"\n\n");
        articleWriter.write(titleArticle+"\n\n");
        articleWriter.write(subTitle+"\n\n");
        for (int k = 0; k < pElements.size(); k++){
            articleWriter.write(pElements.get(k).getText()+"\n\n");
            if (k < h2Elements.size()){
                articleWriter.write(h2Elements.get(k).getText());
            }
        }
        articleWriter.close();
    }

    private void createFolder(String s) {
        File path = new File(s);
        if(!path.exists()){
           if(!path.getParentFile().mkdir())
               path.mkdir();
        }
    }


    private void getArticles(WebDriver urlSiteScienza) {
        WebElement summaryCollage = urlSiteScienza.findElement(By.cssSelector("div.SummaryCollageFiveWrapper-dTAWld.hLKGJm.summary-collage-five"));
        List<WebElement> linksSummaryArticle = summaryCollage.findElements(By.tagName("a"));
        for (WebElement link : linksSummaryArticle){
            if(!linksArticle.contains(link.getAttribute("href"))){
                linksArticle.add(link.getAttribute("href"));
            }

        }

        List<WebElement> summaryArticles = urlSiteScienza.findElements(By.cssSelector("div.summary-list__items"));

        for (WebElement summary : summaryArticles){
            List<WebElement> links = summary.findElements(By.tagName("a"));
            for(WebElement link : links){
                if(!linksArticle.contains(link.getAttribute("href"))){
                    linksArticle.add(link.getAttribute("href"));
                }
            }
        }
        urlSiteScienza.close();
    }
}
