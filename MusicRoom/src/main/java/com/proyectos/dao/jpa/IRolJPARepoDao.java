package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.RolTB;

@EnableTransactionManagement
public interface IRolJPARepoDao extends JpaRepository<RolTB, Integer> {
}
