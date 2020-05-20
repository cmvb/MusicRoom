package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.TerceroTB;

@EnableTransactionManagement
public interface ITerceroJPARepoDao extends JpaRepository<TerceroTB, Integer> {
}
