package com.krushit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.krushit.config.AppConfigProperties;
import com.krushit.constants.UserConstants;
import com.krushit.entity.UserMaster;
import com.krushit.model.ActivateUser;
import com.krushit.model.LoginCredentials;
import com.krushit.model.RecoverPassword;
import com.krushit.model.UserAccount;
import com.krushit.repository.IUsersRepository;

public class UserMgmtServiceImpl implements IUserMgmtService {

	@Autowired
	private IUsersRepository userRepo;
	@Autowired
	private Map<String, String> map;

	public UserMgmtServiceImpl(AppConfigProperties config) {
		map = config.getMessages();
	}

	@Override
	public String registerUser(UserAccount user) {
		// convert UserAccount to UserMaster data
		UserMaster master = new UserMaster();
		BeanUtils.copyProperties(user, master);

		// set temp password
		master.setPassword(getAlphaNumericString(8));
		master.setActive_sw("inactive");

		UserMaster savedMaster = userRepo.save(master);
		return savedMaster != null ? map.get(UserConstants.SAVE_SUCCESS) + savedMaster.getUserid()
				: map.get(UserConstants.SAVE_FALIURE);
	}

	@Override
	public String activateUserAccount(ActivateUser user) {
		//convert ActiveteUser to UserMaster
		UserMaster master = new UserMaster();
		master.setEmail(user.getEmail());
		master.setPassword(user.getPassword());
		
		//check the records available by using email and temp pass
		Example<UserMaster> example = Example.of(master);
		
		List<UserMaster> list = userRepo.findAll(example);
		
		if(!list.isEmpty()) {
			//get entity object
			UserMaster entity = list.get(0);
			//set new password
			entity.setPassword(user.getPassword());
			//change status to active
			entity.setActive_sw("active");
			//update object
			UserMaster updatedMater = userRepo.save(entity);
			return map.get(UserConstants.USER_AVTIVATION_SUCCESS) + updatedMater.getUserid();
		}
		return map.get(UserConstants.USER_AVTIVATION_FAIL);
	}

	@Override
	public String login(LoginCredentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAccount> listUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccount showUserByUserId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccount showUserByEmailAndName(String email, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUser(UserAccount user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUserById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changeUserStatus(Integer id, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String recoverPassword(RecoverPassword recover) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getAlphaNumericString(int n) {

		// choose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz@#)(*&^%$!";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

}
