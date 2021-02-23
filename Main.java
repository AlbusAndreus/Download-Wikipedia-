package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

public class Main {
    static int MAX_Depth = 99999;
    static HashSet<String> links = new HashSet<>();
    static int pageElement = 0;
    public static void main(String[] args) {
       getPageLinks("https://www.wikipedia.org", 0);

        }
    public static void getPageLinks(String url, int depth) {
         pageElement = 0;
        int indexOfM = 0;
        /*for (int i = 0; i <= url.length() - 1; i++) {
            if (url.charAt(i) == 'o') {
                int index = url.indexOf(i);
                if (url.substring(index+1, index + 3).equals("org")) {
                    indexOfM = index + 2;
                }
            }
        }*/
        //url.substring(0, indexOfM);

        String baseURL = "https://www.wikipedia";
        if (!links.contains(url) && (depth < MAX_Depth)) {
        links.add(url);


            try {
                //URL url = new URL("HTTPS//www.wikipedia.com");

                Document document = Jsoup.connect(url).get();

                Elements linksOnPage = document.select("a[href]");

                URL URLObject = new URL(url);
                BufferedReader readr =
                        new BufferedReader(new InputStreamReader(URLObject.openStream()));

                // Enter filename in which you want to download
                File file = new File("C:\\Wikipedia\\" + "URL" + pageElement + depth + ".html");
                if(!file.exists()){
                    file.createNewFile();
                }
                BufferedWriter writer =
                        new BufferedWriter(new FileWriter(file));

                // read each line from stream till end
                String line;
                while ((line = readr.readLine()) != null) {
                    writer.write(line);
                }
                pageElement++;
                readr.close();
                writer.close();
                depth++;
                for (Element page : linksOnPage) {
                    String currentPage = page.attr("abs:href");
                    if(currentPage.contains("wikipedia") || currentPage.contains("wikimedia") && currentPage.endsWith("/")) {
                        getPageLinks(page.attr("abs:href"), depth);
                        pageElement++;
                    }else{
                        System.out.println(page.attr("abs:href"));
                    }
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
