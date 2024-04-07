package no.josefushighscore.model;

import java.util.HashMap;
import java.util.List;

public class Errors {

    HashMap<String, String> errors;

    HashMap<String, List<String>> listErrors;



    public Errors(HashMap<String, String> errors,HashMap<String, List<String>> listErrors) {
        this.errors = errors;
        this.listErrors = listErrors;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> map) {
        this.errors = map;
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
                "errors=" + errors +
                '}';
    }
}
