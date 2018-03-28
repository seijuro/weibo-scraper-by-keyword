package com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.user;

import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.GetParameter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public enum Ages implements GetParameter {
    UNDER_18("18岁以下", "age=18y"),
    BETWEEN_19_AND_22("19~22岁", "age=22y"),
    BETWEEN_23_AND_29("23~29岁", "age=29y"),
    BETWEEN_30_AND_39("30~39岁", "age=39y"),
    OVER_40("40岁以上", "age=40y");

    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String paramText;
    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String labelText;

    Ages(String label, String text) {
        this.labelText = label;
        this.paramText = text;
    }
}
