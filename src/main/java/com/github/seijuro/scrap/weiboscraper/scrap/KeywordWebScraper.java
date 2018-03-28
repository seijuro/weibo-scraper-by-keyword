package com.github.seijuro.scrap.weiboscraper.scrap;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class KeywordWebScraper extends WebScraper {

    private static Logger LOG = LoggerFactory.getLogger(KeywordWebScraper.class.getName());

    private Set<String> keywords = new LinkedHashSet<>();

    /**
     * C'tor
     *
     * @param driver
     * @param url
     * @throws NullPointerException
     * @throws MalformedURLException
     */
    public KeywordWebScraper(WebDriver driver, String url, String[] keywords) throws NullPointerException, MalformedURLException {
        this(driver, new URL(url), keywords);
    }

    /**
     * C'tor
     *
     * @param driver
     * @param url
     * @throws NullPointerException
     */
    public KeywordWebScraper(WebDriver driver, URL url, String[] keywords) throws NullPointerException {
        super(driver, url);

        setKeywords(keywords);
    }

    /**
     * set all keywords included in {@param keywords}.
     *
     * @param keywords
     */
    @SuppressWarnings("unused")
    public void setKeywords(String[] keywords) {
        this.keywords.clear();
        this.keywords.addAll(Arrays.asList(keywords));
    }

    /**
     * add all keywords included in {@param keywords}.
     *
     * @param keywords
     */
    @SuppressWarnings("unused")
    public void addAllKeywords(String[] keywords) {
        this.keywords.addAll(Arrays.asList(keywords));
    }

    /**
     * If there were any keyword which isn't used, return true. Otherwise, return false.
     *
     * @return
     */
    public boolean hasNext() {
        return this.keywords.size() > 0;
    }

    /**
     * If there exist any keyword which isn't used, return first keyword in keyword set.
     * Otherwise, return null.
     *
     * @return
     */
    public String getNextKeyword() {
        if (hasNext()) {
            String keyword = this.keywords.iterator().next();
            this.keywords.remove(keyword);

            return keyword;
        }

        return null;
    }

    /**
     * Somebody wanna handle all keywords (such as sort, iterate, etc ...).
     * By calling this method, you can get all keywords.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public Set<String> getAllKeywords() {
        return keywords;
    }
}
