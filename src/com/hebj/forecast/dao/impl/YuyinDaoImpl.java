package com.hebj.forecast.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.YuyinDao;
import com.hebj.forecast.entity.Yuyin;

@Service
public class YuyinDaoImpl implements YuyinDao {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public List<String> getYuyinByPinyin(String pinyin) {

		String sql = "select hanzi from Yuyin where pinyin like '" + pinyin + "%' order by LENGTH(hanzi)";
		Object[] params = new Object[1];
		params[0] = pinyin;
		hibernateTemplate.setMaxResults(10);

		List<?> yuyins = hibernateTemplate.find(sql);

		return (List<String>) yuyins;
	}

}
