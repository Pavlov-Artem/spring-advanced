package com.epam.esm.service.data;

import java.util.List;

public class CreateOrderDto {
    private Long userId;
    private Long certificateId;

    public CreateOrderDto(Long userId, Long certificateId) {
        this.userId = userId;
        this.certificateId = certificateId;
    }

    public CreateOrderDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
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