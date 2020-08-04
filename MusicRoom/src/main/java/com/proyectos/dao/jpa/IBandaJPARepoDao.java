package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.BandaTB;

@EnableTransactionManagement
public interface IBandaJPARepoDao extends JpaRepository<BandaTB, Integer> {
}
