package com.mtaobao.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private String avatar;

    private Integer status;

    private LocalDateTime createTime;
}
