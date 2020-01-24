package com.hyunzzang.financial.house.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAuthId(String authId);
}
