import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RESTfulClient {
    private HttpClient httpClient;

    public RESTfulClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(Version.HTTP_2)
                .followRedirects(Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public HttpResponse<String> get(String url, Map<String, String> headers) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }

        HttpRequest getRequest = requestBuilder.build();

        return httpClient.send(getRequest, BodyHandlers.ofString());
    }

    public HttpResponse<String> post(String url, String requestBody, Map<String, String> headers) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(requestBody));

        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }

        HttpRequest postRequest = requestBuilder.build();

        return httpClient.send(postRequest, BodyHandlers.ofString());
    }

    public HttpResponse<String> put(String url, String requestBody, Map<String, String> headers) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(requestBody));

        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }

        HttpRequest putRequest = requestBuilder.build();

        return httpClient.send(putRequest, BodyHandlers.ofString());
    }

    public HttpResponse<Void> delete(String url, Map<String, String> headers) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE();

        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }

        HttpRequest deleteRequest = requestBuilder.build();

        return httpClient.send(deleteRequest, BodyHandlers.discarding());
    }

    public static void main(String[] args) {
        RESTfulClient client = new RESTfulClient();

        try {
            // Example usage
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer token");

            HttpResponse<String> response = client.get("https://jsonplaceholder.typicode.com/posts/1", headers);

            System.out.println("GET Request Status Code: " + response.statusCode());
            System.out.println("GET Request Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
