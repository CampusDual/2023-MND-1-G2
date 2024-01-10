package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "UserSubDao")
@Lazy
@ConfigurationFile(configurationFile = "dao/UserSubDao.xml", configurationFilePlaceholder = "dao/placeholders.properties")
public class UserSubDao extends OntimizeJdbcDaoSupport {
    public static final String ID = "USER_SUB_ID";
    public static final String USER = "USER_";
    public static final String SUBS_ID = "SUBS_ID";
    public static final String USER_SUB_VIRTUAL = "USER_SUB_VIRTUAL";
}