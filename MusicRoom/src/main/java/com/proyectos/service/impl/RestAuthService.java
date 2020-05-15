package com.proyectos.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proyectos.dao.IRolDao;
import com.proyectos.util.ConstantesRoles;

@Service
public class RestAuthService {

	@Autowired
	private IRolDao rolDAO;

	public boolean hasAccess(String path, String subPath) {
		boolean acceso = false;

		String rolValido = rolDAO.obtenerRolPorPathSubPath(path, subPath);

		if (!StringUtils.isBlank(rolValido)) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				for (GrantedAuthority auth : authentication.getAuthorities()) {
					String rolUser = auth.getAuthority();
					if (ConstantesRoles.ROL_ADMINISTRADOR.equalsIgnoreCase(rolUser)
							|| rolValido.equalsIgnoreCase(rolUser)) {
						acceso = true;
						break;
					}
				}
			}
		}

		return acceso;
	}

}
