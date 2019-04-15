package com.proyectos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectos.model.UsuarioTB;

public interface IUsuarioDao extends JpaRepository<UsuarioTB, Long> {

}
