package com.github.seijuro.scraper.weibo.search.condition.user;

import com.github.seijuro.scraper.weibo.search.GetParameter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class Location implements GetParameter {
    public enum Major implements GetParameter {
        Anhui("安徽", 34),
        Beijing("北京", 11),
        Chongqing("重庆", 50),
        Fujian("福建", 35),
        Gansu("甘肃", 62),
        Guangdong("广东", 44),
        Guangxi("广西", 45),
        Guizhou("贵州", 52),
        Hainan("海南", 46),
        Hebei("河北", 13),
        Heilongjiang("黑龙江", 23),
        Henan("河南", 41),
        Hubei("湖北", 42),
        Hunan("湖南", 43),
        Mongolia("内蒙古", 15),
        Jiangsu("江苏", 32),
        Jiangxi("江西", 36),
        Jilin("吉林", 22),
        Liaoning("辽宁", 21),
        Ningxia("宁夏", 64),
        Qinghai("青海", 63),
        Shanxi("山西", 14),
        Shandong("山东", 37),
        Shanghai("上海", 31),
        Sichuan("四川",51),
        Tianjin("天津",12),
        Tibet("西藏",54),
        Xinjiang("新疆",65),
        Yunnan("云南",53),
        Zhejiang("浙江",33),
        Shaanxi("陕西",61),
        Taiwan("台湾",71),
        HongKong("香港",81),
        Macao("澳门",82),
        Overseas("海外",400),
        Other("其他",100);

        /**
         * Instance Properties
         */
        private final String labelText;
        private final int code;

        Major(String $label, int $code) {
            this.labelText = $label;
            this.code = $code;
        }

        @Override
        public String getParamText() {
            return Integer.toString(this.code);
        }
    }

    public enum Minor implements GetParameter {
        ;

        /**
         * Instance Properties
         */
        private final String labelText;
        private final int code;

        Minor(String $label, int $code) {
            this.labelText = $label;
            this.code = $code;
        }

        @Override
        public String getParamText() {
            return Integer.toString(this.code);
        }
    }

    /**
     * C'tor
     *
     * @param $major
     * @param $minor
     */
    public Location(Major $major, Minor $minor) {
        this.major = $major;
        this.minor = $minor;
    }

    /**
     * Instance Properties
     */
    private Major major = null;
    private Minor minor = null;

    @Override
    public String getParamText() {
        if (Objects.nonNull(this.major)) {
            StringBuffer query = new StringBuffer("region=custom:");
            query.append(this.major.getParamText());
            query.append(":").append(Objects.nonNull(this.minor) ? this.minor.getParamText() : Integer.toString(1000));

            return toString();
        }

        return StringUtils.EMPTY;
    }
}
