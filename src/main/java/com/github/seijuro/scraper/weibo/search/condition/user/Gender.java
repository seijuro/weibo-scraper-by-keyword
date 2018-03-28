package com.github.seijuro.scraper.weibo.search.condition.user;

import com.github.seijuro.scraper.weibo.search.GetParameter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public enum Gender implements GetParameter {
    MALE("男", "gender=man"),
    FEMALE("女", "gender=women");

    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String paramText;
    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String labelText;

    Gender(String label, String text) {
        this.labelText = label;
        this.paramText = text;
    }

}
