package com.epam.esm.service;

import com.epam.esm.data.Tag;

import java.util.*;

public class CertificateCriteriaParameters {

    private Map<CertificateSearchCriteria, String> searchCriteriaStringMap = new HashMap<>();
    private Optional<CertificateSortCriteria> certificateSortCriteria;
    private Optional<SortDirection> sortDirection;
    private Long page;
    private Long pageSize;
    private List<String> tags = new ArrayList<>();
    private List<Tag> existedTags = new ArrayList<>();

    public CertificateCriteriaParameters() {
    }


    public Map<CertificateSearchCriteria, String> getSearchCriteriaStringMap() {
        return searchCriteriaStringMap;
    }

    public void setSearchCriteriaStringMap(Map<CertificateSearchCriteria, String> searchCriteriaStringMap) {
        this.searchCriteriaStringMap = searchCriteriaStringMap;
    }

    public Optional<CertificateSortCriteria> getCertificateSortCriteria() {
        return certificateSortCriteria;
    }

    public void setCertificateSortCriteria(Optional<CertificateSortCriteria> certificateSortCriteria) {
        this.certificateSortCriteria = certificateSortCriteria;
    }

    public Optional<SortDirection> getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Optional<SortDirection> sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Tag> getExistedTags() {
        return existedTags;
    }

    public void setExistedTags(List<Tag> existedTags) {
        this.existedTags = existedTags;
    }
}
