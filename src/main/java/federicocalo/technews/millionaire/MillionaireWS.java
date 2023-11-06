package federicocalo.technews.millionaire;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import federicocalo.technews.utils.FolderUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class MillionaireWS {
    private String urlSite = "https://www.millionaire.it/";
    private List<String> linksArticle = new ArrayList<>();

    public MillionaireWS(){}

    public void startWS() throws InterruptedException, IOException {
        WebDriver mainSite = new ChromeDriver();
        mainSite.get(urlSite);

        popolaLinkArticleList(mainSite);
        mainSite.close();
        saveArticle();
    }

    private void saveArticle() throws IOException {
        int countArticle = 1;
        for (String urlStr : linksArticle){
            System.out.println("===============================================================================\n");
            System.out.println("MillionaireWS: "+String.valueOf(countArticle)+"/"+String.valueOf(linksArticle.size())+"\n");
            System.out.println("===============================================================================\n");
            if (urlStr.contains("author")||urlStr.contains("newsletter-new")){
                continue;
            }
            WebDriver article = new ChromeDriver();
            article.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            article.get(urlStr);

            String path = FolderUtility.mkdirStr(FolderUtility.pathMillionair+"/"+String.valueOf(countArticle));
            WebElement articleFull = article.findElement(By.cssSelector("div.elementor-widget-wrap.elementor-element-populated"));
            String srcImg = null;
            try{
              srcImg = articleFull.findElement(By.tagName("img")).getAttribute("src");
            } catch (RuntimeException y){
                article.close();
                article.get(urlStr);
                articleFull = article.findElement(By.cssSelector("div.elementor-widget-wrap.elementor-element-populated"));
                srcImg = articleFull.findElement(By.tagName("img")).getAttribute("src");
            } catch (Exception e){
                article.close();
                article.get(urlStr);
                articleFull = article.findElement(By.cssSelector("div.elementor-widget-wrap.elementor-element-populated"));
                srcImg = articleFull.findElement(By.tagName("img")).getAttribute("src");
            }

            String titleArticle = articleFull.findElement(By.tagName("h1")).getText();
            List<WebElement> pArticle = articleFull.findElement(By.cssSelector("div.elementor-element.elementor-element-1bcc915.single-post-content.elementor-widget__width-inherit.elementor-widget-mobile__width-inherit.elementor-widget-tablet__width-inherit.elementor-widget.elementor-widget-theme-post-content")).findElements(By.tagName("p"));
            WebElement trisScrittoDa = articleFull.findElement(By.cssSelector("div.elementor-widget-wrap.elementor-element-populated"));


            FileWriter fileWriter = new FileWriter(FolderUtility.pathMillionair+"/"+String.valueOf(countArticle)+"/testo.txt");
            fileWriter.write(urlStr+"\n\n");
            fileWriter.write(titleArticle+"\n\n");
            List<WebElement> trisElements = trisScrittoDa.findElements(By.className("elementor-widget-container"));
            for (WebElement e : trisElements){
                fileWriter.write(e.getText());
            }
            fileWriter.write("\n\n");
            for(WebElement e : pArticle){
                fileWriter.write(e.getText()+"\n\n");
            }
            fileWriter.close();
            try{
                URL urlImage = new URL(srcImg);
                BufferedImage saveImage = ImageIO.read(urlImage);
                if(srcImg.contains("jpg")) {
                    ImageIO.write(saveImage, "jpg", new File(path + "/" + countArticle + ".jpg"));
                } else if(srcImg.contains("png")){
                    ImageIO.write(saveImage, "png", new File(path+"/"+countArticle + ".png"));
                } else if (srcImg.contains("jpeg")) {
                    ImageIO.write(saveImage, "jpeg", new File(path+"/"+countArticle + ".jpeg"));
                }else if (srcImg.contains("webp")) {
                    ImageIO.write(saveImage, "webp", new File(path+"/"+countArticle + ".webp"));
                }
                article.close();
            }catch (IIOException e){
                article.quit();
                WebDriver article2 = new ChromeDriver();
                article2.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
                article2.get(urlStr);

                WebElement articleFull2 = article2.findElement(By.cssSelector("div.elementor-widget-wrap.elementor-element-populated"));
                String srcImg2 = articleFull2.findElement(By.tagName("img")).getAttribute("src");
                URL urlImage = new URL(srcImg2);
                BufferedImage saveImage = ImageIO.read(urlImage);
                if(srcImg2.contains("jpg")) {
                    ImageIO.write(saveImage, "jpg", new File(path + "/" + countArticle + ".jpg"));
                } else if(srcImg2.contains("png")){
                    ImageIO.write(saveImage, "png", new File(path+"/"+countArticle + ".png"));
                } else if (srcImg2.contains("jpeg")) {
                    ImageIO.write(saveImage, "jpeg", new File(path+"/"+countArticle + ".jpeg"));
                }else if (srcImg2.contains("webp")) {
                    ImageIO.write(saveImage, "webp", new File(path+"/"+countArticle + ".webp"));
                }
                article2.close();
            }
            countArticle++;
        }
    }

    private void popolaLinkArticleList(WebDriver mainSite) {
        List<WebElement> articles = mainSite.findElements(By.tagName("article"));
        for(WebElement card : articles){
            try {
                linksArticle.add(card.findElement(By.tagName("a")).getAttribute("href"));
            }catch (Exception e){

            }
        }

        List<Integer> indici = new ArrayList<>();
        for(int i =0; i<linksArticle.size();i++){
            String link = linksArticle.get(i);
            boolean patternFrequenti = link.contains("/newsletter-new") || link.contains("/author/redazione/") ||
                    link.contains("/author/");

            boolean patternMesi = link.contains("it/gennaio-") || link.contains("it/febbraio-") || link.contains("it/marzo-") ||
                    link.contains("it/aprile-") || link.contains("it/maggio-") || link.contains("it/giugno-") ||
                    link.contains("it/luglio-") || link.contains("it/agosto-") || link.contains("it/settembre-") ||
                    link.contains("it/ottobre-") || link.contains("it/novembre-") || link.contains("it/dicembre-");

            if (patternFrequenti || patternMesi){
                indici.add(i);
            }
        }


        for(int i=indici.size()-1;i>=0;i--){
            linksArticle.remove(linksArticle.get(indici.get(i)));
        }
    }
}
