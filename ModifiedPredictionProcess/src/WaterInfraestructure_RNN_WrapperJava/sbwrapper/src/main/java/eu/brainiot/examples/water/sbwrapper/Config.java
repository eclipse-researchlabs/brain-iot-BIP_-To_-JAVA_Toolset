/* Copyright 2019 Siotic Spain, S.L. */


package eu.brainiot.examples.water.sbwrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class Config {

    private String basePath = ".";
    private List<String> sources = new ArrayList();
    private String configFileRelativePath = null;
    private String commandToExecute = null;

    public Config(JSONObject json) throws JSONException {

        this.basePath = relativePathToAbsolutePath(json.getString("basePath"));
        this.configFileRelativePath = json.getString("configPath");
        this.commandToExecute = json.getString("commandToExecute");
        JSONArray sourcesAsJSON = json.getJSONArray("sources");
        for (Object o: sourcesAsJSON) {
            this.sources.add((String)o);
        }

    }

    public static Config readConfigFrom(String path) throws IOException {

        String configData = new String(Files.readAllBytes(Paths.get(relativePathToAbsolutePath(path))));
        return new Config(new JSONObject(configData));

    }


    public String getBasePath() {
        return this.basePath;
    }


    public String getCommandToExecute() {
        return this.commandToExecute;
    }


    public String getConfigFileRelativePath() {
        return this.configFileRelativePath;
    }


    public List<String> getListOfSources() {
        return this.sources;
    }


    private static String relativePathToAbsolutePath(String relativePath) {
        return new File(relativePath).getAbsolutePath();
    }

}
