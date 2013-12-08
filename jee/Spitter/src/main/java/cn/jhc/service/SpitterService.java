package cn.jhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import cn.jhc.dao.SpitterDao;
import cn.jhc.dao.SpittleDao;
import cn.jhc.domain.Spitter;
import cn.jhc.domain.Spittle;

@Component
public class SpitterService {
	
	@Autowired
	private SpittleDao spittleDao;
	
	@Autowired
	private SpitterDao spitterDao;
	
	public List<Spittle> getRecentSpittles(int count){
		return spittleDao.findAll(new PageRequest(0, count,
				Sort.Direction.DESC, "when")).getContent();
	}
	
	public Spitter getSpitter(String username) {
		return spitterDao.findByUsername(username);
	}

	public Object getSpittlesForSpitter(String username) {
		return spittleDao.findBySpitterUsername(username);
	}

	public void saveSpitter(Spitter spitter) {
		spitterDao.save(spitter);
	}
}
