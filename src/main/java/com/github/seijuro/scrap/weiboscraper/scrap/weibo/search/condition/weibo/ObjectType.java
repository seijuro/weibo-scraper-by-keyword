package com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.weibo;

import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.GetParameter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public enum ObjectType implements GetParameter {
    ALL("全部", "typeall=1"),
    HOT("热门", "xsort=hot"),
    ORIGINAL("原创", "scope=ori"),
    ATTENTION("关注人", "atten=1"),
    VIP("认证用户", "vip=1"),
    MIDEA("媒体", "category=4");

    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String paramText;
    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String labelText;

    ObjectType(String label, String text) {
        this.labelText = label;
        this.paramText = text;
    }
}
