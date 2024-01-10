package com.campusdual.model.core.service;

import com.campusdual.api.core.service.IProjectService;
import com.campusdual.model.core.dao.ProjectDao;
import com.campusdual.model.core.dao.TaskDao;
import com.campusdual.model.core.dao.TimerDao;
import com.campusdual.model.core.dao.UsersProjectDao;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.db.SQLStatementBuilder.BasicOperator;
import com.ontimize.jee.common.db.SQLStatementBuilder.BasicExpression;
import com.ontimize.jee.common.db.SQLStatementBuilder.BasicField;
import com.ontimize.jee.common.db.SQLStatementBuilder.ExtendedSQLConditionValuesProcessor;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import com.ontimize.jee.webclient.export.base.ExcelExportService;
import org.aspectj.apache.bcel.classfile.Module;
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.ontimize.jee.webclient.export.base.ExcelExportRestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Lazy
@Service("ProjectService")
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    @Autowired
    private UsersProjectService usersProjectService;

    @Autowired
    private TaskService taskService;


    @Override
    public EntityResult projectQuery(Map<String, Object> keyMap, List<?> attrList) {
        return this.daoHelper.query(this.projectDao, keyMap, attrList);
    }
    public EntityResult exportProjectQuery (Map<String, Object> keyMap, List<?> attrList){
    EntityResult a=this.daoHelper.query(this.projectDao, keyMap, attrList);
        int b = a.getColumnSQLType("Estado");
         a.deleteRecord(b);
        return a;
    }


    @Override
    public EntityResult projectInsert(Map<String, Object> attrMap) {
        EntityResult res = this.daoHelper.insert(this.projectDao, attrMap);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newAttrMap = new HashMap<>();
        newAttrMap.put(ProjectDao.P_ID, res.get(ProjectDao.P_ID));
        newAttrMap.put(UsersProjectDao.USER_, authentication.getName());
        usersProjectService.usersProjectInsert(newAttrMap);

        return res;
    }

    @Override
    public EntityResult projectUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap) {
        return this.daoHelper.update(this.projectDao, attrMap, keyMap);
    }

    @Override
    public EntityResult projectDelete(Map<String, Object> keyMap) {
        EntityResult err;
        try{
            err = this.daoHelper.delete(this.projectDao, keyMap);

        }catch (DataIntegrityViolationException e){
            err = new EntityResultMapImpl();
            err.setCode(EntityResult.OPERATION_WRONG);
            err.setMessage("DELETE_PROJECT_ERROR");
        }
        return err;
    }

    @Override
    public EntityResult projectTotalTimeQuery(Map<String, Object> keyMap, List<String> attrList) {
        List<Long> minuteTimes = new ArrayList<>();
        List<Long> minuteDecimalTimes = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> newKeyMap = new HashMap<>(keyMap);

        BasicField userField = new BasicField(UsersProjectDao.USER_);
        BasicExpression userExp = new BasicExpression(userField, BasicOperator.EQUAL_OP, authentication.getName());

        if (keyMap.containsKey(ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY)) {
            newKeyMap.put(ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY, new BasicExpression(keyMap.get(ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY), BasicOperator.AND_OP, userExp));
        } else {
            newKeyMap.put(ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY, userExp);
        }

        EntityResult res= this.daoHelper.query(this.projectDao, newKeyMap, attrList,"projectTotalTime");

        if (res.containsKey(ProjectDao.PROJECT_TOTAL_TIME) || res.containsKey(ProjectDao.PROJECT_TOTAL_TIME_DECIMAL)) {
            for (int i = 0; i < res.calculateRecordNumber(); i++) {
                Map<String, Object> recValues = res.getRecordValues(i);
                if (recValues.containsKey(ProjectDao.PROJECT_TOTAL_TIME)) {
                    PGInterval value = (PGInterval) recValues.get(ProjectDao.PROJECT_TOTAL_TIME);
                    minuteTimes.add(TaskService.intervalToMinutes(value));
                } else {
                    minuteTimes.add(0L);
                }
                if (recValues.containsKey(ProjectDao.PROJECT_TOTAL_TIME_DECIMAL)) {
                    PGInterval value = (PGInterval) recValues.get(ProjectDao.PROJECT_TOTAL_TIME_DECIMAL);
                    minuteDecimalTimes.add(TaskService.intervalToMinutes(value));
                } else {
                    minuteDecimalTimes.add(0L);
                }
            }
            res.put(ProjectDao.PROJECT_TOTAL_TIME, minuteTimes);
            res.put(ProjectDao.PROJECT_TOTAL_TIME_DECIMAL, minuteDecimalTimes);
        }
        return res;
    }

    @Override
    public EntityResult projectTotalTimeDelete(Map<String, Object> keyMap) {
        EntityResult err;
        try{
            err = this.daoHelper.delete(this.projectDao, keyMap);

        }catch (DataIntegrityViolationException e){
            err = new EntityResultMapImpl();
            err.setCode(EntityResult.OPERATION_WRONG);
            err.setMessage("DELETE_PROJECT_ERROR");
        }
        return err;
    }

    @Override
    public EntityResult projectTotalTimeUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap) {
        if(attrMap.containsKey(ProjectDao.P_FINISHED) && ((boolean) attrMap.get(ProjectDao.P_FINISHED))){
            Map<String, Object> kTaskQueryMap = new HashMap<>();
            List<String> aTaskQueryList = new ArrayList<>();

            kTaskQueryMap.put(TaskDao.P_ID, keyMap.get(ProjectDao.P_ID));
            aTaskQueryList.add(TaskDao.T_ID);
            aTaskQueryList.add(TaskDao.T_FINISHED);
            EntityResult queryRes = taskService.taskQuery(kTaskQueryMap, aTaskQueryList);

            ArrayList<Integer> tId = (ArrayList<Integer>) queryRes.get(TaskDao.T_ID);

            for (int i = 0; i < tId.size(); i++) {
                Map<String, Object> updateKeyMap = new HashMap<>();
                Map<String, Object> updateAttrMap = new HashMap<>();

                updateKeyMap.put(TaskDao.T_ID, tId.get(i));
                updateAttrMap.put(TaskDao.T_FINISHED, attrMap.get(ProjectDao.P_FINISHED));
                taskService.taskUpdate(updateAttrMap, updateKeyMap);
            }

        }
        return this.daoHelper.update(this.projectDao, attrMap, keyMap);
    }
}
