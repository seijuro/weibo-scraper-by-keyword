package com.github.seijuro.scraper.weibo.search.url;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchURL implements HttpURLConvertable {

    static final Logger LOG = LoggerFactory.getLogger(SearchURL.class.getName());

    protected static final String BASE_URL = "https://s.weibo.com";
    public static final SearchURL SEARCH_PAGE = new SearchURL(BASE_URL);

    /**
     * Instance Properties
     */
    @NonNull
    protected final String url;

    /**
     * C'tor
     *
     * @param $url
     */
    SearchURL(String $url) {
        this.url = $url;
    }


    @Override
    public String toHttpUrl() {
        return this.url;
    }
}
