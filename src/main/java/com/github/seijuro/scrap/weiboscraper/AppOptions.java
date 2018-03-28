package com.github.seijuro.scrap.weiboscraper;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppOptions {

    private static final Logger LOG = LoggerFactory.getLogger(AppOptions.class.getName());

    private static AppOptions ourInstance = new AppOptions();

    public static AppOptions getInstance() {
        return ourInstance;
    }

    static final String DATE_FORMAT_YMD = "yyyyMMdd";
    static final String DATE_FORMAT_YMDH = "yyyyMMddHH";

    @Getter
    private String[] keywords;
    @Getter
    private DateTime beginDateTime;
    @Getter
    private DateTime endDateTime;
    @Getter @Setter
    private String configFilePath;

    public void setKeywords(String keyword) {
        if (StringUtils.isNotEmpty(keyword)) {
            String[] tokens = StringUtils.stripToEmpty(keyword).split(",");

            List<String> tmp = new ArrayList<>();
            for (String token : tokens) {
                tmp.add(StringUtils.stripToEmpty(token).replaceAll("\\s+", "+"));
            }

            this.keywords = tmp.toArray(new String[] {});
            LOG.debug("keyword(param) : {} --> {}", keyword, Arrays.toString(this.keywords));
        }
    }

    public void setBeginDateTime(String date) throws IllegalArgumentException {
        if (StringUtils.isEmpty(date)) {
            throw new IllegalArgumentException("Param, date, is empty");
        }

        if (date.length() == DATE_FORMAT_YMD.length()) {
            DateTimeFormatter format = DateTimeFormat.forPattern(DATE_FORMAT_YMD);
            this.beginDateTime = format.parseDateTime(date);
            return;
        }
        else if (date.length() == DATE_FORMAT_YMDH.length()) {
            DateTimeFormatter format = DateTimeFormat.forPattern(DATE_FORMAT_YMDH);
            this.beginDateTime = format.parseDateTime(date);
            return;
        }
        throw new IllegalArgumentException("Param, date, must have one of formats, 'yyyyMMddHH' or 'yyyyMMdd'");
    }

    public void setEndDateTime(String date) throws IllegalArgumentException {
        if (StringUtils.isEmpty(date)) {
            throw new IllegalArgumentException("Param, date, is empty");
        }

        if (date.length() == DATE_FORMAT_YMD.length()) {
            DateTimeFormatter format = DateTimeFormat.forPattern(DATE_FORMAT_YMD);
            this.endDateTime = format.parseDateTime(date);
            return;
        }
        else if (date.length() == DATE_FORMAT_YMDH.length()) {
            DateTimeFormatter format = DateTimeFormat.forPattern(DATE_FORMAT_YMDH);
            this.endDateTime = format.parseDateTime(date);
            return;
        }
        throw new IllegalArgumentException("Param, date, must have one of formats, 'yyyyMMddHH' or 'yyyyMMdd'");

    }



    /**
     * prevent to create <code>AppOptions</code> instance by calling constructor.
     * <code>AppOptions</code> class follows Singleton pattern.
     */
    private AppOptions() {
    }
}
