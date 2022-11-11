package org.example.enums;

public enum ApiKey {
    API_KEY("live_2XMRWf7Qjw6bi4KIahJZYONZvmCwRzdnxnbfnXlGfW5ATCzMeElzq9EXvmrdJzJb");

    private String key;

    private  ApiKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

}
