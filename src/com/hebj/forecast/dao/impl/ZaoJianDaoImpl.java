package com.hebj.forecast.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.ZaoJianDao;
import com.hebj.forecast.entity.ZaoJian;

@Service
public class ZaoJianDaoImpl implements ZaoJianDao {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public ZaoJian getZaoJian(Date date) {
		String sql = "from ZaoJian where date=?";
		Object[] params = new Object[1];
		params[0] = date;
		hibernateTemplate.setMaxResults(1);

		List<?> ZaoJian = hibernateTemplate.find(sql, params);

		if (ZaoJian.isEmpty()) {
			ZaoJian zaoJian2 = new ZaoJian();
			zaoJian2.content = "";
			return zaoJian2;
		}

		return (com.hebj.forecast.entity.ZaoJian) ZaoJian.get(0);
	}

	@Override
	public void saveZaoJian(ZaoJian zaojian) {

		String hql = "from ZaoJian z where  z.date = ?";
		Object[] params = new Object[1];
		params[0] = zaojian.getDate();
		List<?> weather = hibernateTemplate.find(hql, params);
		if (!weather.isEmpty()) {
			hibernateTemplate.deleteAll(weather);
		}
		hibernateTemplate.save(zaojian);
		hibernateTemplate.flush();

	}

}
