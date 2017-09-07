package com.hebj.forecast.dao;

import java.sql.Date;

import com.hebj.forecast.entity.AllMsg;

public interface AllMsgDao {

	AllMsg getAllMsg(Date date);

	void saveAllMsg(AllMsg AllMsg);

}
