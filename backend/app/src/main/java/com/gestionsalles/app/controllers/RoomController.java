package com.example.demo.controllers;


import com.example.demo.models.Room;
import com.example.demo.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomServ;


    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms= roomServ.getAllRooms();
        if(rooms.isEmpty()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id){
        Optional<Room> room= roomServ.findRoomById(id);
        if(room.isPresent()){
            return ResponseEntity.ok(room.get());
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/admin")
    public ResponseEntity<List<Room>> findByAdminEmail(@RequestParam String email){
        List<Room> rooms= roomServ.findByAdminEmail(email);
        if(!rooms.isEmpty()){
            return ResponseEntity.ok(rooms);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<List<Room>> findByAdminId(@PathVariable Long id){
        List<Room> rooms= roomServ.findByAdminId(id);
        if(!rooms.isEmpty()){
            return ResponseEntity.ok(rooms);
        }
        return ResponseEntity.notFound().build();
    }



    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        Optional<Room> r= roomServ.addRoom(room);
        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.ok(roomServ.findByName(room.getName()).get());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room){
        Optional<Room> r= roomServ.updateRoomById(id,room);
        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id){
        Optional<Room> r= roomServ.deleteRoomById(id);
        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.notFound().build();
    }















}
