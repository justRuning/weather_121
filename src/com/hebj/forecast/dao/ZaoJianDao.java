package com.hebj.forecast.dao;

import java.sql.Date;

import com.hebj.forecast.entity.ZaoJian;

public interface ZaoJianDao {

	ZaoJian getZaoJian(Date date);

	void saveZaoJian(ZaoJian zaojian);

}
