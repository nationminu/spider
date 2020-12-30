package com.github.nationminu.spider.sraper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate; 

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
 
import com.github.nationminu.spider.domain.Site;
import com.github.nationminu.spider.filter.Filter;
import com.github.nationminu.spider.repository.SiteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpiderScheduler {

	@Autowired
	SiteRepository siterepository;

	@Scheduled(cron = "${schedluer.job.cron}")
	public void run() {
		StopWatch crawlerWatch = new StopWatch("SpiderThreads");

		Document doc;
		//Location location = new Location();

		crawlerWatch.start("totalTasks");
		try {

			for (Site s : siterepository.findAll()) {
				String id = s.getId();
				String url = s.getUrl();

				log.info("Connect to : {} , {}", id, url);

				doc = Jsoup.connect(s.getUrl()).userAgent("SpiderBot").timeout(50000).get();

				Elements elements = doc.select("a[href]");
				int record_count = 1;
				for (Element element : elements) {
					String link = element.attr("href").toString();
					String title = element.text().toLowerCase();

					if (link.startsWith("/") || link.startsWith("?")) {
						link = getDomainName(url) + link;
					}
					if (link.startsWith("./") || link.startsWith("?")) {
						link = getDomainName(url) + link.substring(1);
					}
					if (link.startsWith(getDomainName(url))) {

//						log.info("HTML Document: {} , {}", title, link);
						
						// URL Filter Include
//						boolean inc_regex = Pattern.matches("https://rockplace.co.kr/bbs_post/notice/(.*?)/", link);
//						if (inc_regex == false)
//							continue;

						List<String> filter = new ArrayList<>();
						if(link.startsWith(getDomainName("https://rockplace.co.kr"))) {
							filter.add("(.*?)/bbs_post/notice/(.*?)/");							
						}
						if(link.startsWith(getDomainName("https://www.autodaily.co.kr"))) {
							filter.add("(.*?)/news/articleView.html\\?idxno=(.*?)");								
						}
						if(Filter.url_inc_filter(filter,link) == false) continue;

						// URL Filter Exclude
//						boolean exd_regex = Pattern.matches("(.*?)#$", link);
//						if (exd_regex == true)
//							continue; 
						filter.removeAll(filter);
						//filter.add("(.*?)/$");
						filter.add("(.*?)#$");
						filter.add("(.*?)\\.html$");
						filter.add("(.*?)\\.php$");
						if(Filter.url_exc_filter(filter,link) == false) continue;

						log.info("{} Gathering URL of HTML Document: {} , {}", record_count, title, link);
						record_count++;
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		crawlerWatch.stop();
		log.info("it takes {}", crawlerWatch.prettyPrint());
	}
  
	public static String getDomainName(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String domain = uri.getScheme() + "://" + uri.getHost();
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	public static String getPath(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String path = uri.getPath();
		return path;
	}

	public static int getPathDepth(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String domain = uri.getPath();
		String[] depth = domain.split("/");
		return depth.length;
	}

	// Utility function
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}