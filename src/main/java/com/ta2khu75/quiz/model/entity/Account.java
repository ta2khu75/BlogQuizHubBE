package com.ta2khu75.quiz.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ta2khu75.quiz.model.entity.base.EntityBaseString;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "status"})
@EqualsAndHashCode(callSuper = true, exclude = { "status"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends EntityBaseString implements UserDetails {
	private static final long serialVersionUID = -6436446209727776976L;
	@Column(unique = true, nullable = false)
	String email;
	@Column(nullable = false)
	String password;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	AccountStatus status;
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	AccountProfile profile;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", status.getRole().getName())));
		authorities.addAll(status.getRole().getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getName())).toList());
		return authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonLocked() {
		return status.isNonLocked();
	}

	@Override
	public boolean isEnabled() {
		return status.isEnabled();
	}
}
