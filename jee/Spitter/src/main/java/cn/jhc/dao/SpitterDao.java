package cn.jhc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.jhc.domain.Spitter;

public interface SpitterDao extends JpaRepository<Spitter, Long> {

	Spitter findByUsername(String username);
}
