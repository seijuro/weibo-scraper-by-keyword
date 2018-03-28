package com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition;

import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.GetParameter;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.excp.InsufficientSearchConditionException;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.excp.SearchConditionException;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.weibo.DateTimeRange;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.weibo.DocumentType;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.weibo.ObjectType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class WeiboSearchCondition implements GetParameter {
    public static final String AND = "&";

    @NonNull
    @Getter
    private String keyword;
    private ObjectType objectType;
    private DocumentType documentType;
    private DateTimeRange dateTimeRange;

    /**
     * C'tor
     *
     * @param $keyword
     * @throws SearchConditionException
     */
    public WeiboSearchCondition(String $keyword) throws SearchConditionException {
        if (StringUtils.isEmpty($keyword)) {
            throw new SearchConditionException("Param, keyword, is empty.");
        }

        this.keyword = $keyword;
        this.objectType = null;
        this.documentType = null;
        this.dateTimeRange = null;
    }

    /**
     * C'tor
     *
     * @param builder
     */
    protected WeiboSearchCondition(Builder builder) {
        this.keyword = builder.keyword;
        this.objectType = builder.objectType;
        this.documentType = builder.documentType;
        this.dateTimeRange = builder.dateTimeRange;
    }

    public boolean hasOnlyKeyword() {
        return Objects.isNull(this.objectType);
    }

    @Override
    public String getParamText() {
        StringBuffer query = new StringBuffer();
        try {
            query.append(URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString()));

            if (Objects.nonNull(this.objectType) &&
                    Objects.nonNull(this.documentType)) {
                query.append(AND).append(this.objectType.getParamText());
                query.append(AND).append(this.documentType.getParamText());

                if (Objects.nonNull(this.dateTimeRange)) {
                    query.append(AND).append(this.dateTimeRange.getParamText());
                }
            }

            return query.toString();
        }
        catch (UnsupportedEncodingException usexcp) {
            usexcp.printStackTrace();

            throw new RuntimeException("Encoding keyword failed.");
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(this.keyword);
        if (Objects.nonNull(this.objectType)) { sb.append("_").append(this.objectType.name()); }
        if (Objects.nonNull(this.documentType)) { sb.append("_").append(this.documentType.name()); }
        if (Objects.nonNull(this.dateTimeRange)) {
            if (StringUtils.isNotEmpty(this.dateTimeRange.getBeginText())) {
                sb.append("_").append(this.dateTimeRange.getBeginText());

                if (StringUtils.isNotEmpty(this.dateTimeRange.getEndText())) {
                    sb.append("_").append(this.dateTimeRange.getEndText());
                }
            }
        }

        return sb.toString();
    }

    /**
     * Builder Pattern Class
     */
    public static class Builder {
        /**
         * Instance Properties
         */
        @Setter
        private String keyword = null;
        @Setter
        private ObjectType objectType = null;
        @Setter
        private DocumentType documentType = null;
        @Setter
        private DateTimeRange dateTimeRange = null;

        public boolean isValid() {
            return StringUtils.isNotEmpty(this.keyword);
        }

        /**
         * Builder Pattern Method.
         *
         * @return
         * @throws InsufficientSearchConditionException
         */
        public WeiboSearchCondition build() throws InsufficientSearchConditionException {
            if (!isValid()) {
                throw new InsufficientSearchConditionException("Cann't build 'Weibo' search condition. 'keyword' must be set.");
            }
            return new WeiboSearchCondition(this);
        }
    }
}
