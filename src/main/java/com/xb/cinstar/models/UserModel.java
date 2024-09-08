package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity

@Table(name = "users")
public class UserModel extends BaseEntity{
    private  String fullName;
    private Date birthDay;
    private String phoneNumber;
    private String cic;
    private  String email;
    private String password;
    private  String userName;
    @OneToMany(mappedBy = "sender")
    private List<MessageModel> sentMessages;

    @OneToMany(mappedBy = "receiver")
    private List<MessageModel> receivedMessages;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roles;
    public UserModel() {

    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_promotion", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "promotion_id"))
    private List<PromotionModel> promotions;

    @OneToMany(mappedBy = "userModel")
    private List<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<OrderModel> orders;

    public UserModel(String fullName, Date birthDay, String numberPhone, String cic, String email, String password, String userName) {
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.phoneNumber = numberPhone;
        this.cic = cic;
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
}
