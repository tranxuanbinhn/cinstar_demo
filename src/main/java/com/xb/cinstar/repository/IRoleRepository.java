package com.xb.cinstar.repository;

import com.xb.cinstar.models.ERole;
import com.xb.cinstar.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel>findByName(ERole name);
    Boolean existsByName(String name);
}
