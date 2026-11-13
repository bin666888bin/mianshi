package com.example.lighthouse.api;

import jakarta.validation.constraints.NotBlank;

public class CastRequest {

    @NotBlank
    private String fromName;

    @NotBlank
    private String wish;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }
}
