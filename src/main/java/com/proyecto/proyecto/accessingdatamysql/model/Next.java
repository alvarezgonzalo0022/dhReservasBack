package com.proyecto.proyecto.accessingdatamysql.model;

public class Next {
    private String href;

    private String start;

    public Next(String href, String start) {
        this.href = href;
        this.start = start;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
