package com.hebj.forecast.dao;

import java.util.List;

import com.hebj.forecast.entity.Yuyin;

public interface YuyinDao {

	List<String> getYuyinByPinyin(String pinyin);

}
