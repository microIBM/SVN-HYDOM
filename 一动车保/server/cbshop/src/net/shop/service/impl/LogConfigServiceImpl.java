/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.shop.CommonAttributes;
import net.shop.LogConfig;
import net.shop.service.LogConfigService;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Service - 日志配置
 * 
 * 
 * 
 */
@Service("logConfigServiceImpl")
public class LogConfigServiceImpl implements LogConfigService {

	@Override
	@SuppressWarnings("unchecked")
	@Cacheable("logConfig")
	public List<LogConfig> getAll() {
		try {
			File shopxxXmlFile = new ClassPathResource(CommonAttributes.SHOP_XML_PATH).getFile();
			Document document = new SAXReader().read(shopxxXmlFile);
			List<org.dom4j.Element> elements = document.selectNodes("/shop/logConfig");
			List<LogConfig> logConfigs = new ArrayList<LogConfig>();
			for (org.dom4j.Element element : elements) {
				String operation = element.attributeValue("operation");
				String urlPattern = element.attributeValue("urlPattern");
				LogConfig logConfig = new LogConfig();
				logConfig.setOperation(operation);
				logConfig.setUrlPattern(urlPattern);
				logConfigs.add(logConfig);
			}
			return logConfigs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}