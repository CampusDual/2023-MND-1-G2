package com.campusdual.model.core.service;

import com.campusdual.api.core.service.ISignUpService;
import com.campusdual.model.core.dao.UserDao;
import com.campusdual.model.core.dao.UserRoleDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Lazy
@Service("SignUpService")
public class SignUpService implements ISignUpService {

    @Autowired
    private UserDao userDao;
    @Autowired UserRoleDao userRoleDao;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;
    @Override
    public EntityResult signUpInsert(Map<String, Object> attrMap) throws OntimizeJEERuntimeException {
        try{
            String password = (String) attrMap.get(UserDao.PASSWORD);
            String confirmPassword = (String) attrMap.get("CONFIRM_PASS");

            if (!password.equals(confirmPassword)){
                return createErrorResult("ERROR_NOT_MATCHING_PASSWORDS");
            }

            if (!isPasswordCorrect(password)) {
                return createErrorResult("ERROR_WEAK_PASSWORD");
            }

            EntityResult insertQuery = this.daoHelper.insert(userDao, attrMap);

            String user = (String) attrMap.get(UserDao.ID);
            Map<String, Object> userRoleKV = new HashMap<>();
            userRoleKV.put(UserDao.ID, user);
            userRoleKV.put(UserRoleDao.ID,1);


            return  insertQuery;
        } catch (org.springframework.dao.DuplicateKeyException exception){
            return createErrorResult("ERROR_DUPLICATE_USER_NAME");
        }
    }

    private EntityResult createErrorResult(String errorMessage) {
        EntityResult errorEr = new EntityResultMapImpl();
        errorEr.setCode(EntityResult.OPERATION_WRONG);
        errorEr.setMessage(errorMessage);
        return errorEr;
    }

    private boolean isPasswordCorrect(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".[a-z]+.")) {
            return false;
        }
        if (!password.matches(".[A-Z]+.")) {
            return false;
        }
        if (!password.matches(".\\d+.")) {
            return false;
        }
        if (!password.matches(".[!@#$%^&()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+.*")) {
            return false;
        }
        return true;
    }
}