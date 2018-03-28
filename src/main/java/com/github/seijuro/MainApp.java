package com.github.seijuro;

import com.github.seijuro.config.Configuration;
import com.github.seijuro.config.DriverMode;
import com.github.seijuro.config.DriverType;
import com.github.seijuro.scraper.Scraper;
import com.github.seijuro.scraper.weibo.WeiboSearchWebScraper;
import com.github.seijuro.scraper.weibo.search.SearchURLException;
import com.github.seijuro.scraper.weibo.search.condition.WeiboSearchCondition;
import com.github.seijuro.scraper.weibo.search.condition.weibo.DateTimeRange;
import com.github.seijuro.scraper.weibo.search.condition.weibo.DocumentType;
import com.github.seijuro.scraper.weibo.search.condition.weibo.ObjectType;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class MainApp {

    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class.getName());

    public static final String PROP_CHROME_DRIVER_PATH = "webdriver.chrome.driver";
    public static final String PROP_PHANTOMJS_DRIVER_PATH = "phantomjs.binary.path";

    private static final String[] KEYWORDS = {"世界杯", "俄罗斯世界杯", "梅西", "messi", "内马尔", "neymar", "习近平"};

    public static WebDriver createWebDriver(Configuration config) {
        DriverType driver = config.getDriver();
        DriverMode mode = Objects.nonNull(config.getMode()) ? config.getMode() : DriverMode.LOCAL;

        try {
            if (driver == DriverType.CHROME) {
                String[] options = config.getDriverOptions();

                if (mode == DriverMode.LOCAL) {
                    String driverPath = config.getDriverPath();
                    return createLocalChromeWebDriver(driverPath, options);
                }
                else if (mode == DriverMode.REMOTE) {
                    URL address = new URL(String.format("http://%s:%s/wd/hub", config.getRemoteIP(), config.getRemotePort()));
                    LOG.debug("Remote address : " + address);

                    return createRemoteChromeWebDriver(address, options);
                }
            }

            throw new RuntimeException("Not supported driver yet.");
        }
        catch (MalformedURLException mfuexcp) {
            LOG.error(mfuexcp.getMessage());
            throw new RuntimeException(mfuexcp.getMessage());
        }
    }

    public static WebDriver createRemoteChromeWebDriver(URL url, String[] options) throws MalformedURLException {
        if (Objects.nonNull(url)) {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            if (Objects.nonNull(options)) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(options);
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            }
            return new RemoteWebDriver(url, capabilities);
        }

        throw new RuntimeException("URL for retmoe web-driver is null.");
    }

    public static WebDriver createLocalChromeWebDriver(String path, String[] options) {
        if (StringUtils.isNotEmpty(path)) {
            System.setProperty(PROP_CHROME_DRIVER_PATH, path);
        }

        if (Objects.nonNull(options)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments(options);

            return new ChromeDriver(chromeOptions);
        }

        return new ChromeDriver();
    }

    public static void main(String[] args) {
        AppOptions appOptions = AppOptionParser.parse(args);
        Configuration config = new Configuration();

        try {
            Properties prop = new Properties();
            prop.load(new FileReader(appOptions.getConfigFilePath()));
            config.load(prop);

            prop.clear();
            prop.load(new FileReader(config.getUserConfigurationPath()));
            config.load(prop);
        }
        catch (IOException ioexcp) {
            LOG.error("Loading config file failed (msg : {})" + ioexcp.getMessage());

            return;
        }

        WebDriver webDriver = createWebDriver(config);

        try {
            List<WeiboSearchCondition> searchConditions = new ArrayList<>();
            String[] keywords = appOptions.getKeywords();

            if (Objects.nonNull(keywords)) {
                for (String keyword : keywords) {
                    try {
                        WeiboSearchCondition.Builder conditionBuilder = new WeiboSearchCondition.Builder();

                        conditionBuilder.setKeyword(keyword);
                        conditionBuilder.setObjectType(ObjectType.ORIGINAL);
                        conditionBuilder.setDocumentType(DocumentType.ALL);
                        conditionBuilder.setDateTimeRange(new DateTimeRange(appOptions.getBeginDateTime(), appOptions.getEndDateTime()));
                        searchConditions.add(conditionBuilder.build());
                    } catch (SearchURLException suexcp) {
                        LOG.warn(suexcp.getMessage());
                    }
                }
                Scraper scrapper = new WeiboSearchWebScraper(config, webDriver, searchConditions);
                scrapper.scrap();
            }
            else {
                LOG.warn("There are not keyword to search.");
            }

        }
        catch (Exception excp) {
            excp.printStackTrace();
        }
        finally {
            webDriver.close();
        }
    }
}
