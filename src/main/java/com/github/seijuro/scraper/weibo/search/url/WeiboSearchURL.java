package com.github.seijuro.scraper.weibo.search.url;

import com.github.seijuro.scraper.weibo.search.condition.WeiboSearchCondition;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class WeiboSearchURL extends SearchURL {

    private static final Logger LOG = LoggerFactory.getLogger(WeiboSearchURL.class.getName());

    private static final String WEIBO_SEARCH_URL = BASE_URL + "/weibo";

    /**
     * Instance Properties
     */
    @NonNull
    private WeiboSearchCondition condition;

    WeiboSearchURL(WeiboSearchCondition $condition) throws IllegalArgumentException {
        super(WEIBO_SEARCH_URL);
        this.condition = $condition;
    }

    @Override
    public String toHttpUrl() {
        return toHttpUrl(this.condition.hasOnlyKeyword() ? Refer.FROM_INDEX : Refer.FROM_ADVANCED_SEARCH);
    }

    public String toHttpUrl(Refer refer) {
        return toHttpUrl(refer, Integer.MIN_VALUE);
    }

    public String toHttpUrl(int page) {
        return toHttpUrl(null, Integer.MIN_VALUE);
    }

    protected String toHttpUrl(Refer refer, int page) {
        StringBuffer url = new StringBuffer(super.toHttpUrl());
        url.append("/").append(this.condition.getParamText());
        if (page > 0) {
            url.append("&page=").append(page);
        }
        else if (Objects.nonNull(refer)) {
            url.append("&").append(refer.getParamText());
        }
        return url.toString();
    }
}
