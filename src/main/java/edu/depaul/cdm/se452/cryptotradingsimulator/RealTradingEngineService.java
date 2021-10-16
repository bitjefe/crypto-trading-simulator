package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import edu.depaul.cdm.se452.cryptotradingsimulator.dto.CoinMCResponse.CoinMCResponse;
import io.github.cdimascio.dotenv.Dotenv;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RealTradingEngineService implements TradingEngineService {
    private HashMap<String, Double> prices = new HashMap<>();

    @Override
    public Double fetchRemotePrice(String cryptoTicker) {
        return prices.get(cryptoTicker);
    }

    @Override
    public HashMap<String, Double> getPrices() {
        return prices;
    }

    public HashMap<String, Double> fetchTopTenCoins() {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("COINMC_API_KEY");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=10"))
                .GET()
                .header("X-CMC_PRO_API_KEY", apiKey)
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("---");
            String apiResponse = response.body();
            ObjectMapper mapper = new ObjectMapper();
            CoinMCResponse items = mapper.readValue(apiResponse, CoinMCResponse.class);

            prices = new HashMap<>();
            items.data.stream().forEach(d -> {
                prices.put(d.symbol, d.quote.usd.price);
            });

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return prices;
    }

    public void setPrice(String cryptoTicker, Double price) {
        prices.put(cryptoTicker, price);
    }
}