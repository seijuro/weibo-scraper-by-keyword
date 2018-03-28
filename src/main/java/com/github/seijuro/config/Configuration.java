package com.github.seijuro.config;

import com.github.seijuro.util.StoredAsProperty;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Properties;

public class Configuration {
    @StoredAsProperty(ConfigurationConstants.App.ROOT_DIR)
    private String rootDir = null;
    @StoredAsProperty(ConfigurationConstants.App.PAGE_DIR)
    private String pageDir = null;
    @StoredAsProperty(ConfigurationConstants.App.SNAPSHOT_DIR)
    private String snapshotDir = null;

    @StoredAsProperty(ConfigurationConstants.App.USER_CONF)
    private String userConf = null;
    @StoredAsProperty(ConfigurationConstants.App.MDOE)
    private String mode = null;
    @StoredAsProperty(ConfigurationConstants.App.DRIVER)
    private String driver = null;
    @StoredAsProperty(ConfigurationConstants.App.DRIVER_PATH)
    @Getter(AccessLevel.PUBLIC)
    private String driverPath = null;
    @StoredAsProperty(ConfigurationConstants.App.DRIVER_OPTION)
    private String driverOptions = null;
    @StoredAsProperty(ConfigurationConstants.App.REMOTE_IP)
    @Getter(AccessLevel.PUBLIC)
    private String remoteIP = null;
    @StoredAsProperty(ConfigurationConstants.App.REMOTE_PORT)
    @Getter(AccessLevel.PUBLIC)
    private String remotePort = null;

    @StoredAsProperty(ConfigurationConstants.User.USERNAME)
    @Getter(AccessLevel.PUBLIC)
    private String username = null;
    @StoredAsProperty(ConfigurationConstants.User.PASSWORD)
    @Getter(AccessLevel.PUBLIC)
    private String password = null;

    public String getRootDirectoryPath() {
        return this.rootDir;
    }

    public String getPageDirectoryPath() {
        return this.pageDir;
    }

    public String getSnapshotDirectoryPath() {
        return this.snapshotDir;
    }

    public String getUserConfigurationPath() {
        return this.userConf;
    }

    public DriverType getDriver() {
        if (StringUtils.isNotEmpty(this.driver)) {
            for (DriverType driver : DriverType.values()) {
                if (driver.getDriverName().equalsIgnoreCase(this.driver)) {
                    return driver;
                }
            }
        }

        return null;
    }

    public DriverMode getMode() {
        if (StringUtils.isNotEmpty(this.mode)) {
            for (DriverMode mode : DriverMode.values()) {
                if (mode.getModeName().equalsIgnoreCase(this.mode)) {
                    return mode;
                }
            }
        }

        return null;
    }

    public String[] getDriverOptions() {
        String trimedOptionString = StringUtils.stripToEmpty(this.driverOptions);
        if (StringUtils.isNotEmpty(trimedOptionString)) {
            return trimedOptionString.split("\\s+");
        }

        return null;
    }

    public void load(Properties prop) {
        if (Objects.nonNull(prop)) {
            try {
                Field[] fields = Configuration.class.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(StoredAsProperty.class)) {
                        StoredAsProperty storedAs = field.getAnnotation(StoredAsProperty.class);
                        String propName = storedAs.value();

                        field.set(this, prop.getProperty(propName, (String) field.get(this)));
                    }
                }
            } catch (IllegalAccessException iaexcp) {
                throw new RuntimeException("Illegal access to fieldName in property setter", iaexcp);
            }
        }
    }
}
