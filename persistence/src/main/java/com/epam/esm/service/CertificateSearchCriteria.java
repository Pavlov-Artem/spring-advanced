package com.epam.esm.service;

public enum CertificateSearchCriteria {

    BY_TAG("tag"),
    BY_NAME_PART("name_part"),
    BY_DESCRIPTION_PART("desc_part");

    private final String shortName;

    CertificateSearchCriteria(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }


}
