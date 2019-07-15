package com.yuehe.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuehe.app.entity.Profile;

/**
 * @author Soveran Zhong
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Profile findBySaleId(String saleId);
}
