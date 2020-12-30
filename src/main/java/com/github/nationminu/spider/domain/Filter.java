package com.github.nationminu.spider.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Filter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String siteid;
	private String type;
	private String regex;

	public Filter(String id, String siteid, String type, String regex) {
		this.id = id;
		this.siteid = siteid;
		this.type = type;
		this.regex = regex;
	}
}
