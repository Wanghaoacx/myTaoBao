package com.mtaobao.trade.feign;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.trade.dto.UserAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service", url = "${feign.user-service.url:http://localhost:8201}")
public interface UserFeignClient {

    @GetMapping("/user/{id}")
    Result<?> getUserById(@PathVariable("id") Long id);

    @GetMapping("/user/address/list")
    Result<List<UserAddressDTO>> getAddressList(@RequestParam("userId") Long userId);

    @GetMapping("/user/address/default")
    Result<UserAddressDTO> getDefaultAddress(@RequestParam("userId") Long userId);

    @GetMapping("/user/address/{id}")
    Result<UserAddressDTO> getAddressById(@PathVariable("id") Long id);
}
