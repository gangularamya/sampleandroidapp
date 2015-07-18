package com.ms.ramya.sampleapp;
import java.util.List;

public class TotalData {


    private List<ImageData> results;




    public List<ImageData> getResults() {
        return results;
    }

    public void setResults(List<ImageData> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "imageData [imageData=" + results + "]";
    }
}