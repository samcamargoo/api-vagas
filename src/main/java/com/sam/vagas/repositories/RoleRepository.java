package com.sam.vagas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sam.vagas.entities.Roles;
import com.sam.vagas.enums.RolesEnum;

public interface RoleRepository extends JpaRepository<Roles, UUID> {

	@Query("SELECT r from Roles r WHERE r.rolesEnum = :role")
	//@Query("SELECT r from Roles r WHERE r.rolesEnum = com.sam.estoque.enums.RolesEnum.ROLE_USER")
	Roles findRole(@Param("role") RolesEnum roles);
}
