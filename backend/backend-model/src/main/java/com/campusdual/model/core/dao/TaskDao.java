package com.campusdual.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Lazy
@Repository(value = "TaskDao")
@ConfigurationFile(
        configurationFile = "dao/TaskDao.xml",
        configurationFilePlaceholder = "dao/placeholders.properties")
public class TaskDao extends OntimizeJdbcDaoSupport {

    public static final String T_ID = "T_ID";
    public static final String T_NAME = "T_NAME";
    public static final String T_DESCRIPTION = "T_DESCRIPTION";
    public static final String P_ID = "P_ID";
    public static final String T_DATE = "T_DATE";
    public static final String T_TIME = "T_TIME";
    public static final String TS_ID = "TS_ID";
    public static final String T_FINISHED = "T_FINISHED";

        public static final String TOTAL_TASK_TIME = "TOTAL_TASK_TIME";
    public static final String TOTAL_TASK_TIME_DECIMAL = "TOTAL_TASK_TIME_DECIMAL";
    public static final String USER_TASK_TIME = "USER_TASK_TIME";
    public static final String T_OWNER = "T_OWNER";

}

