package com.hebj.forecast.service;

import java.sql.Date;

import com.hebj.forecast.entity.AllMsg;

public interface AllMsgService {

	AllMsg makeAllMsg();

	AllMsg getAllMsg(Date date);

	void sava(AllMsg allMsg);

}
