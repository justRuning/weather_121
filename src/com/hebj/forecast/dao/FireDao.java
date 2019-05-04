package com.hebj.forecast.dao;

import java.sql.Date;

import com.hebj.forecast.entity.Fire;

public interface FireDao {

	Fire getFire(Date date);

	void saveFire(Fire fire);

}
