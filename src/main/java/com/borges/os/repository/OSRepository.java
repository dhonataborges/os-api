package com.borges.os.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.borges.os.domain.OS;

@Repository
public interface OSRepository extends JpaRepository<OS, Integer> {

}
