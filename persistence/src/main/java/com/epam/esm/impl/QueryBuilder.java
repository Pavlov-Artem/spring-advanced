package com.epam.esm.impl;

import com.epam.esm.DAOConstants;
import com.epam.esm.data.GiftCertificate;
import com.epam.esm.data.Tag;
import com.epam.esm.data.User;
import com.epam.esm.data.UserOrder;
import com.epam.esm.service.CertificateSearchCriteria;
import com.epam.esm.service.CertificateSortCriteria;
import com.epam.esm.service.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

import static com.epam.esm.DAOConstants.CERTIFICATE_TABLE;


class QueryBuilder {

    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    static String updateChangedCertificateRowsBuilder(GiftCertificate oldCertificate, GiftCertificate newCertificate) throws DAOException {

        StringBuilder columnsToUpdate = new StringBuilder("update " + CERTIFICATE_TABLE + " set ");
        if (oldCertificate.equals(newCertificate)) {
            throw new DAOException("nothing to update");
        } else if (!oldCertificate.getName().equals(newCertificate.getName())) {
            columnsToUpdate.append(String.format(" name= '%s',", newCertificate.getName()));
        } else if (!oldCertificate.getDescription().equals(newCertificate.getDescription())) {
            columnsToUpdate.append(String.format(" description= '%s',", newCertificate.getDescription()));
        } else if (!oldCertificate.getPrice().equals(newCertificate.getPrice())) {
            columnsToUpdate.append(String.format(" price= '%s',", newCertificate.getPrice()));
        } else if (oldCertificate.getDuration() != newCertificate.getDuration()) {
            columnsToUpdate.append(String.format(" duration= '%s',", newCertificate.getDuration()));
        }

        columnsToUpdate.append(String.format(" last_update_date= '%s'", newCertificate.getLastUpdateTime()));
        columnsToUpdate.append(" where id=").append(oldCertificate.getId());
        return columnsToUpdate.toString();
    }

    static String buildCompoundQuery(Map<CertificateSearchCriteria, String> searchCriteria) {

        StringBuilder compoundQuery = new StringBuilder();
        compoundQuery.append(buildSelectQueryConditionPart(searchCriteria))
//                .append(buildSortPart(sortCriteria))
                .append(";");


        return compoundQuery.toString();
    }


    static String buildSelectQueryConditionPart(Map<CertificateSearchCriteria, String> searchCriteria) {

        // if true we need to put 'and' before new condition
        boolean firstCondition = false;
        StringBuilder queryConditionPart;

        if (searchCriteria.containsKey(CertificateSearchCriteria.BY_TAG)) {
            queryConditionPart = new StringBuilder("select gc.* from gift_certificate gc " +
                    "                              join gift_certificate_has_tag gt  " +
                    "                              on gt.gift_certificate_id = gc.id  " +
                    "                              join tag t " +
                    "                              on t.id = gt.tag_id " +
                    "                              WHERE t.name IN ")
                    .append(String.format(" ('%s') ", searchCriteria.get(CertificateSearchCriteria.BY_TAG)));
            searchCriteria.remove(CertificateSearchCriteria.BY_TAG);
            firstCondition = true;
        } else {
            queryConditionPart = new StringBuilder("select gc.* from gift_certificate gc ");
        }


        for (Map.Entry<CertificateSearchCriteria, String> entry : searchCriteria.entrySet()) {
            if (firstCondition) {
                queryConditionPart.append(" AND ");
            } else {
                queryConditionPart.append(" WHERE ");
            }

            if (entry.getKey().equals(CertificateSearchCriteria.BY_NAME_PART)) {
                byNamePartCondition(queryConditionPart, entry.getValue());
                firstCondition = true;
            } else if (entry.getKey().equals(CertificateSearchCriteria.BY_DESCRIPTION_PART)) {
                byDescriptionPart(queryConditionPart, entry.getValue());
                firstCondition = true;
            } else {
                break;
            }

        }

        return queryConditionPart.toString();

    }


    private static void byNamePartCondition(StringBuilder queryConditionPart, String value) {
        queryConditionPart.append("gc.")
                .append(DAOConstants.GC_NAME)
                .append(" like ")
                .append("'%")
                .append(value)
                .append("%' ");

    }

    private static void byDescriptionPart(StringBuilder queryConditionPart, String value) {
        queryConditionPart.append("gc.")
                .append(DAOConstants.GC_DESCRIPTION)
                .append(" like ")
                .append("'%")
                .append(value)
                .append("%' ");
    }


}
