/*
 * 
 * 
 * 
 */
package net.shop.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shop.Filter;
import net.shop.Order;
import net.shop.entity.Brand;
import net.shop.service.BrandService;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 品牌列表
 * 
 * 
 * 
 */
@Component("brandListDirective")
public class BrandListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "brands";

	@Resource(name = "brandServiceImpl")
	private BrandService brandService;

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<Brand> brands;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Brand.class);
		List<Order> orders = getOrders(params);
		if (useCache) {
			brands = brandService.findList(count, filters, orders, cacheRegion);
		} else {
			brands = brandService.findList(count, filters, orders);
		}
		setLocalVariable(VARIABLE_NAME, brands, env, body);
	}

}