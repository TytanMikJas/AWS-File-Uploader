package com.example.server.file;

public enum Constants {
    BUCKET_URL("https://8vz46pxelk.execute-api.us-east-1.amazonaws.com/dev/tytan-file-bucket/");

    private final String value;
    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}