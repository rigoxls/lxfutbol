package com.lxfutbol.user.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.user.dto.RegistryUserDto;
import com.lxfutbol.user.repository.RolEntity;
import com.lxfutbol.user.repository.RolRepository;
import com.lxfutbol.user.repository.UserEntity;
import com.lxfutbol.user.repository.UserRepository;
import com.sun.el.stream.Optional;

@Service
public class UserService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private RolRepository rol;
	@Autowired
	private UserRepository user;

	public UserEntity login(String email, String passWord) {

		UserEntity usuarioRes = new UserEntity();
		try {

			Query q = entityManager
					.createNativeQuery("SELECT * FROM user where email = :emailP and password = :pass");
			q.setParameter("emailP", email);
			q.setParameter("pass", passWord);

			Object[] user = (Object[]) q.getSingleResult();
			
			
			usuarioRes.setId(Integer.valueOf(user[2].toString()));
			usuarioRes.setLastName(user[3].toString());
			usuarioRes.setEmail(email);
			usuarioRes.setIdUser(user[1].toString());
			usuarioRes.setName(user[4].toString());
			usuarioRes.setPassword(passWord);
			usuarioRes.setPhone(user[6].toString());
			usuarioRes.setRol(rol.findById(Integer.valueOf(user[7].toString())).get());
			
		} catch (NoResultException ex) {
			throw ex;
		}

		return usuarioRes;
	}
	
	public void registry(RegistryUserDto dtoUser) throws Exception {

		Boolean userTrue = user.existsById(dtoUser.getIdUser());

		if (!userTrue) {
			UserEntity newUser = new UserEntity();
			RolEntity newRol = new RolEntity();
			newUser.setIdUser(dtoUser.getIdUser());
			newUser.setName(dtoUser.getName());
			newUser.setLastName(dtoUser.getLastName());
			newUser.setPhone(dtoUser.getPhone());
			newUser.setEmail(dtoUser.getEmail());
			newUser.setPassword(dtoUser.getPassword());

			Query q = entityManager.createNativeQuery("SELECT * FROM rol where value = :rolType");
			q.setParameter("rolType", "C");
			Object[] rolId = (Object[]) q.getSingleResult();
			newRol.setId(Integer.valueOf(rolId[0].toString()));
			newRol.setName(rolId[1].toString());
			newRol.setDescription(rolId[2].toString());
			newRol.setValue((rolId[3].toString().charAt(0)));
			newUser.setRol(newRol);
			user.save(newUser);
		} else {
			throw new Exception("El usuario ya existe.");
		}

	}
}
