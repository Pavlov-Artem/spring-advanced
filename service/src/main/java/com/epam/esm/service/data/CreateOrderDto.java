package com.epam.esm.service.data;

import java.util.List;

public class CreateOrderDto {
    private Long userId;
    private List<Long> certificateIds;

    public CreateOrderDto(Long userId, List<Long> certificateIds) {
        this.userId = userId;
        this.certificateIds = certificateIds;
    }

    public CreateOrderDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getCertificateIds() {
        return certificateIds;
    }

    public void setCertificateIds(List<Long> certificateIds) {
        this.certificateIds = certificateIds;
    }
}

//{
//        "id": 1,
//        "purchaseDate": "2020-12-07T22:18Z",
//        "user": {
//        "id": 1,
//        "name": "Mirabelle",
//        "email": "mbenne0@amazon.co.uk",
//        "links": []
//        },
//        "orderDetails": [
//        {
//        "id": 1,
//        "cost": 10,
//        "giftCertificate": {
//        "id": 1,
//        "name": "vip_certificate",
//        "description": "very important certificate",
//        "price": 10,
//        "createDate": 1607379479000,
//        "lastUpdateTime": 1607379479000,
//        "duration": 10,
//        "certificateTags": [
//        {
//        "id": 2,
//        "name": "vip",
//        "links": []
//        }
//        ],
//        "links": []
//        }
//        }
//        ]
//        }