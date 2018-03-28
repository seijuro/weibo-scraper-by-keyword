package com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition;

import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.GetParameter;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.excp.InsufficientSearchConditionException;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.excp.SearchConditionException;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.user.AccountType;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.user.Ages;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.user.Gender;
import com.github.seijuro.scrap.weiboscraper.scrap.weibo.search.condition.user.Location;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class UserSearchCondition implements GetParameter {

    private static final Logger LOG = LoggerFactory.getLogger(UserSearchCondition.class.getName());

    /**
     * Instance Properties
     */
    private String keyword;
    private String nickname;
    private String tag;
    private String school;
    private String work;
    private AccountType accountType;
    private Location location;
    private Ages ages;
    private Gender gender;

    /**
     * C'tor
     *
     * @param $keyword
     */
    public UserSearchCondition(String $keyword) throws SearchConditionException {
        if (StringUtils.isEmpty($keyword)) {
            throw new SearchConditionException("Param, keyword, is empty.");
        }

        this.keyword = $keyword;
        this.nickname = null;
        this.tag = null;
        this.school = null;
        this.work = null;
        this.accountType = null;
        this.location = null;
        this.ages = null;
        this.gender = null;
    }

    /**
     * C'tor (Builder Patthern C'tor)
     *
     * @param builer
     */
    protected UserSearchCondition(Builder builer) {
        this.keyword = builer.keyword;
        this.nickname = builer.nickname;
        this.tag = builer.tag;
        this.school = builer.school;
        this.work = builer.work;
        this.accountType = builer.accountType;
        this.location = builer.location;
        this.ages = builer.ages;
        this.gender = builer.gender;
    }

    @SuppressWarnings("unused")
    public boolean isNormal() {
        return StringUtils.isNotEmpty(this.keyword);
    }

    @SuppressWarnings("unused")
    public boolean isAdvanced() {
        if (StringUtils.isEmpty(this.keyword)) {
            return StringUtils.isNotEmpty(this.nickname) ||
                    StringUtils.isNotEmpty(this.tag) ||
                    StringUtils.isNotEmpty(this.school) ||
                    StringUtils.isNotEmpty(this.work);
        }

        return false;
    }

    @Override
    public String getParamText() {
        StringBuffer query = new StringBuffer();

        try {
            //  normal (intergrated)
            String encoding = StandardCharsets.UTF_8.toString();
            if (isNormal()) {
                query.append(URLEncoder.encode(this.nickname, encoding));
                return query.toString();
            }
            //  advanced
            if (StringUtils.isNotEmpty(this.nickname)) {
                query.append("&nickname=").append(URLEncoder.encode(this.nickname, encoding));
            }
            if (StringUtils.isNotEmpty(this.tag)) {
                query.append("&tag=").append(URLEncoder.encode(this.tag, encoding));
            }
            if (StringUtils.isNotEmpty(this.school)) {
                query.append("&school=").append(URLEncoder.encode(this.school, encoding));
            }
            if (StringUtils.isNotEmpty(this.work)) {
                query.append("&work=").append(URLEncoder.encode(this.work, encoding));
            }
            if (Objects.nonNull(this.accountType)) {
                query.append("&").append(this.accountType.getParamText());
            }
            if (Objects.nonNull(this.location)) {
                query.append("&").append(this.location.getParamText());
            }
            if (Objects.nonNull(this.ages)) {
                query.append("&").append(this.ages.getParamText());
            }
            if (Objects.nonNull(this.gender)) {
                query.append("&").append(this.gender.getParamText());
            }
            return query.toString();
        }
        catch (UnsupportedEncodingException usexcp) {
            LOG.error(usexcp.getMessage());
            throw new RuntimeException("Encoding user search condition(s) failed.");
        }
    }

    public static class Builder {
        /**
         * Instance Properties
         */
        private String keyword = null;
        @Setter
        private String nickname = null;
        @Setter
        private String tag = null;
        @Setter
        private String school = null;
        @Setter
        private String work = null;
        @Setter
        private AccountType accountType = null;
        @Setter
        private Location location = null;
        @Setter
        private Ages ages = null;
        @Setter
        private Gender gender = null;

        protected boolean isValid() {
            if (StringUtils.isNotEmpty(keyword) ||
                    StringUtils.isNotEmpty(this.nickname) ||
                    StringUtils.isNotEmpty(this.tag) ||
                    StringUtils.isNotEmpty(this.school) ||
                    StringUtils.isNotEmpty(this.work)) {
                return true;
            }

            return false;
        }

        /**
         * Builder Pattern method.
         *
         * @return
         * @throws InsufficientSearchConditionException
         */
        public UserSearchCondition build() throws InsufficientSearchConditionException {
            if (!isValid()) {
                StringBuffer msg = new StringBuffer();
                msg.append("Cann't build 'user' search condition with he given info(s).")
                        .append("\n")
                        .append("At least, keyword or one of {nickname, tag, school, work} must be set.");
                throw new InsufficientSearchConditionException(msg.toString());
            }

            return new UserSearchCondition(this);
        }
    }
}
