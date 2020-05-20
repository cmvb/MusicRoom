package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.SalaTB;

@EnableTransactionManagement
public interface ISalaJPARepoDao extends JpaRepository<SalaTB, Integer> {
}
