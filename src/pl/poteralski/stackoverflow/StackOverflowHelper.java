package pl.poteralski.stackoverflow;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by codeninja on 01.02.16.
 */
public class StackOverflowHelper {

    public String getSnippet(String selectedText) throws UnirestException {
        String title = removeHashTagsFromText(selectedText);
        String preparedHashTags = prepareHashTags(getHashTags(selectedText));
        HttpResponse<JsonNode> jsonArray = makeHttpRequest(title, preparedHashTags);
        return jsonArray.getBody().getArray().toString();
    }

    private HttpResponse<JsonNode> makeHttpRequest(String title, String preparedTags) throws UnirestException {
        return Unirest.get(StackExchangeUri.API_URL)
            .queryString(StackExchangeUri.SITE, StackExchangeUri.SITE_SO)
            .queryString(StackExchangeUri.ORDERING, StackExchangeUri.ORDERING_DESC)
            .queryString(StackExchangeUri.SORT, StackExchangeUri.SORT_ACTIVITY)
            .queryString(StackExchangeUri.ACCEPTED, StackExchangeUri.ACCEPTED_TRUE)
            .queryString(StackExchangeUri.TAGGED, preparedTags)
            .queryString(StackExchangeUri.TITLE, title)
            .asJson();
    }

    private List<String> getHashTags(String text){
        Pattern pattern = Pattern.compile("#(\\w+|\\W+)");
        Matcher mat = pattern.matcher(text);
        List<String> tags = new ArrayList<>();
        while (mat.find()) tags.add(mat.group(1));
        return tags;
    }

    private String prepareHashTags(List<String> hashTagsList){
        return String.join(";", hashTagsList);
    }

    private String removeHashTagsFromText(String text){
        return text.replaceAll("#(\\w+|\\W+)", "");
    }
}
