package com.hebj.forecast.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.AllMsgDao;
import com.hebj.forecast.entity.AllMsg;
import com.hebj.forecast.entity.Fire;

@Service
public class AllMsgDaoImpl implements AllMsgDao {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public AllMsg getAllMsg(Date date) {
		String sql = "from AllMsg f where f.date=?";
		Object[] params = new Object[1];
		params[0] = date;
		hibernateTemplate.setMaxResults(1);

		List<?> allMsg = hibernateTemplate.find(sql, params);

		if (allMsg.isEmpty()) {
			AllMsg allMsg2 = new AllMsg();
			allMsg2.setContent("");
			return allMsg2;
		}

		return (AllMsg) allMsg.get(0);
	}

	@Override
	public void saveAllMsg(AllMsg AllMsg) {
		String hql = "from AllMsg z where  z.date = ?";
		Object[] params = new Object[1];
		params[0] = AllMsg.getDate();
		List<?> allMsgs = hibernateTemplate.find(hql, params);
		if (!allMsgs.isEmpty()) {
			hibernateTemplate.deleteAll(allMsgs);
		}
		hibernateTemplate.save(AllMsg);
		hibernateTemplate.flush();

	}

}
