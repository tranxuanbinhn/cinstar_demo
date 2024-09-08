package com.xb.cinstar.repository;

import com.xb.cinstar.models.RefreshToken;
import com.xb.cinstar.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRefreshToken extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUserModel(UserModel userModel);
    @Modifying
    @Query(value = "delete from refreshtoken where userid = ?", nativeQuery = true)
    int deleteAllByUserId(Long id);
}
