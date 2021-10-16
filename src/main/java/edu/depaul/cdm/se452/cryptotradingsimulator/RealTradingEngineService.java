package edu.depaul.cdm.se452.cryptotradingsimulator;

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
    private final AppCacheRepository appCacheRepository;

    private String cacheKey = "coinmc_api_call";

    public RealTradingEngineService(AppCacheRepository appCacheRepository) {
        this.appCacheRepository = appCacheRepository;
    }

    @Override
    public Double fetchRemotePrice(String cryptoTicker) {
        return getPrices().get(cryptoTicker);
    }

    @Override
    public HashMap<String, Double> getPrices() {
        if (AppCache.isCached(cacheKey, appCacheRepository)) {
            return parseResponse(AppCache.getCacheValue(cacheKey, appCacheRepository));
        }

        String apiResponse = this.callApi();
        AppCache.cacheItem(cacheKey, apiResponse, 1000, appCacheRepository);
        return fetchTopTenCoins();
    }

    public HashMap<String, Double> fetchTopTenCoins() {
        return parseResponse(this.callApi());
    }

    public String callApi() {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("COINMC_API_KEY");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=10"))
                .GET()
                .header("X-CMC_PRO_API_KEY", apiKey)
                .build();

        try {

            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    private HashMap<String, Double> parseResponse(String apiResponse) {
        HashMap<String, Double> prices = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        CoinMCResponse items = null;

        try {
            items = mapper.readValue(apiResponse, CoinMCResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        items.data.stream().forEach(d -> {
            prices.put(d.symbol, d.quote.usd.price);
        });

        return prices;
    }
}