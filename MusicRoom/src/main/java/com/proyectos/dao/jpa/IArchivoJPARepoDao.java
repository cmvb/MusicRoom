package com.proyectos.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyectos.model.ArchivoTB;

@EnableTransactionManagement
public interface IArchivoJPARepoDao extends JpaRepository<ArchivoTB, Integer> {
}
