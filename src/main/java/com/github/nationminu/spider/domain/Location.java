package com.github.nationminu.spider.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Document
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String siteid;
	private String version;
	private String location;

	public Location(String id, String siteid, String version, String location) {
		this.id = id;
		this.siteid = siteid;
		this.version = version;
		this.location = location;
	}
}
