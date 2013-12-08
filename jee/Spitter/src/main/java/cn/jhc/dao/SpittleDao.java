package cn.jhc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.jhc.domain.Spittle;

public interface SpittleDao extends JpaRepository<Spittle, Long> {

	List<Spittle> findBySpitterUsername(String username);
}
