package com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.weibo;

import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.GetParameter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public enum DocumentType implements GetParameter {
    ALL("全部", "suball=1"),
    HAS_PIC("含图片", "haspic=1"),
    HAS_VIDEO("含视频", "hasvideo=1"),
    HAS_MUSIC("含音乐", "hasmusic=1"),
    HAS_LINK("含短链", "haslink=1");

    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String labelText;
    @NonNull
    @Getter(AccessLevel.PUBLIC)
    private final String paramText;

    DocumentType(String label, String param) {
        this.labelText = label;
        this.paramText = param;
    }
}
