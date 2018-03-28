package com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.url;

import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.SearchURLException;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.UserSearchCondition;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.WeiboSearchCondition;

import java.util.Objects;

public class SearchURLFactory {
    public static WeiboSearchURL weiboSearchURL(String keyword) throws SearchURLException {
        return new WeiboSearchURL(new WeiboSearchCondition(keyword));
    }

    public static WeiboSearchURL weiboSearchURL(WeiboSearchCondition condition) throws SearchURLException {
        if (Objects.isNull(condition)) {
            throw new SearchURLException("Param, condition, is null.");
        }
        return new WeiboSearchURL(condition);
    }

    public static UserSearchURL userSearchURL(String keyword) throws SearchURLException {
        return new UserSearchURL(new UserSearchCondition(keyword));
    }

    public static UserSearchURL userSearchURL(UserSearchCondition condition) throws SearchURLException {
        if (Objects.isNull(condition)) {
            throw new SearchURLException("Param, condition, is null.");
        }
        return new UserSearchURL(condition);
    }
}
