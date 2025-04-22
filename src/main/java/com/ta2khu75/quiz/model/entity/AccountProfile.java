package com.ta2khu75.quiz.model.entity;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {  "blogs", "quizzes", "followers", "following" })
@EqualsAndHashCode( exclude = { "blogs", "quizzes", "followers", "following" })
public class AccountProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(unique = true, nullable = false)
	String displayName;
	@Column(nullable = false)
	String firstName;
	@Column(nullable = false)
	String lastName;
	@Column(nullable = false)
	Instant birthday;
	@LastModifiedDate
	@Column(insertable = false)
	Instant updatedAt;
    @OneToOne(mappedBy = "profile")
	Account account;
	@OneToMany(mappedBy = "author")
	List<Blog> blogs;
	@OneToMany(mappedBy = "author")
	List<Quiz> quizzes;
	@OneToMany(mappedBy = "following")
	Set<Follow> followers;
	@OneToMany(mappedBy = "follower")
	Set<Follow> following;
}
