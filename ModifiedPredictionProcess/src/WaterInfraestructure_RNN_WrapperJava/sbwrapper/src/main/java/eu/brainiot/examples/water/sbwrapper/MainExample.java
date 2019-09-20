/* Copyright 2019 Siotic Spain, S.L. */


package eu.brainiot.examples.water.sbwrapper;


import jep.JepException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainExample {


    public static void main(String[] args) throws JepException, IOException {

        if (args.length != 1) {
            System.out.println("Invalid parameters...");
            System.out.println("MainExample pathToConfigFile");
            return;
        }

        SBWrapper wrapper = new SBWrapper();
        List<String> paths = new ArrayList();

        Config config = Config.readConfigFrom(args[0]);

        wrapper.setCurrentDirTo(config.getBasePath());

        wrapper.addDirToPath(config.getBasePath());

        for (String entry : config.getListOfSources()) {
            paths.add(config.getBasePath() + File.separator +  entry);
        }

        for (String path : paths) {
            wrapper.executeFile(path);
        }

        wrapper.executeFn(config.getCommandToExecute(), new ArrayList<String>(Arrays.asList("'" + config.getConfigFileRelativePath() + "'")));

    }


}
