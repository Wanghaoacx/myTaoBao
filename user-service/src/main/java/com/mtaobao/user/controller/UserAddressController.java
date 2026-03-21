package com.mtaobao.user.controller;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.user.entity.UserAddress;
import com.mtaobao.user.service.UserAddressService;
import com.mtaobao.user.vo.UserAddressVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/address")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService addressService;

    @GetMapping("/list")
    public Result<List<UserAddressVO>> getAddressList(@RequestParam Long userId) {
        List<UserAddress> list = addressService.getAddressList(userId);
        List<UserAddressVO> voList = list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return Result.success(voList);
    }

    @GetMapping("/default")
    public Result<UserAddressVO> getDefaultAddress(@RequestParam Long userId) {
        UserAddress address = addressService.getDefaultAddress(userId);
        return Result.success(convertToVO(address));
    }

    @GetMapping("/{id}")
    public Result<UserAddressVO> getById(@PathVariable Long id) {
        UserAddress address = addressService.getById(id);
        return Result.success(convertToVO(address));
    }

    @PostMapping
    public Result<Void> addAddress(@RequestBody UserAddress address) {
        addressService.addAddress(address);
        return Result.success();
    }

    @PutMapping
    public Result<Void> updateAddress(@RequestBody UserAddress address) {
        addressService.updateAddress(address);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return Result.success();
    }

    @PutMapping("/default")
    public Result<Void> setDefaultAddress(@RequestParam Long userId, @RequestParam Long addressId) {
        addressService.setDefaultAddress(userId, addressId);
        return Result.success();
    }

    private UserAddressVO convertToVO(UserAddress address) {
        if (address == null) return null;
        UserAddressVO vo = new UserAddressVO();
        vo.setId(address.getId());
        vo.setUserId(address.getUserId());
        vo.setReceiverName(address.getReceiverName());
        vo.setReceiverPhone(address.getReceiverPhone());
        vo.setProvince(address.getProvince());
        vo.setCity(address.getCity());
        vo.setDistrict(address.getDistrict());
        vo.setDetailAddress(address.getDetailAddress());
        vo.setPostalCode(address.getPostalCode());
        vo.setIsDefault(address.getIsDefault());
        return vo;
    }
}
