package com.github.nationminu.spider;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.nationminu.spider.domain.Filter;
import com.github.nationminu.spider.domain.Location;
import com.github.nationminu.spider.domain.Site;
import com.github.nationminu.spider.repository.SiteRepository;
import com.github.nationminu.spider.repository.FilterRepository;
import com.github.nationminu.spider.repository.LocationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SpiderApplicationTest {

	@Autowired
	SiteRepository repository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	FilterRepository filterRepository;

	@DisplayName("Test Spring @Autowired Integration")
	@Test
	void contextTests() {

		try {
			log.info("==========Delete all site entities==========");
			repository.deleteAll();
			locationRepository.deleteAll();
			filterRepository.deleteAll();

			String siteid = UUID.randomUUID().toString();
			System.out.println("==========Save list of site entities==========");
			repository.save(new Site(siteid, "rockplace", "https://rockplace.co.kr/bbs/notice/"));


			//locationRepository.save(new Location(UUID.randomUUID().toString(), siteid, LocalDateTime.now().toString(),
			//		"https://rockplace.co.kr/bbs/notice/"));

			filterRepository
					.save(new Filter(UUID.randomUUID().toString(), siteid, "include", "(.*?)/bbs_post/notice/(.*?)/"));

			filterRepository.save(new Filter(UUID.randomUUID().toString(), siteid, "exclude", "(.*?)#$"));
			filterRepository.save(new Filter(UUID.randomUUID().toString(), siteid, "exclude", "(.*?).html$"));
			filterRepository.save(new Filter(UUID.randomUUID().toString(), siteid, "exclude", "(.*?).php$"));

			String siteid2 = UUID.randomUUID().toString();			
			repository.save(new Site(siteid2, "autodaily news", "https://www.autodaily.co.kr/news/articleList.html?sc_section_code=S1N1&view_type=sm"));

			filterRepository
					.save(new Filter(UUID.randomUUID().toString(), siteid2, "include", "(.*?)/news/articleView.html\\\\?idxno=(.*?)"));

			filterRepository.save(new Filter(UUID.randomUUID().toString(), siteid2, "exclude", "(.*?)#$"));
			filterRepository.save(new Filter(UUID.randomUUID().toString(), siteid2, "exclude", "(.*?).html$"));
			filterRepository.save(new Filter(UUID.randomUUID().toString(), siteid2, "exclude", "(.*?).php$"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
