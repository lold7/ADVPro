package se233.chapter2.controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import se233.chapter2.model.CurrencyEntity;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class FetchData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<CurrencyEntity> fetchRange(String symbol, int N) {
        String dateEnd = LocalDate.now().format(formatter);
        String dateStart = LocalDate.now().minusDays(N).format(formatter);
        String urlStr = String.format("https://cmu.to/SE233currencyapi?base=THB&symbol=%s&start_date=%s&end_date=%s", symbol, dateStart, dateEnd);
        List<CurrencyEntity> histList = new ArrayList<>();
        try {
            // Fetch the JSON response from the URL as a String.
            String retrievedJson = IOUtils.toString(new URL(urlStr), Charset.defaultCharset());

            JSONObject jsonOBJ = new JSONObject(retrievedJson).getJSONObject("rates");

            // Create an iterator to loop through the keys (dates) in the "rates" object.
            Iterator keysToCopyIterator = jsonOBJ.keys();

            // Loop through each date in the JSON response.
            while (keysToCopyIterator.hasNext()) {
                String key = (String) keysToCopyIterator.next(); // The key is the date string
                Double rate = Double.parseDouble(jsonOBJ.get(key).toString());
                // Create a new CurrencyEntity and add it to the list.
                histList.add(new CurrencyEntity(rate, key));
            }

            // Sort the list of currency entities by date in ascending order.
            histList.sort(new Comparator<CurrencyEntity>() {
                @Override
                public int compare(CurrencyEntity o1, CurrencyEntity o2) {
                    return o1.getTimestamp().compareTo(o2.getTimestamp());
                }
            });
        } catch (MalformedURLException e) {
            System.err.println("Encounter a Malformed Url exception");
        } catch (IOException e) {
            System.err.println("Encounter an IO exception");
        }

        return histList;
    }
}
