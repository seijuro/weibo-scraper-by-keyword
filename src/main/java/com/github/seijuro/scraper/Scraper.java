package com.github.seijuro.scraper;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public interface Scraper {
    public int scrap() throws InterruptedException;

    public WebDriver getWebDriver();
    public WebDriverWait getDefaultWebDriverWait();
    public void waitForSeconds(long min, long max) throws InterruptedException;
    public void waitForSeconds(long min, long max, String reason) throws InterruptedException;
    public void waitForMillis(long min, long max) throws InterruptedException;
    public void waitForMillis(long min, long max, String reason) throws InterruptedException;
    public void waitForPageLoaded() throws InterruptedException, TimeoutException;
    public void load(String url) throws InterruptedException;
    public void navigate(String url) throws InterruptedException;
    public void refresh() throws InterruptedException;
    public void close();
    public void snapshot(String path) throws InterruptedException, IOException;
    public void save(String path) throws IOException, InterruptedException;
    public void save(String source, String path) throws IOException, InterruptedException;
    public void save(WebElement element, String path) throws IOException, InterruptedException;
    public void timeoutAfterWait(long amount, TimeUnit unit) throws InterruptedException, TimeoutException;
    public void scrollIntoView(WebElement element, boolean alignTop) throws InterruptedException;
    public void clickElement(WebElement element) throws InterruptedException;
}
