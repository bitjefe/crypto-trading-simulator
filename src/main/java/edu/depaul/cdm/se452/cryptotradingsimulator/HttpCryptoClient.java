package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import io.github.cdimascio.dotenv.Dotenv;

public class HttpCryptoClient {
    public static void main(String[] args) throws IOException, InterruptedException {

        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("COINMC_API_KEY");    

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=10"))
                .GET()
                .header("X-CMC_PRO_API_KEY", apiKey)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("---");
        System.out.println(response.statusCode());
        System.out.println(response.body());
        
        System.out.println("---");
    }
}
