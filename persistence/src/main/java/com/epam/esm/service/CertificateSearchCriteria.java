package com.epam.esm.service;

public enum CertificateSearchCriteria {

    BY_TAG("tag"),
    BY_NAME_PART("name_part"),
    BY_DESCRIPTION_PART("desc_part"),
    TAGS("tags");
//    NAME_ASC("name_asc"),
//    NAME_DESC("name_desc"),
//    PRICE_ASC("price_asc"),
//    PRICE_DESC("price_desc");

    private final String shortName;
    private String value;

    CertificateSearchCriteria(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }


}
