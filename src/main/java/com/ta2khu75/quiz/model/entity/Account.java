package com.ta2khu75.quiz.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ta2khu75.quiz.model.entity.base.EntityBaseString;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "role", "blogs", "quizzes", "followers", "following" })
@EqualsAndHashCode(callSuper = true, exclude = { "role", "blogs", "quizzes", "followers", "following" })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends EntityBaseString implements UserDetails {
	private static final long serialVersionUID = -6436446209727776976L;
	@Column(unique = true, nullable = false)
	String email;
	@Column(unique = true, nullable = false)
	String displayName;
	@Column(nullable = false)
	String firstName;
	@Column(nullable = false)
	String lastName;
	@Column(nullable = false)
	String password;
	@Column(nullable = false)
	LocalDate birthday;
	String codeVerify;
	String refreshToken;
	boolean enabled;
	@Builder.Default
	boolean nonLocked = true;

	@ManyToOne
	Role role;
	@OneToMany(mappedBy = "author")
	List<Blog> blogs;
	@OneToMany(mappedBy = "author")
	@JsonIgnore
	List<Quiz> quizzes;
	@OneToMany(mappedBy = "following")
	@JsonIgnore
	Set<Follow> followers;
	@OneToMany(mappedBy = "follower")
	@JsonIgnore
	Set<Follow> following;

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", role.getName()))); // Add role as authority
		authorities.addAll(role.getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getName())).toList());
		return authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonLocked() {
		return nonLocked;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
