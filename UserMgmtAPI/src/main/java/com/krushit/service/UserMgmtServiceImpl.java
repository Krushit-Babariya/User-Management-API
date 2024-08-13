package com.krushit.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;

import com.krushit.config.AppConfigProperties;
import com.krushit.constants.UserConstants;
import com.krushit.entity.UserMaster;
import com.krushit.model.ActivateUser;
import com.krushit.model.LoginCredentials;
import com.krushit.model.RecoverPassword;
import com.krushit.model.UserAccount;
import com.krushit.repository.IUsersRepository;
import com.krushit.utils.EmailUtils;

public class UserMgmtServiceImpl implements IUserMgmtService {

	@Autowired
	private IUsersRepository userRepo;
	@Autowired
	private Map<String, String> map;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private Environment env;

	public UserMgmtServiceImpl(AppConfigProperties config) {
		map = config.getMessages();
	}

	@Override
	public String registerUser(UserAccount user) throws Exception {
		// convert UserAccount to UserMaster data
		UserMaster master = new UserMaster();
		BeanUtils.copyProperties(user, master);
		
		String tempPwd = generatePassword();
		master.setPassword(tempPwd);
		master.setActive_sw("inactive");
		
		String subject= "User Registration Success";
		String body = readEmailMessageBody(env.getProperty("mailBody.registerUser.location"), user.getName(), tempPwd); 
		
		emailUtils.sendEmailMessage(user.getEmail(), subject, body);

		UserMaster savedMaster = userRepo.save(master);
		return savedMaster != null ? map.get(UserConstants.SAVE_SUCCESS) + savedMaster.getUserid()
				: map.get(UserConstants.SAVE_FALIURE);
	}

	/*	@Override
		public String activateUserAccount(ActivateUser user) {
			// convert ActiveteUser to UserMaster
			UserMaster master = new UserMaster();
			master.setEmail(user.getEmail());
			master.setPassword(user.getPassword());
	
			// check the records available by using email and temp pass
			Example<UserMaster> example = Example.of(master);
	
			List<UserMaster> list = userRepo.findAll(example);
	
			if (!list.isEmpty()) {
				// get entity object
				UserMaster entity = list.get(0);
				// set new password
				entity.setPassword(user.getPassword());
				// change status to active
				entity.setActive_sw("active");
				// update object
				UserMaster updatedMater = userRepo.save(entity);
				return map.get(UserConstants.USER_AVTIVATION_SUCCESS) + updatedMater.getUserid();
			}
			return map.get(UserConstants.USER_AVTIVATION_FAIL);
		}
	*/

	@Override
	public String activateUserAccount(ActivateUser user) {
		UserMaster entity = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());

		if (entity == null) {
			return map.get(UserConstants.USER_AVTIVATION_FAIL);
		} else {
			entity.setPassword(user.getPassword());
			// change status to active
			entity.setActive_sw("active");
			// update object
			UserMaster updatedMater = userRepo.save(entity);
			return map.get(UserConstants.USER_AVTIVATION_SUCCESS) + updatedMater.getUserid();
		}
	}

	@Override
	public String login(LoginCredentials credentials) {
		UserMaster master = new UserMaster();
		BeanUtils.copyProperties(credentials, master);

		Example<UserMaster> example = Example.of(master);
		List<UserMaster> credential = userRepo.findAll(example);

		if (!credential.isEmpty()) {
			return map.get(UserConstants.INVALID_CREDENTIALS);
		} else {
			if (credential.get(0).getActive_sw().equalsIgnoreCase("active")) {
				return map.get(UserConstants.VALID_CREDENTIALS) + credential.get(0).getName();
			}
		}
		return map.get(UserConstants.ACCOUNT_NOT_ACTIVE);
	}

	@Override
	public List<UserAccount> listUsers() {
		List<UserMaster> list = userRepo.findAll();
		List<UserAccount> listAcc = new ArrayList<UserAccount>();

		//convert UserMaster to UserAccounts object
		list.forEach(account -> {
			UserAccount user = new UserAccount();
			BeanUtils.copyProperties(list, listAcc);
			listAcc.add(user);
		});

		return listAcc;
	}

	@Override
	public UserAccount showUserByUserId(Integer id) {
		Optional<UserMaster> opt = userRepo.findById(id);
		UserAccount account = new UserAccount();
		if (opt.isPresent()) {
			account = new UserAccount();
			BeanUtils.copyProperties(opt.get(), account);
			return account;
		}
		return account;
	}

	@Override
	public UserAccount showUserByEmailAndName(String email, String name) {
		UserMaster master = userRepo.findByNameAndEmail(name, email);
		UserAccount account = new UserAccount();
		if (master != null) {
			account = new UserAccount();
			BeanUtils.copyProperties(master, account);
		}
		return account;
	}

	@Override
	public String updateUser(UserAccount user) {
		//get user object
		Optional<UserMaster> opt = userRepo.findById(user.getUserid());

		if (opt.isPresent()) {
			UserMaster master = new UserMaster();
			BeanUtils.copyProperties(user, master);
			userRepo.save(master);
			return map.get(UserConstants.UPDATE_SUCCESS) + master.getUserid();
		}

		return map.get(UserConstants.UPDATE_SUCCESS);
	}

	@Override
	public String deleteUserById(Integer id) {
		Optional<UserMaster> master = userRepo.findById(id);

		if (master.isPresent()) {
			userRepo.deleteById(id);
			return map.get(UserConstants.DELETE_SUCCESS) + id;
		}
		return map.get(UserConstants.DELETE_FAILURE);
	}

	@Override
	public String changeUserStatus(Integer id, String status) {
		Optional<UserMaster> opt = userRepo.findById(id);

		if (opt.isPresent()) {
			UserMaster master = opt.get();
			master.setActive_sw(status);
			return map.get(UserConstants.STATUS_UPDATION_SUCCESS) + id;
		}
		return map.get(UserConstants.STATUS_UPDATION_FAIL);
	}

	@Override
	public String recoverPassword(RecoverPassword recover) throws Exception {
		UserMaster master = userRepo.findByNameAndEmail(recover.getEmail(), recover.getUname());
		

		if (master != null) {
			String pwd = master.getPassword();
			String subject= "Password Recovery Mail";
			String body = readEmailMessageBody(env.getProperty("mailBody.recoverPass.location"), recover.getUname(), pwd); 
			
			emailUtils.sendEmailMessage(recover.getEmail(), subject, body);
			return pwd;
		}
		return map.get(UserConstants.INVALID_CREDENTIALS);
	}

	private static String generatePassword() {
		int n = 8;
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

	private String readEmailMessageBody(String fileName, String fullName, String pwd) throws Exception {
		String mailBody = null;
		String url = " ";
		try (FileReader reader = new FileReader(fileName); BufferedReader br = new BufferedReader(reader)) {
			// Read file content to StringBuffer object line by line
			StringBuffer buffer = new StringBuffer();
			String line = null;
			do {
				line = br.readLine();
				buffer.append(line);
			} while (line != null);
			mailBody = buffer.toString();
			mailBody.replace("{FULL-NAME}", fullName);
			mailBody.replace("{PWD}", pwd);
			mailBody.replace("{URL}", url);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mailBody;
	}

}
