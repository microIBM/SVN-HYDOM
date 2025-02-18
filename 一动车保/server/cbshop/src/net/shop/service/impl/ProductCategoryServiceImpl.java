/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shop.dao.ProductCategoryDao;
import net.shop.entity.ProductCategory;
import net.shop.service.ProductCategoryService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 商品分类
 * 
 * 
 * 
 */
@Service("productCategoryServiceImpl")
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory, Long> implements ProductCategoryService {

	@Resource(name = "productCategoryDaoImpl")
	private ProductCategoryDao productCategoryDao;

	@Resource(name = "productCategoryDaoImpl")
	public void setBaseDao(ProductCategoryDao productCategoryDao) {
		super.setBaseDao(productCategoryDao);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findRoots() {
		return productCategoryDao.findRoots(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findRoots(Integer count) {
		return productCategoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("productCategory")
	public List<ProductCategory> findRoots(Integer count, String cacheRegion) {
		return productCategoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findParents(ProductCategory productCategory) {
		return productCategoryDao.findParents(productCategory, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findParents(ProductCategory productCategory, Integer count) {
		return productCategoryDao.findParents(productCategory, count);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("productCategory")
	public List<ProductCategory> findParents(ProductCategory productCategory, Integer count, String cacheRegion) {
		return productCategoryDao.findParents(productCategory, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findTree() {
		return productCategoryDao.findChildren(null, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findChildren(ProductCategory productCategory) {
		return productCategoryDao.findChildren(productCategory, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findChildren(ProductCategory productCategory, Integer count) {
		return productCategoryDao.findChildren(productCategory, count);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("productCategory")
	public List<ProductCategory> findChildren(ProductCategory productCategory, Integer count, String cacheRegion) {
		return productCategoryDao.findChildren(productCategory, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void save(ProductCategory productCategory) {
		super.save(productCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public ProductCategory update(ProductCategory productCategory) {
		return super.update(productCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public ProductCategory update(ProductCategory productCategory, String... ignoreProperties) {
		return super.update(productCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(ProductCategory productCategory) {
		super.delete(productCategory);
	}

}