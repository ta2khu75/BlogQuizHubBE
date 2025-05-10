package com.ta2khu75.quiz.repository.account;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.repository.custom.AccountRepositoryCustom;

public interface AccountRepository extends JpaRepository<Account, String>, AccountRepositoryCustom {
	@Query("SELECT a FROM Account a JOIN FETCH a.profile JOIN FETCH a.status WHERE a.email = :email")
	Optional<Account> findByEmailWithProfileWithStatus(@Param("email") String email);

//	boolean existsByIdAndRoleName(String accountId, String roleName);

	boolean existsByEmail(String email);

//	Optional<Account> findByEmailAndRefreshToken(String email, String refreshToken);

//	Optional<Account> findByCodeVerify(String codeVerify);
	Optional<Account> findByEmail(String email);
//	@Query("SELECT a FROM Account a JOIN a.profile p JOIN a.status s JOIN s.role r WHERE "
//			+ "(:keyword IS NULL OR p.displayName LIKE %:keyword% OR a.email LIKE %:keyword%) "
//			+ "AND (:roleId IS NULL OR r.id = :roleId) "
//			+ "AND (:enabled IS NULL OR s.enabled = :enabled) "
//			+ "AND (:nonLocked IS NULL OR s.nonLocked = :nonLocked) ")
//	Page<Account> search(@Param("keyword") String keyword, @Param("roleId") Long roleId, @Param("enabled") Boolean enabled, @Param("nonLocked") Boolean nonLocked , Pageable pageable);
}
