package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.UsuarioTB;

@EnableTransactionManagement
public interface IUsuarioJPARepoDao extends JpaRepository<UsuarioTB, Integer> {
}
