package com.xb.cinstar.service;

import com.xb.cinstar.exception.TokenRefreshException;
import com.xb.cinstar.models.RefreshToken;
import com.xb.cinstar.models.UserModel;
import com.xb.cinstar.repository.IRefreshToken;
import com.xb.cinstar.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired private IUserRepository userRepository;
    @Autowired private IRefreshToken refreshTokenRepository;
    @Value("${cinstar.app.jwtRefreshExpirationMs}")
    private  Long refreshTokenDurationMs;


    public Optional<RefreshToken> findByRefreshToken(String token)
    {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userID)
    {
        UserModel userModel = userRepository.findById(userID).get();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setUserModel(userModel);
        refreshToken.setToken(UUID.randomUUID().toString());
      refreshToken =   refreshTokenRepository.save(refreshToken);
            return  refreshToken;

    }
    public  RefreshToken verifyExpiration(RefreshToken refreshToken)
    {
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0)
        {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return  refreshToken;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserModel(userRepository.findById(userId).get());
    }

}
