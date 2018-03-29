package com.github.seijuro;

import lombok.NonNull;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppOptionParser {

    private static final Logger LOG = LoggerFactory.getLogger(AppOptionParser.class.getName());

    enum MainOption {
        KEYWORD("k", "keyword", true, true, "Search keywords (delimiter : camma ',' )"),
        BEGEIN("b", "begin", false, true, "start date (format yyyyMMdd, yyyyMMddHH)"),
        END("e", "end", false, true, "end date (format yyyyMMdd, yyyyMMddHH)"),
        CONFIG("c", "config", true, true, "configuration file path");

        @NonNull
        private final String shortName;
        @NonNull
        private final String longName;
        @NonNull
        private final String description;
        private final boolean required;
        private final boolean hasArgs;

        MainOption(String $shortName, String $longName, boolean $required, boolean $hasArgs, String $description) {
            this.shortName = $shortName;
            this.longName = $longName;
            this.required = $required;
            this.hasArgs = $hasArgs;
            this.description = $description;
        }
    }

    public static AppOptions parse(String args[]) {
        AppOptions appOptions = AppOptions.getInstance();
        Options options = new Options();

        for (MainOption option : MainOption.values()) {
            Option tmpOption = new Option(option.shortName, option.longName, option.hasArgs, option.description);
            tmpOption.setRequired(option.required);
            options.addOption(tmpOption);
        }

        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            String keywords = cmd.getOptionValue(MainOption.KEYWORD.shortName);
            String config = cmd.getOptionValue(MainOption.CONFIG.shortName);

            appOptions.setKeywords(keywords);
            appOptions.setConfigFilePath(config);
            if (cmd.hasOption(MainOption.BEGEIN.shortName)) {
                appOptions.setBeginDateTime(cmd.getOptionValue(MainOption.BEGEIN.shortName));
                if (cmd.hasOption(MainOption.END.shortName)) {
                    appOptions.setEndDateTime(cmd.getOptionValue(MainOption.END.shortName));
                }
            }
        }
        catch (ParseException pexcp) {
            pexcp.printStackTrace();
            helpFormatter.printHelp("WeiboScraper", options);

            throw new RuntimeException("Parsing app option(s) failed.");
        }

        return appOptions;
    }
}
