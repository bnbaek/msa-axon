package net.open.payment.repository.holder;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by BNBAEK
 * Package : net.open.payment.repository.holder
 * User: dean
 * Date: 2020/06/01
 * Time: 2:08 오후
 */

@Getter
@Entity
@Table(name = "HOLDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holder {

  @Id
  @Column(name = "holder_id", nullable = false)
  private String holderId;

  @Column(name = "user_id", unique = true)
  private String userId;

  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String phoneNumber;

  private String address;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  public Holder(String holderId, String userId, String name, String phoneNumber, String address, LocalDateTime createdAt) {
    this.holderId = holderId;
    this.userId = userId;
    this.address = address;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
  }
}

