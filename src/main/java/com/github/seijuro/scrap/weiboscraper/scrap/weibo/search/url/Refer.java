package com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.url;

import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.GetParameter;
import org.apache.commons.lang3.StringUtils;

public class Refer implements GetParameter {

    public static final String NONE = "g";
    public static final String INDEX = "index";

    public static final Refer FROM_ADVANCED_SEARCH = new Refer("g");
    public static final Refer FROM_INDEX = new Refer("index");

    private String from;
    private String to;

    public Refer(String $from) {
        this.from = $from;
        this.to = StringUtils.EMPTY;
    }

    public Refer(String $from, String $to) {
        this.from = $from;
        this.to = $to;
    }

    @Override
    public String getParamText() {
        StringBuffer text = new StringBuffer("Refer=");
        text.append(this.from);
        if (StringUtils.isNotEmpty(this.to)) {
            text.append("_").append(this.to);
        }
        return text.toString();
    }
}
