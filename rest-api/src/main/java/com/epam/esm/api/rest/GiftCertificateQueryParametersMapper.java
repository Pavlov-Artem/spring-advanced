package com.epam.esm.api.rest;

import com.epam.esm.service.CertificateSearchCriteria;
import com.epam.esm.service.CertificateSortCriteria;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GiftCertificateQueryParametersMapper {

    private GiftCertificateQueryParametersMapper() {
    }

    static Map<CertificateSearchCriteria, String> mapSearchParams(Map<String, String> inputParams) {

        Map<CertificateSearchCriteria, String> searchCriteriaMap = new HashMap<>();
        List<String> searchCriteria = Stream.of(CertificateSearchCriteria.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        for (Map.Entry<String, String> entry : inputParams.entrySet()) {
            if (searchCriteria.contains(entry.getKey().toUpperCase())) {
                CertificateSearchCriteria criteria = Enum.valueOf(CertificateSearchCriteria.class, entry.getKey().toUpperCase());
                searchCriteriaMap.put(criteria, entry.getValue());
            }
        }

        return searchCriteriaMap;
    }

    static List<CertificateSortCriteria> mapSortCriteria(Map<String, String> inputParams) {

        String sortParamName = "sort";
        List<String> sortCriteriaNames = Stream.of(CertificateSortCriteria.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        List<CertificateSortCriteria> sortCriteria = new ArrayList<>();
        String sortingCriteriaInputValues = inputParams.getOrDefault(sortParamName, "");
        if (sortingCriteriaInputValues.isEmpty()) {
            return sortCriteria;
        }

        sortCriteria = Arrays.asList(sortingCriteriaInputValues.split(",")).stream()
                .filter(s -> sortCriteriaNames.contains(s.toUpperCase()))
                .map(s -> CertificateSortCriteria.valueOf(s.toUpperCase()))
                .collect(Collectors.toList());

        return sortCriteria;
    }


}
