package com.damian.objetivos.service.impl;

import com.damian.objetivos.entity.User;
import com.damian.objetivos.entity.UserRole;
import com.damian.objetivos.model.UserRoleModel;
import com.damian.objetivos.repository.UserRoleRepository;
import com.damian.objetivos.service.UserRoleService;
import com.damian.objetivos.util.LoggerMapper;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service()
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserService userService;

    @Override
    public UserRoleModel findByUser(User user) {
        Set<UserRole> roles = findRolesByUser(user);
        UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setUsername(user.getUsername());
        List<String> rolesString = new ArrayList<>();
        for (UserRole userRole : roles) {
            rolesString.add(userRole.getRole());
        }
        userRoleModel.setRoles(rolesString);
        return userRoleModel;
    }

    @Override
    public Set<UserRole> findRolesByUser(User user) {
        return userRoleRepository.findByUser(user);
    }

    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public void delete(int userRoleId) {
        userRoleRepository.deleteByUserRoleId(userRoleId);
    }

    @Override
    public void actualizarRoles(UserRoleModel userRoleModel) {
        int elimino = 0;
        int mantengo = 0;
        int inserto = 0;
        User user = userService.findByUsername(userRoleModel.getUsername());
        Set<UserRole> rolesViejos = findRolesByUser(user);
        List<String> rolesNuevos = userRoleModel.getRoles();
        for (UserRole rolViejo : rolesViejos) {
            if (!rolesNuevos.contains(rolViejo.getRole())) {
                userRoleRepository.delete(rolViejo);
                elimino++;
            } else {
                rolesNuevos.remove(rolViejo.getRole());
                mantengo++;
            }
        }
        UserRole userRole;
        for (String rolNuevo : rolesNuevos) {
            userRole = new UserRole();
            userRole.setRole(rolNuevo);
            userRole.setUser(user);
            userRoleRepository.save(userRole);
            inserto++;
        }
        LoggerMapper.log(Level.INFO, "actualizarRoles", "Inserto: " + inserto + ", mantengo: " + mantengo + ", elimino: " + elimino, getClass());
    }
}
