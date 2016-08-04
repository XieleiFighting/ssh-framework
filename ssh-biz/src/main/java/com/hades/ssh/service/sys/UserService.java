package com.hades.ssh.service.sys;

import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hades.ssh.common.entity.search.Searchable;
import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.enums.UserStatus;
import com.hades.ssh.entity.sys.User;
import com.hades.ssh.entity.sys.UserOrganizationJob;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月4日 下午6:52:40
 * <p>Version: 1.0
 */
public interface UserService extends BaseService<User, Long> {

	User save(User user);
	
	User update(User user);
	
	UserOrganizationJob findUserOrganizationJob(UserOrganizationJob userOrganizationJob);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User findByMobilePhoneNumber(String mobilePhoneNumber);
	
	User changePassword(User user, String newPassword);
	
	User changeStatus(User opUser, User user, UserStatus newStatus, String reason);
	
	User login(String username, String password);
	
	void changePassword(User opUser, Long[] ids, String newPassword);
	
	void changeStatus(User opUser, Long[] ids, UserStatus newStatus, String reason);
	
	Set<Map<String, Object>> findIdAndNames(Searchable searchable, String usernme);
	
	/**
     * 获取那些在用户-组织机构/工作职务中存在 但在组织机构/工作职务中不存在的
     *
     * @param pageable
     * @return
     */
	Page<UserOrganizationJob> findUserOrganizationJobOnNotExistsOrganizationOrJob(Pageable pageable);
	
	/**
     * 删除用户不存在的情况的UserOrganizationJob（比如手工从数据库物理删除）。。
     *
     * @return
     */
	void deleteUserOrganizationJobOnNotExistsUser();
	
}
