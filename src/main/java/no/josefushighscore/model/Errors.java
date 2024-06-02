package no.josefushighscore.model;

import java.util.HashMap;
import java.util.List;

public class Errors {

    HashMap<String, String> fields;

    HashMap<String, List<String>> listErrors;



    public Errors(HashMap<String, String> errors,HashMap<String, List<String>> listErrors) {
        this.fields = errors;
        this.listErrors = listErrors;
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, String> map) {
        this.fields = map;
    }

    public HashMap<String, List<String>> getListErrors() {
        return listErrors;
    }

    public void setListErrors(HashMap<String, List<String>> listErrors) {
        this.listErrors = listErrors;
    }

    @Override
    public String toString() {
        return "Errors{" +
                "errors=" + fields +
                '}';
    }
}
