package pl.poteralski.stackoverflow;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 *
 * Created by codeninja on 01.02.16.
 */
public class StackOverflowHelper {
    public String getSnippet(String selectedText) throws UnirestException {
        HttpResponse<JsonNode> jsonArray = Unirest.get("https://api.stackexchange.com/2.2/search/advanced")
                .queryString("order", "desc")
                .queryString("sort", "activity")
                .queryString("accepted", "True")
                .queryString("tagged", "python%3Blxml")
                .queryString("title", "parse%20html")
                .queryString("site", "stackoverflow")
                .asJson();
        return jsonArray.getBody().getArray().toString();
    }
}
