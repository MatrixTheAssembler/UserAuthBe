package de.frauas.userauth.repository;

import de.frauas.userauth.entity.Role;
import de.frauas.userauth.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByName(String name);
}
