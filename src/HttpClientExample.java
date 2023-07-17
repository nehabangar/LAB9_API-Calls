import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class HttpClientExample {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(Version.HTTP_2)
            .followRedirects(Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) {
        HttpClient httpClientDefault = HttpClient.newHttpClient();

        HttpClient httpClientCustom = HttpClient.newBuilder()
                .followRedirects(Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                    .build();

            HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());

            System.out.println("GET Request Status Code: " + getResponse.statusCode());
            System.out.println("GET Request Body: " + getResponse.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            String postRequestBody = "{\"title\":\"test title\",\"body\":\"test body\",\"userId\":1}";

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(postRequestBody))
                    .build();

            HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());

            System.out.println("POST Request Status Code: " + postResponse.statusCode());
            System.out.println("POST Request Body: " + postResponse.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            String putRequestBody = "{\"id\":1, \"title\":\"updated title\",\"body\":\"updated body\",\"userId\":1}";

            HttpRequest putRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(putRequestBody))
                    .build();

            HttpResponse<String> putResponse = httpClient.send(putRequest, BodyHandlers.ofString());

            System.out.println("PUT Request Status Code: " + putResponse.statusCode());
            System.out.println("PUT Request Body: " + putResponse.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            HttpRequest deleteRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                    .DELETE()
                    .build();

            HttpResponse<Void> deleteResponse = httpClient.send(deleteRequest, BodyHandlers.discarding());

            System.out.println("DELETE Request Status Code: " + deleteResponse.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            HttpRequest asyncGetRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                    .build();

            httpClient.sendAsync(asyncGetRequest, BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
