package com.ta2khu75.quiz.model.entity;

import java.time.Instant;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String codeVerify;
	String refreshToken;
    boolean enabled; 
    boolean nonLocked = true;
	@LastModifiedDate
	@Column(insertable = false)
	Instant updatedAt;
	@LastModifiedBy
	@Column(insertable = false)
	String updatedBy;
    @OneToOne(mappedBy = "status")
    Account account;
	@ManyToOne
	Role role;
}
