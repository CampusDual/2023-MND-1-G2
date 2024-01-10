package com.campusdual.model.core.dao;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;


@Repository(value = "UserRoleDao")
@Lazy
@ConfigurationFile(
	configurationFile = "dao/UserRoleDao.xml",
	configurationFilePlaceholder = "dao/placeholders.properties")
public class UserRoleDao extends OntimizeJdbcDaoSupport {

	public static final String ID = "ID_USER_ROLE";
	public static final String ID_ROLENAME = "ID_ROLENAME";
	public static final String userRoleQuery = "userRole";
	public static final String ID_SERVER_PERMISSION = "ID_SERVER_PERIMISSION";
}