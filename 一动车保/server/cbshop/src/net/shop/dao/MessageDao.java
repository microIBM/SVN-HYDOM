/*
 * 
 * 
 * 
 */
package net.shop.dao;

import net.shop.Page;
import net.shop.Pageable;
import net.shop.entity.Member;
import net.shop.entity.Message;

/**
 * Dao - 消息
 * 
 * 
 * 
 */
public interface MessageDao extends BaseDao<Message, Long> {

	/**
	 * 查找消息分页
	 * 
	 * @param member
	 *            会员，null表示管理员
	 * @param pageable
	 *            分页信息
	 * @return 消息分页
	 */
	Page<Message> findPage(Member member, Pageable pageable);

	/**
	 * 查找草稿分页
	 * 
	 * @param sender
	 *            发件人，null表示管理员
	 * @param pageable
	 *            分页信息
	 * @return 草稿分页
	 */
	Page<Message> findDraftPage(Member sender, Pageable pageable);

	/**
	 * 查找消息数量
	 * 
	 * @param member
	 *            会员，null表示管理员
	 * @param read
	 *            是否已读
	 * @return 消息数量，不包含草稿
	 */
	Long count(Member member, Boolean read);

	/**
	 * 删除消息
	 * 
	 * @param id
	 *            ID
	 * @param member
	 *            执行人，null表示管理员
	 */
	void remove(Long id, Member member);

}