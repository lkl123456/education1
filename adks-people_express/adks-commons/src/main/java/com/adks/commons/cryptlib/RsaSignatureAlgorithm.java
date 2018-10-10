package com.adks.commons.cryptlib;

public enum RsaSignatureAlgorithm {

    SHA1WithRSA("SHA1WithRSA"),

    MD5WithRSA("MD5WithRSA");

    private String signAlgorithm;

    private RsaSignatureAlgorithm(String signAlgorithm) {
        this.signAlgorithm = signAlgorithm;
    }

    public String getSignAlgorithm() {
        return signAlgorithm;
    }

}
