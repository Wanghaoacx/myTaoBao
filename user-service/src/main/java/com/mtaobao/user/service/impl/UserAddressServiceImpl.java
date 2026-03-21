package com.mtaobao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mtaobao.api.common.exception.BusinessException;
import com.mtaobao.api.utils.IdGenerator;
import com.mtaobao.user.entity.UserAddress;
import com.mtaobao.user.mapper.UserAddressMapper;
import com.mtaobao.user.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressMapper addressMapper;

    @Override
    public List<UserAddress> getAddressList(Long userId) {
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAddress::getUserId, userId)
                .orderByDesc(UserAddress::getIsDefault)
                .orderByDesc(UserAddress::getCreateTime);
        return addressMapper.selectList(wrapper);
    }

    @Override
    public UserAddress getDefaultAddress(Long userId) {
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAddress::getUserId, userId)
                .eq(UserAddress::getIsDefault, 1);
        return addressMapper.selectOne(wrapper);
    }

    @Override
    public UserAddress getById(Long id) {
        return addressMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAddress(UserAddress address) {
        // 如果设置为默认地址，先取消其他默认地址
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            clearDefaultAddress(address.getUserId());
        }
        address.setId(IdGenerator.nextId());
        address.setCreateTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        address.setDeleted(0);
        addressMapper.insert(address);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAddress(UserAddress address) {
        UserAddress exist = addressMapper.selectById(address.getId());
        if (exist == null) {
            throw new BusinessException("地址不存在");
        }
        if (!exist.getUserId().equals(address.getUserId())) {
            throw new BusinessException("无权修改此地址");
        }

        // 如果设置为默认地址，先取消其他默认地址
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            clearDefaultAddress(address.getUserId());
        }

        address.setUpdateTime(LocalDateTime.now());
        addressMapper.updateById(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefaultAddress(Long userId, Long addressId) {
        clearDefaultAddress(userId);

        LambdaUpdateWrapper<UserAddress> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserAddress::getId, addressId)
                .eq(UserAddress::getUserId, userId)
                .set(UserAddress::getIsDefault, 1)
                .set(UserAddress::getUpdateTime, LocalDateTime.now());
        addressMapper.update(null, wrapper);
    }

    private void clearDefaultAddress(Long userId) {
        LambdaUpdateWrapper<UserAddress> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserAddress::getUserId, userId)
                .eq(UserAddress::getIsDefault, 1)
                .set(UserAddress::getIsDefault, 0)
                .set(UserAddress::getUpdateTime, LocalDateTime.now());
        addressMapper.update(null, wrapper);
    }
}
