package com.hebj.forecast.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.FireDao;
import com.hebj.forecast.entity.Fire;
import com.sun.org.apache.regexp.internal.recompile;

@Service
public class FireDaoImpl implements FireDao {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public Fire getFire(Date date) {
		String sql = "from Fire f where f.date=?";
		Object[] params = new Object[1];
		params[0] = date;
		hibernateTemplate.setMaxResults(1);

		List<?> Fire = hibernateTemplate.find(sql, params);

		if (Fire.isEmpty()) {
			Fire fire2 = new Fire();
			fire2.setContent("");
			return fire2;
		}

		return (com.hebj.forecast.entity.Fire) Fire.get(0);
	}

	@Override
	public void saveFire(Fire Fire) {

		String hql = "from Fire z where  z.date = ?";
		Object[] params = new Object[1];
		params[0] = Fire.getDate();
		List<?> fires = hibernateTemplate.find(hql, params);
		if (!fires.isEmpty()) {
			hibernateTemplate.deleteAll(fires);
		}
		hibernateTemplate.save(Fire);
		hibernateTemplate.flush();

	}

}
