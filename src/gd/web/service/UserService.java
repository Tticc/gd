package gd.web.service;

import gd.web.entity.UserEntity;


public interface UserService {
	
	UserEntity getUserByName(String userName);
	
	UserEntity getUserById(int id);
	
	void addUser(UserEntity userEntity);
	
	void deleteUser(UserEntity userEntity);
	
	void updateUser(UserEntity userEntity);

	UserEntity login(UserEntity userEntity);
	
	void initStreamTable(int staId);

	void deleteUserByStaId(int id);

	UserEntity getUserByStaId(int id);
}
