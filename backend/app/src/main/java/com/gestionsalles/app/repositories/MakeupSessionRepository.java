package com.gestionsalles.app.repositories;

import com.gestionsalles.app.models.MakeupSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeupSessionRepository extends JpaRepository<MakeupSession, Long> {
}
