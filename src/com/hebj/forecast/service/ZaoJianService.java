package com.hebj.forecast.service;

import java.sql.Date;

import com.hebj.forecast.entity.ZaoJian;

public interface ZaoJianService {

	ZaoJian makeZaoJian();

	ZaoJian getZaoJian(Date date);

	void sava(ZaoJian zaoJian);

}
