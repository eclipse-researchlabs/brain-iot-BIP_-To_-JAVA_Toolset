/* Copyright 2019 Siotic Spain, S.L. */


package eu.brainiot.examples.water.sbwrapper;


import jep.JepException;
import jep.SharedInterpreter;
import java.util.List;


public class SBWrapper {

    private SharedInterpreter jep = null;

    public SBWrapper() { }


    private SharedInterpreter jep() throws JepException {

        if (null == this.jep) {
            this.jep = new SharedInterpreter();
            this.jep.eval("import os");
            this.jep.eval("import sys");
        }
        return this.jep;

    }

    public void executeFile(String path) throws JepException {

        this.jep().eval("exec(open('" + path + "').read())");

    }

    public void executeCode(String code) throws JepException {

        this.jep().eval(code);

    }

    public void executeFn(String functionName, List<String> parameterValues) throws JepException {

        String fnExecution = functionName + "(";
        for (String parameterValue: parameterValues) {
            fnExecution += parameterValue  + ",";
        }

        fnExecution = fnExecution.substring(0, fnExecution.length() - 1);

        fnExecution += ")";

        this.executeCode(fnExecution);

    }


    public void executeScript(String path) throws JepException {

        this.jep().runScript(path);

    }


    public void setCurrentDirTo(String path) throws JepException {

        this.executeCode("os.chdir('" + path + "')");

    }


    public void addDirToPath(String path) throws JepException {

        this.executeCode("sys.path.insert(0,'" + path + "')");

    }


}
