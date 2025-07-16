package se233.chapter2.model;

import java.util.List;

public class Currency {
    public Double getWatchRate() {
        return watchRate;
    }

    public void setWatchRate(Double watchRate) {
        this.watchRate = watchRate;
    }

    public Boolean getWatch() {
        return isWatch;
    }

    public void setWatch(Boolean watch) {
        isWatch = watch;
    }

    public List<CurrencyEntity> getHistorical() {
        return historical;
    }

    public void setHistorical(List<CurrencyEntity> historical) {
        this.historical = historical;
    }

    public CurrencyEntity getCurrent() {
        return current;
    }

    public void setCurrent(CurrencyEntity current) {
        this.current = current;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    private String shortCode;
    private CurrencyEntity current;
    private List<CurrencyEntity> historical;
    private Boolean isWatch;
    private Double watchRate;

    public Currency(String shortCode) {
        this.shortCode = shortCode;
        this.isWatch = false;
        this.watchRate = 0.0;
    }

    // Getters and setters for these fields would typically be added here
    // to allow other parts of the application to access and modify the properties.
}
