package com.gestionsalles.app.repos;

import com.gestionsalles.app.models.Role;
import com.gestionsalles.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    @Query("select u.id,u.name, u.role, u.email from User u WHERE u.id= :id order by u.id")
    List<Object[]> NameAndRoleAndEmail(@Param("id") Long id);

    @Query("select u.id, u.email,u.name,u.role from User u where u.id = :id order by u.id")
    List<Object[]> findEmailAndNameAndRoleById(@Param("id") Long id);

    List<User> findUserByRole(Role role);
    Optional<User> findFirstByRole(Role role);
}
