package com.github.seijuro.scrap.weiboscraper.scrap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class WebScraper implements Scraper {

    private static final Logger LOG = LoggerFactory.getLogger(WebScraper.class.getName());

    private static final long DEFAULT_WAIT_SECONDS = 10L;

    /**
     * Instance Properties
     */
    protected WebDriver webDriver;
    protected JavascriptExecutor jsExecutor;
    protected URL requestURL;
    protected WebDriverWait wait;

    /**
     * C'tor
     *
     * @param driver
     * @throws NullPointerException
     */
    @SuppressWarnings("unused")
    public WebScraper(WebDriver driver, String url) throws NullPointerException, MalformedURLException {
        this(driver, new URL(url));
    }

    /**
     * C'tor
     *
     * @param driver
     * @param url
     * @throws NullPointerException
     */
    @SuppressWarnings("unused")
    public WebScraper(WebDriver driver, URL url) throws NullPointerException {
        if (Objects.isNull(driver)) {
            throw new NullPointerException("Param, driver, is null object.");
        }
        if (Objects.isNull(url)) {
            throw new NullPointerException("Param, url, is a null object.");
        }

        this.webDriver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(this.webDriver, DEFAULT_WAIT_SECONDS);
        this.requestURL = url;
    }

    @Override
    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    @Override
    public WebDriverWait getDefaultWebDriverWait() {
        return this.wait;
    }

    @Override
    public void waitForMillis(long minMillis, long maxMillis) throws InterruptedException {
        waitForMillis(minMillis, maxMillis, StringUtils.EMPTY);
    }

    @Override
    public void waitForMillis(long minMillis, long maxMillis, String reason) throws InterruptedException {
        long randomMillis = (long)(Math.random() * (maxMillis - minMillis)) + minMillis;
        StringBuffer message = new StringBuffer();
        message.append("Thread sleep {");
        if (StringUtils.isNotEmpty(reason)) { message.append(" reason : [").append(reason).append("],"); }
        message.append(" ms : [").append(randomMillis).append("] }");
        LOG.debug(message.toString());

        Thread.sleep(randomMillis);
    }

    @Override
    public void waitForSeconds(long minSec, long maxSec) throws InterruptedException {
        waitForMillis(minSec * DateUtils.MILLIS_PER_SECOND, maxSec * DateUtils.MILLIS_PER_SECOND, StringUtils.EMPTY);
    }

    @Override
    public void waitForSeconds(long minSec, long maxSec, String reason) throws InterruptedException {
        waitForMillis(minSec * DateUtils.MILLIS_PER_SECOND, maxSec * DateUtils.MILLIS_PER_SECOND, reason);
    }

    @Override
    public void load(String url) throws InterruptedException {
        LOG.info("Loading page ... (op : get, url : {})", url);
        this.webDriver.get(url);
    }

    @Override
    public void navigate(String url) throws InterruptedException {
        LOG.info("Loading page ... (op : navigate, url : {})", url);
        this.webDriver.navigate().to(url);
    }

    @Override
    public void snapshot(String path) throws IOException, InterruptedException {
        File scrFile = ((TakesScreenshot) this.webDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }

    @Override
    public void save(String path) throws IOException, InterruptedException {
        String pageSource = this.webDriver.getPageSource();
        save(path, pageSource);
    }

    @Override
    public void save(String source, String path) throws IOException, InterruptedException {
        save(path, source, null);
    }

    @Override
    public void save(WebElement element, String path) throws IOException, InterruptedException {
        save(path, element.getAttribute("innerHTML"), null);
    }

    public void save(String path, String source, Map<String, String> replPatterns) throws IOException, InterruptedException {
        String pageSource = source;
        if (Objects.nonNull(replPatterns)) {
            for (Map.Entry<String, String> entry : replPatterns.entrySet()) {
                pageSource = pageSource.replaceAll(entry.getKey(), entry.getValue());
            }
        }

        FileUtils.writeStringToFile(new File(path), pageSource, StandardCharsets.UTF_8);
    }

    @Override
    public void refresh() throws InterruptedException {
        LOG.info("Refreshing page ... (op : refresh)");
        this.webDriver.navigate().refresh();
    }

    @Override
    public void close() {
        if (Objects.nonNull(this.webDriver)) {
            this.webDriver.close();
            this.webDriver = null;
        }
    }

    @Override
    public void timeoutAfterWait(long amount, TimeUnit unit) throws InterruptedException, TimeoutException {
        this.webDriver.manage().timeouts().implicitlyWait(amount, unit);
    }

    @Override
    public void waitForPageLoaded() throws InterruptedException, TimeoutException {
        long begin = System.currentTimeMillis();
        this.wait.until((ExpectedCondition<Boolean>) webDriver -> StringUtils.equalsIgnoreCase(jsExecutor.executeScript("return document.readyState").toString(), "complete"));
        long end = System.currentTimeMillis();
        long elapsed = end - begin;
        LOG.debug("Wait for loading page .... elapsed : {} (ms)", elapsed);
    }

    @Override
    public void scrollIntoView(WebElement element, boolean alignTop) throws InterruptedException {
        this.jsExecutor.executeScript("arguments[0].scrollIntoView("+ Boolean.toString(alignTop) + ");", element);
    }

    @Override
    public void clickElement(WebElement element) throws InterruptedException {
        this.jsExecutor.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' })", element);
        waitForSeconds(2L, 4L, "Scrolling");
        WebElement clickableElement = this.wait.until(ExpectedConditions.elementToBeClickable(element));
        clickableElement.click();
    }
}
