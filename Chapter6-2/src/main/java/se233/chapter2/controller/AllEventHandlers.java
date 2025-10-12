package se233.chapter2.controller;

import javafx.scene.control.TextInputDialog;
import se233.chapter2.Launcher;
import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

// Import the Log4j 2 classes
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AllEventHandlers {

    // Create a logger instance for this class
    private static final Logger logger = LogManager.getLogger(AllEventHandlers.class);

    /**
     * Handles the refresh action. It calls a method in the Launcher
     * to re-fetch data and update the UI.
     */
    public static void onRefresh() {
        try {
            Launcher.refreshPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void onAdd() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Currency");
            dialog.setContentText("Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> code = dialog.showAndWait();
            if (code.isPresent()){
                // LOG the addition
                logger.info("Adding currency: {}", code.get());

                List<Currency> currencyList = Launcher.getCurrencyList();
                Currency c = new Currency(code.get());
                List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(),8);
                c.setHistorical(cList);
                c.setCurrent(cList.get(cList.size()-1 ));
                currencyList.add(c);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException | ExecutionException e) {
            // Log the error
            logger.error("An error occurred while adding a currency: {}", e.getMessage());
            e.printStackTrace();
        }
    }
    public static void onDelete(String code) {
        try {
            // LOG the deletion
            logger.info("Deleting currency: {}", code);

            List<Currency> currencyList = Launcher.getCurrencyList();
            int index =-1;
            for(int i=0 ; i<currencyList.size() ; i++) {
                if (currencyList.get(i).getShortCode().equals(code) ) {
                    index = i;
                    break;
                }
            }
            if (index !=-1) {
                currencyList.remove(index);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException | ExecutionException e) {
            // Log the error
            logger.error("An error occurred while deleting a currency: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void onWatch(String code) {
        // ... (rest of the onWatch method is unchanged)
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index =-1;
            for(int i = 0 ; i < currencyList.size() ; i++) {
                if (currencyList.get(i).getShortCode().equals(code) ) {
                    index = i;
                    break;
                }
            }
            if (index !=-1) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add Watch");
                dialog.setContentText("Rate:");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                Optional<String> retrievedRate = dialog.showAndWait();
                if (retrievedRate.isPresent()){
                    double rate = Double.parseDouble(retrievedRate.get());
                    currencyList.get(index).setWatch(true);
                    currencyList.get(index).setWatchRate(rate);
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
                }
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
