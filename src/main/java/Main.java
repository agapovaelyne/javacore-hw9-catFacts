import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet("https://cat-fact.herokuapp.com/facts");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Arrays.stream(response.getAllHeaders())
                .forEach(System.out::println);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<CatFact> posts = null;
        try {
            posts = mapper.readValue(
                    response.getEntity().getContent(), new TypeReference<List<CatFact>>() {}
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("All posts:");
        posts.forEach(System.out::println);
        System.out.println("\nFiltered posts:");

        posts.stream()
                //.filter(fact -> fact.getUpvotes() != null && fact.getUpvotes() > 0);
                //.filter(fact -> fact.getUsed() == true)
                .filter(fact ->  fact.getStatusVerified() == true && fact.getSentCount() > 0)
                .forEach(System.out::println);
    }
}

/*

в ответе от api сейчас отсутствуют поля upvotes и userUpvoted,
поэтому фильтрация по ним согласно задаче закомменчена в строке 5, а другие варианты фильтрации приведены в строках 52,53

{"status":{"verified":true,"sentCount":1},
"type":"cat","deleted":false,
"_id":"58e008800aac31001185ed07","user":"58e007480aac31001185ecef",
"text":"Wikipedia has a recording of a cat meowing, because why not?",
"__v":0,
"source":"user",
"updatedAt":"2020-08-23T20:20:01.611Z","createdAt":"2018-03-06T21:20:03.505Z",
"used":false}
 */