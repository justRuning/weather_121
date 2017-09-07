package com.hebj.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yuyin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String hanzi;
	private String pinyin;

	public Yuyin() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHanzi() {
		return hanzi;
	}

	public void setHanzi(String hanzi) {
		this.hanzi = hanzi;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Override
	public String toString() {
		return "Yuyin [id=" + id + ", hanzi=" + hanzi + ", pinyin=" + pinyin + "]";
	}

}
