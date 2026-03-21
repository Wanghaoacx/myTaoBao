package com.mtaobao.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddressVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String receiverName;

    private String receiverPhone;

    private String province;

    private String city;

    private String district;

    private String detailAddress;

    private String postalCode;

    private Integer isDefault;

    /** 完整地址 */
    public String getFullAddress() {
        return province + city + district + detailAddress;
    }
}
