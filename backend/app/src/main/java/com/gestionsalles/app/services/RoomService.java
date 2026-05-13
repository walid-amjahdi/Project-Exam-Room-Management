package com.example.demo.services;

import com.example.demo.models.Reservation;
import com.example.demo.models.Room;
import com.example.demo.repos.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepo;

    public Optional<Room> findByName(String name) {
        return roomRepo.findByNameEquals(name);
    }


    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    public Optional<Room> findRoomById(Long id) {
        Optional<Room> room= roomRepo.findById(id);
        if(room.isPresent()){
            return room;
        }
        return Optional.empty();
    }

    public Optional<Room> addRoom(Room room) {
        Optional<Room> r= findByName(room.getName());
        if(!r.isPresent()){
            roomRepo.save(room);
            return findByName(room.getName());
        }
        return Optional.empty();
    }

    public Optional<Room> updateRoomById(Long id, Room room) {
        Optional<Room> r= findRoomById(id);
        if(r.isPresent()){
            room.setId(id);
            roomRepo.save(room);
            return r;
        }
        return Optional.empty();
    }

    public Optional<Room> deleteRoomById(Long id) {
        Optional<Room> r= findRoomById(id);
        if(r.isPresent()){
            roomRepo.deleteById(id);
            return r;
        }
        return Optional.empty();
    }

    public List<Reservation> getActiveReservations(){
        return null;
    }
}
