package com.github.seijuro.scraper.weibo.search.condition.weibo;

import com.github.seijuro.scraper.weibo.search.GetParameter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class DateTimeRange implements GetParameter {

    private static final Logger LOG = LoggerFactory.getLogger(DateTimeRange.class.getName());

    private static final String PARAM_PREFIX = "timescope=custom";
    private static final String PARAM_DELIMITER = ":";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd-H");

    private static final DateTime LHS = new DateTime(2009, 8, 16, 0, 0, DateTimeZone.UTC);

    private DateTime begin = null;
    private DateTime end = null;

    public DateTimeRange(DateTime $begin) {
        this($begin, null);
    }

    public DateTimeRange(DateTime $begin, DateTime $end) {
        this.begin = $begin;
        this.end = $end;
    }

    @Override
    public String getParamText() {
        if (Objects.isNull(this.begin)) {
            return StringUtils.EMPTY;
        }

        StringBuffer query = new StringBuffer(PARAM_PREFIX);
        query.append(PARAM_DELIMITER).append(begin.toString(DATE_TIME_FORMATTER));

        if (Objects.nonNull(this.end) &&
                this.end.isAfter(this.begin)) {
            query.append(PARAM_DELIMITER).append(end.toString(DATE_TIME_FORMATTER));
        }

        return query.toString();
    }

    public String getBeginText() {
        return Objects.nonNull(this.begin) ? this.begin.toString(DATE_TIME_FORMATTER) : StringUtils.EMPTY;
    }

    public String getEndText() {
        return Objects.nonNull(this.end) ? this.end.toString(DATE_TIME_FORMATTER) : StringUtils.EMPTY;
    }
}
