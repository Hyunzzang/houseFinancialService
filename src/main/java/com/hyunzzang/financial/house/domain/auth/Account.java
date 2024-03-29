package com.hyunzzang.financial.house.domain.auth;

import com.hyunzzang.financial.house.common.util.CipherUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    name = "account",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"authId"}
        )
    }
)
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String authId;

  @Column
  private String pw;

  @Column(updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Builder
  public Account(String authId, String pw) throws NoSuchAlgorithmException {
    Assert.notNull(authId, "인증 아이디가 널 입니다.");
    Assert.notNull(pw, "패스워드가 널 입니다.");

    this.authId = authId;
    this.pw = encrypt(pw);
  }


  public boolean verifyPassword(String pwd) throws NoSuchAlgorithmException {
    String encrPwd = encrypt(pwd);
    return StringUtils.equals(this.pw, encrPwd);
  }


  private String encrypt(String target) throws NoSuchAlgorithmException {
    // todo SALT 를 적용 할까?
    return CipherUtil.sha256(target);
  }
}
