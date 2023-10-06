package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ITimerService;
import com.campusdual.model.core.dao.TaskDao;
import com.campusdual.model.core.dao.TimerDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Lazy
@Service("TimerService")
public class TimerService implements ITimerService {
    @Autowired
    private TimerDao timerDao;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;


    @Override
    public EntityResult timerQuery(Map<String, Object> keyMap, List<String> attrList) {
        return this.daoHelper.query(this.timerDao, keyMap, attrList);
    }

    @Override
    public EntityResult timerInsert(Map<String, Object> attrMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        attrMap.put(timerDao.USER_, authentication.getName());//NOMBRE ususario
        return this.daoHelper.insert(this.timerDao, attrMap);
    }

    @Override
    public EntityResult timerUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap) {
        return this.daoHelper.update(this.timerDao, attrMap, keyMap);
    }

    @Override
    public EntityResult timerDelete(Map<String, Object> keyMap) {
        return this.daoHelper.delete(this.timerDao, keyMap);
    }
}
