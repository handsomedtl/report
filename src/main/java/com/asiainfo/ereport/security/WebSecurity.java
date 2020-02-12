package com.asiainfo.ereport.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.asiainfo.eframe.dao.AuthDao;
import com.asiainfo.eframe.entity.RoleInfo;

@Service("webSecurity")
public class WebSecurity {
	@Resource(name = "authDaoImpl")
	private AuthDao authDaoImpl;

	public boolean checkUserId(Authentication authentication, String path, String pathVariable) {
		if (authentication == null) {
			return false;
		}
		if (authentication.isAuthenticated() && authentication.getName().equalsIgnoreCase("superusr")) {
			return true;
		}
		boolean result = false;
		String url = path + "/" + pathVariable;
		List<RoleInfo> roles = authDaoImpl.getRoleListByResUrl(url);
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		if (roles == null || roles.isEmpty()) {
			return true;
		} else {
			result = false;
		}
		for (RoleInfo roleInfo : roles) {
			ConfigAttribute ca = new SecurityConfig(roleInfo.getRightcode());
			atts.add(ca);
		}
		for (ConfigAttribute attribute : atts) {
			String needRole = ((SecurityConfig) attribute).getAttribute();
			// authority为用户所被赋予的权限, needRole 为访问相应的资源应该具有的权限。
			for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				if (needRole.equals(grantedAuthority.getAuthority()))
					return true;
			}
		}
		return result;
	}
}
