package com.campusdual.model.core.service;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.campusdual.model.core.dao.UserSubDao;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.security.PermissionsProviderSecured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.campusdual.api.core.service.IUserService;
import com.campusdual.model.core.dao.UserDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;


@Lazy
@Service("UserService")
public class UserService implements IUserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private DefaultOntimizeDaoHelper daoHelper;

	public void loginQuery(Map<?, ?> key, List<?> attr) {
	}

	//Sample to permission
	//@Secured({ PermissionsProviderSecured.SECURED })
	@Secured({PermissionsProviderSecured.SECURED})
	public EntityResult userQuery(Map<?, ?> keyMap, List<?> attrList) {
		return this.daoHelper.query(userDao, keyMap, attrList);
	}

	@Secured({PermissionsProviderSecured.SECURED})
	public EntityResult userInsert(Map<?, ?> attrMap) {
		return this.daoHelper.insert(userDao, attrMap);
	}

	@Secured({PermissionsProviderSecured.SECURED})
	public EntityResult userUpdate(Map<?, ?> attrMap, Map<?, ?> keyMap) {
		return this.daoHelper.update(userDao, attrMap, keyMap);
	}

	@Secured({PermissionsProviderSecured.SECURED})
	public EntityResult userDelete(Map<?, ?> keyMap) {
		return this.daoHelper.delete(this.userDao, keyMap);
	}

	@Override
	public EntityResult userToShareQuery(Map<?, ?> keyMap, List<?> attrList) {
		Map<String, Object> userToShareKV = new HashMap<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		SQLStatementBuilder.BasicField field = new SQLStatementBuilder.BasicField(UserDao.NAME);
		SQLStatementBuilder.BasicExpression bexp = new SQLStatementBuilder.BasicExpression(field, SQLStatementBuilder.BasicOperator.NOT_EQUAL_OP, username);
		SQLStatementBuilder.BasicField fieldAdmin =
				new SQLStatementBuilder.BasicField("ROLENAME");
		SQLStatementBuilder.BasicExpression adminBE =
				new SQLStatementBuilder.BasicExpression(fieldAdmin, SQLStatementBuilder.BasicOperator.NOT_EQUAL_OP, "admin");
		SQLStatementBuilder.BasicExpression filterBE =
				new SQLStatementBuilder.BasicExpression(adminBE, SQLStatementBuilder.BasicOperator.AND_OP, bexp);
		userToShareKV.put(SQLStatementBuilder.ExtendedSQLConditionValuesProcessor.EXPRESSION_KEY, filterBE);
		return this.daoHelper.query(this.userDao, userToShareKV, attrList, UserDao.USERS_TO_SHARE_QUERY);
	}

	@Secured({PermissionsProviderSecured.SECURED})
	public EntityResult deleteCurrentUserAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Map<String, Object> keyMap = new HashMap<>();
		keyMap.put("username_column_name", username); //
		return this.daoHelper.delete(this.userDao, keyMap);
	}

	public EntityResult registerInsert(Map<?, ?> attrMap) {
		EntityResult err;
		try {
			err = this.userInsert(attrMap);

		} catch (DataIntegrityViolationException e) {
			err = new EntityResultMapImpl();
			err.setCode(EntityResult.OPERATION_WRONG);
			err.setMessage("REGISTER_ERROR_CREATE");
		}
		return err;
	}
}
