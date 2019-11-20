package com.designdreams.copass.dao;

import com.designdreams.copass.bean.User;

public interface UserDAO {

     boolean createUser(String uuid, User user);

     String getUser(String uuid, String emailId);

     boolean removeUser(String uuid, String emailId);
}
