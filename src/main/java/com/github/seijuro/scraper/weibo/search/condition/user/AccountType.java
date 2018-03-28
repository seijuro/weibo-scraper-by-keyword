package com.github.seijuro.scraper.weibo.search.condition.user;

import com.github.seijuro.scraper.weibo.search.GetParameter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public enum AccountType implements GetParameter {
    ALL("所有用户", ""),
    ORGANIZAION("机构认证", "auth=org_vip"),
    PERSONAL("个人认证", "auth=per_vip"),
    OTHER("普通用户", "auth=ord");

    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String paramText;
    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String labelText;

    AccountType(String label, String text) {
        this.labelText = label;
        this.paramText = text;
    }
}
