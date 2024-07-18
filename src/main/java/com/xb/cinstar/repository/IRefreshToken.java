package com.xb.cinstar.repository;

import com.xb.cinstar.models.RefreshToken;
import com.xb.cinstar.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRefreshToken extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUserModel(UserModel userModel);
}
