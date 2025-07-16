package se233.chapter2.controller;

import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

import java.util.ArrayList;
import java.util.List;

public class Initialize {

    /**
     * Creates a Currency object, fetches its historical data, sets the current rate,
     * and returns the populated object.
     * @return A fully initialized Currency object.
     */
    public static List<Currency> initializeApp() {
        // Create a new currency object for USD.
        Currency c = new Currency("USD");


        List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(), 8);


        c.setHistorical(cList);


        c.setCurrent(cList.get(cList.size() - 1));


        List<Currency> currencyList = new ArrayList<>();
        currencyList.add(c);
        return currencyList;
    }
}

