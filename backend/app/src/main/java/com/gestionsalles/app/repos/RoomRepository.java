package com.gestionsalles.app.repos;

import com.gestionsalles.app.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    Optional<Room> findByNameEquals(String name);

    List<Room> findRoomByAdminId(Long id);
    List<Room> findRoomByAdminEmail(String email);

}
