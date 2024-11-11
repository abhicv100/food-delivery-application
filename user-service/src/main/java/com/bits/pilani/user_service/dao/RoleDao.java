package com.bits.pilani.user_service.dao;

import org.springframework.data.repository.ListCrudRepository;

import com.bits.pilani.user_service.entity.RoleEntity;

public interface RoleDao extends ListCrudRepository<RoleEntity, Integer>{

}
