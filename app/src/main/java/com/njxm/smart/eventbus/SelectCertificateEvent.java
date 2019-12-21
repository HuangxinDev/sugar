package com.njxm.smart.eventbus;

public class SelectCertificateEvent {

    private String name; // 证书名

    private String certId; // 证书id

    public SelectCertificateEvent(String name, String certId) {
        this.name = name;
        this.certId = certId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }
}
