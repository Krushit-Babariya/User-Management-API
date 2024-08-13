package com.krushit.service;

import java.util.List;

import com.krushit.model.ActivateUser;
import com.krushit.model.LoginCredentials;
import com.krushit.model.RecoverPassword;
import com.krushit.model.UserAccount;

public interface IUserMgmtService {
    public String registerUser(UserAccount user) throws Exception;
    public String activateUserAccount(ActivateUser user);
    public String login(LoginCredentials credentials);
    public List<UserAccount> listUsers();
    public UserAccount showUserByUserId(Integer id);
    public UserAccount showUserByEmailAndName(String email, String name);
    public String updateUser(UserAccount user);
    public String deleteUserById(Integer id);
    public String changeUserStatus(Integer id, String status);
    public String recoverPassword(RecoverPassword recover) throws Exception;
}
