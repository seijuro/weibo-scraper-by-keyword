package com.github.seijuro.scraper.weibo;

import com.github.seijuro.config.Configuration;
import com.github.seijuro.scraper.KeywordWebScraper;
import com.github.seijuro.scraper.weibo.search.SearchURLException;
import com.github.seijuro.scraper.weibo.search.WeiboSearchPathHelper;
import com.github.seijuro.scraper.weibo.search.condition.WeiboSearchCondition;
import com.github.seijuro.scraper.weibo.search.url.SearchURLFactory;
import com.github.seijuro.scraper.weibo.search.url.WeiboSearchURL;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WeiboSearchWebScraper extends KeywordWebScraper implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(WeiboSearchWebScraper.class.getName());

    private static final int MAX_TRY = 3;
    private static Map<String, String> REPLRULE_LINKS_TO_SAVE;

    static {
        REPLRULE_LINKS_TO_SAVE = new HashMap<>();
        REPLRULE_LINKS_TO_SAVE.put("href=\"//", "href=\"https://");
        REPLRULE_LINKS_TO_SAVE.put("img src=\"//", "img src=\"https://");
        REPLRULE_LINKS_TO_SAVE.put("video src=\"//", "video src=\"https://");
        REPLRULE_LINKS_TO_SAVE.put("img class=\"bigcursor\" src=\"//", "img class=\"bigcursor\" src=\"https://");
    }

    /**
     * Instance Properties
     */
    private Configuration appConfig;
    private Queue<URL> queue;
    private Map<String, WeiboSearchCondition> searchConditionMap;

    /**
     * C'tor
     *
     * @param driver
     * @throws NullPointerException
     * @throws MalformedURLException
     */
    public WeiboSearchWebScraper(Configuration config, WebDriver driver, List<WeiboSearchCondition> conditions) throws NullPointerException, MalformedURLException {
        super(driver, WeiboSearchURL.SEARCH_PAGE.toHttpUrl(), conditions.stream().map((Function<WeiboSearchCondition, String>) condition -> condition.getKeyword() ).collect(Collectors.toList()).toArray(new String[conditions.size()]));

        this.appConfig = config;
        this.queue = new LinkedList<>();
        this.searchConditionMap = new HashMap<>();
        for (WeiboSearchCondition condition : conditions) {
            this.searchConditionMap.put(condition.getKeyword(), condition);
        }
    }

    public List<WebElement> getUnfoldButtonElements() {
        try {
            return this.webDriver.findElements(By.xpath("//a[contains(@class, 'WB_text_opt') and @action-type='fl_unfold' and i[contains(@class, 'W_ficon') and contains(@class, 'ficon_arrow_down')]]"));
        }
        catch (java.util.NoSuchElementException nsexcp) {
            nsexcp.printStackTrace();
        }

        return null;
    }

    /**
     * Pop up 'log-in' window.
     * Find 'log-in' button in search page and click it. Sometimes,
     * The 'log-in' button doesn't work. For that reason, this will try to click button 3 times and check if 'log-in' window present or not.
     * if 'log-in' window present successfully, return 'true'. Otherwise, return false.
     *
     * @return
     * @throws InterruptedException
     */
    public boolean popUpLonginWindow() throws InterruptedException {
        By loginButtonXpath = By.xpath(WeiboSearchPathHelper.TopMenu.getLoginButtonElementPath());

        int tryCount = 0;
        do {
            try {
                this.wait.until(ExpectedConditions.presenceOfElementLocated(loginButtonXpath));
                WebElement loginButtonElement = this.wait.until(ExpectedConditions.elementToBeClickable(loginButtonXpath));
                waitForSeconds(2L, 3L, "Loading top menu including login button failed.");

                loginButtonElement.click();
                this.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'layer_login_register_v2') and @node-type='inner']")));

                return true;
            }
            catch (TimeoutException toexcp) {
                toexcp.printStackTrace();
            }

            ++tryCount;
        } while (tryCount != MAX_TRY);

        return false;
    }

    /**
     * Try to log in 'weibo' search with the given {@param username} and {@param password}.
     * This method try to pop up 'log-in' window and log in. And finally check if log-in process is done successfully.
     *
     * @param username
     * @param password
     * @return
     * @throws IllegalArgumentException
     * @throws InterruptedException
     */
    public boolean login(String username, String password) throws IllegalArgumentException, InterruptedException {
        if (StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("Param, username, is an empty string.");
        }

        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("Param, password, is an empty string.");
        }

        try {
            if (popUpLonginWindow()) {
                this.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'layer_login_register_v2') and @node-type='inner']")));

                By usernameInputXpath = By.xpath(WeiboSearchPathHelper.Login.getUsernameInputElementPath());
                By passwordInputXpath = By.xpath(WeiboSearchPathHelper.Login.getPasswordInputElementPath());
                By submitButtonXpath = By.xpath(WeiboSearchPathHelper.Login.getSubmitButtonElementPath());

                WebElement usernameInputElement = this.wait.until(ExpectedConditions.elementToBeClickable(usernameInputXpath));
                WebElement passwordInputElement = this.wait.until(ExpectedConditions.elementToBeClickable(passwordInputXpath));
                WebElement submitButtonElement = this.wait.until(ExpectedConditions.elementToBeClickable(submitButtonXpath));

                waitForSeconds(2L, 3L, "Loading 'log-in' window and all elements finished.");
                usernameInputElement.sendKeys(username);
                waitForSeconds(2L, 3L, "Waiting after sending 'username'");
                passwordInputElement.sendKeys(password);
                waitForSeconds(2L, 3L, "Waiting after sending 'password'");

                LOG.debug("Click 'submit' button");
                submitButtonElement.click();
                waitForPageLoaded();

                By accountButtonXpath = By.xpath(WeiboSearchPathHelper.TopMenu.getAccountButtonElementPath());
                //  check if 'log-in' process is done successfully.
                this.wait.until(ExpectedConditions.presenceOfElementLocated(accountButtonXpath));

                return true;
            }
            else {
                //  check if already login
                By accountButtonXpath = By.xpath(WeiboSearchPathHelper.TopMenu.getAccountButtonElementPath());
                wait.until(ExpectedConditions.presenceOfElementLocated(accountButtonXpath));

                return true;
            }
        }
        catch (TimeoutException excp) {
            excp.printStackTrace();
        }

        return false;
    }

    public boolean search(final String keyword) throws InterruptedException {
        try {
            By keywordInputXpath = By.xpath(WeiboSearchPathHelper.SearchForm.getSearchInputPath());
            By submitButtonXpath = By.xpath(WeiboSearchPathHelper.SearchForm.getSearchButtonPath());

            this.wait.until(ExpectedConditions.presenceOfElementLocated(keywordInputXpath));
            this.wait.until(ExpectedConditions.presenceOfElementLocated(submitButtonXpath));
            this.wait.until(ExpectedConditions.elementToBeClickable(submitButtonXpath));

            WebElement keywordInputElement = this.webDriver.findElement(keywordInputXpath);
            WebElement submitButtonElement = this.webDriver.findElement(submitButtonXpath);

            keywordInputElement.sendKeys(keyword);

            int tryIndex = 0;
            do {
                submitButtonElement.click();
                waitForPageLoaded();
                if (validateSearchResultPageLoaded()) {
                    return true;
                }

                waitForMillis(2L * DateUtils.MILLIS_PER_SECOND, 4L * DateUtils.MILLIS_PER_SECOND);
            } while (tryIndex != MAX_TRY);
        }
        catch (TimeoutException excp) {
            excp.printStackTrace();
        }

        return false;
    }

    public List<String> getListOfPageLinks() {
        List<String> pageLinks = new ArrayList<>();

        try {
            By pageListWrapXpath = By.xpath(WeiboSearchPathHelper.SearchResult.getPagesWrapElementPath());
            By pageItemsXpath = By.xpath(WeiboSearchPathHelper.SearchResult.getPageItemsElementPath());

            WebElement pageListWrapElement = this.webDriver.findElement(pageListWrapXpath);
            if (Objects.nonNull(pageListWrapElement)) {
                List<WebElement> pageItemElements = this.webDriver.findElements(pageItemsXpath);

                for (WebElement pageItemElement : pageItemElements) {
                    boolean isCurrentpage = StringUtils.contains(pageItemElement.getAttribute("class"), "cur");
                    WebElement linkElement = pageItemElement.findElement(By.xpath("a"));
                    String pageLinkUrl = linkElement.getAttribute("href");
                    pageLinks.add(pageLinkUrl);
                    LOG.debug("pageItem := { href : {}, current? : {}, text : {} }", pageLinkUrl, Boolean.toString(isCurrentpage), linkElement.getAttribute("innerHTML"));
                }
            }
        }
        catch (java.util.NoSuchElementException nsexcp) {
            LOG.warn("There are no such element (page list) ... message : {}", nsexcp.getMessage());
        }

        return pageLinks;
    }

    public String getPageFromURL(String url) {
        int sIdx = url.indexOf("page=");
        if (sIdx < 0) {
            return Integer.toString(1);
        }

        sIdx += 5;
        int eIdx = url.indexOf('&', sIdx);
        if (eIdx < 0) {
            eIdx = url.length();
        }
        return url.substring(sIdx, eIdx);
    }

    public void scrapPage(WeiboSearchCondition searchCondition) throws InterruptedException {
        //  unfold 'all' weibo.
        {
            List<WebElement> unfoldButtonElements = getUnfoldButtonElements();
            for (WebElement element : unfoldButtonElements) {
                clickElement(element);
                waitForSeconds(2L, 8L, "Loading 'full' comment & to escape 'robot detection'");
            }
        }

        String currentUrl = this.webDriver.getCurrentUrl();
        String page = getPageFromURL(currentUrl);

        LOG.debug("current url : {} -> page : {}", currentUrl, page);

        try {
            String filepath = "/Users/myungjoonlee/Desktop/WeiboSnapshot/" + String.format("%s_%s.html", searchCondition.toString(), page);
            save(filepath, this.webDriver.getPageSource(), REPLRULE_LINKS_TO_SAVE);
//            snapshot("/Users/myungjoonlee/Desktop/WeiboSnapshot/" + String.format("%s_%s.png", searchCondition.toString(), page));
        }
        catch (IOException ioexcp) {
            ioexcp.printStackTrace();
        }
    }

    public boolean validateSearchResultPageLoaded() throws InterruptedException, java.util.NoSuchElementException {
        int tryCount = 0;

        do {
            try {
                By bottomSearchBoxPath = By.xpath(WeiboSearchPathHelper.Bottom.getSearchInputBoxPath());
                this.wait.until(ExpectedConditions.presenceOfElementLocated(bottomSearchBoxPath));

                return true;
            } catch (TimeoutException toexcp) {
                toexcp.printStackTrace();
            }

            ++tryCount;
        } while (tryCount != MAX_TRY);

        return false;
    }

    public WeiboSearchCondition getNextSearchCondition() {
        if (hasNext()) {
            String keyword = getNextKeyword();
            WeiboSearchCondition condition = this.searchConditionMap.get(keyword);
            this.searchConditionMap.remove(keyword);

            return condition;
        }

        return null;
    }

    @Override
    public int scrap() throws InterruptedException {
        URL pageUrl = this.requestURL;
        load(pageUrl.toString());

        String user = this.appConfig.getUsername();
        String passwd = this.appConfig.getPassword();

        if (!hasNext()) {
            LOG.warn("No search keyword.");
            return 0;
        }

        //  login
        login(user, passwd);

        do {
            try {
                WeiboSearchCondition condition = getNextSearchCondition();
                WeiboSearchURL weiboSearchURL = SearchURLFactory.weiboSearchURL(condition);
                String searchURL = weiboSearchURL.toHttpUrl();

                LOG.debug("Search 'weibo' ... (keyword : {}, url : {}}",
                        condition.getKeyword(),
                        searchURL);

                this.webDriver.navigate().to(searchURL);
                waitForPageLoaded();

                if (!validateSearchResultPageLoaded()) {
                    LOG.error("Searching keyword '{}' failed ...", condition.getKeyword());
                    return -1;
                }

                waitForSeconds(1L, 5L); //  sleep 1 ~ 5 (sec)
                List<String> links = getListOfPageLinks();
                for (String link : links) {
                    try {
                        this.queue.offer(new URL(link));
                    } catch (MalformedURLException excp) {
                        LOG.warn("url (text : {}) is not valid ... SKIP!", link);
                    }
                }

                while (true) {
                    scrapPage(condition);

                    waitForSeconds(5L, 15L, "To escape 'robot detection'");

                    pageUrl = null;
                    while (this.queue.size() > 0 &&
                            (Objects.isNull(pageUrl) || StringUtils.isEmpty(pageUrl.toString()))) {
                        pageUrl = this.queue.poll();
                    }
                    if (this.queue.size() == 0 &&
                            Objects.isNull(pageUrl) || StringUtils.isEmpty(pageUrl.toString())) {
                        break;
                    }

                    int tryCount = 0;
                    for (; tryCount != MAX_TRY; ++tryCount) {
                        load(pageUrl.toString());
                        waitForPageLoaded();
                        waitForSeconds(2L, 4L, "Loading dynamic content");

                        if (!validateSearchResultPageLoaded()) {
                            LOG.warn("Laoding result page failed (keyword : {})", condition.getKeyword());
                        }

                        break;
                    }
                }
            }
            catch (SearchURLException suexcp) {
                suexcp.printStackTrace();
            }

            waitForMillis(5L * DateUtils.MILLIS_PER_MINUTE, 10L * DateUtils.MILLIS_PER_MINUTE, "Having time gap between searching keyword");
        } while (hasNext());


        return 0;
    }

    @Override
    public void run() {
    }
}
