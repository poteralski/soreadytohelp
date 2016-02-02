package pl.poteralski.stackoverflow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 *
 * Created by codeninja on 02.02.16.
 */
public class StackOverflowParser {
    private Document website = null;

    private Document getWebsite(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();
    }

    public void parse(String url){
        try {
            website = getWebsite(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBestAnswer(){
        if (website != null){
            return this.website.select(".answer").first().select("code").text();
        } else return "I can't help You, I'm a horse!";
    }
}
