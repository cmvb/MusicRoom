package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.IntegranteTB;

@EnableTransactionManagement
public interface IIntegranteJPARepoDao extends JpaRepository<IntegranteTB, Integer> {
}
