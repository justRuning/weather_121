package com.hebj.forecast.service;

import java.sql.Date;

import com.hebj.forecast.entity.Fire;

public interface FireService {

	Fire makeFire();

	Fire getFire(Date date);

	void sava(Fire fire);

}
