package com.github.nationminu.spider.filter;

import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Filter {

	public static boolean url_inc_filter(List<String> rxs, String input) {
		for (String rx : rxs) {
			// log.info("{} {}", rx, input);
			// log.info("" + Pattern.matches(rx, input));
			if (Pattern.matches(rx, input) == false)
				return false;
		}
		return true;
	}

	public static boolean url_exc_filter(List<String> rxs, String input) {
		for (String rx : rxs) {
			// log.info("{} {}", rx, input);
			// log.info("" + Pattern.matches(rx, input));

			if (Pattern.matches(rx, input) == true)
				return false;
		}
		return true;
	}
}
