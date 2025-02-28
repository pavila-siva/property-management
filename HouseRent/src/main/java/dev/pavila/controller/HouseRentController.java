package dev.pavila.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.pavila.dto.HouseRentDto;
import dev.pavila.model.HouseRent;
import dev.pavila.service.HouseRentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/houses") // ✅ Ensures correct mapping
// @RequestMapping("/test")
@Slf4j
public class HouseRentController {

    private final HouseRentService service;

    @PostMapping("/create")
    public ResponseEntity<String> createHouse(@RequestBody HouseRentDto dto) {
        log.info("Received request to create house: {}", dto);
        service.createHouseRent(dto);
        return new ResponseEntity<>("House Created!", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HouseRentDto>> getAllHouses() {
        log.info("Fetching all houses...");
        return ResponseEntity.ok(service.getAllHouseRent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHouseById(@PathVariable Long id) {
        log.info("Fetching house with ID: {}", id);

        Optional<HouseRent> house = service.getHouseById(id);

        if (house.isPresent()) {
            return ResponseEntity.ok(house.get()); // ✅ Returns JSON when found
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("House with ID " + id + " not found!"); // ✅ Returns String when not found
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editHouse(@PathVariable Long id, @RequestBody HouseRentDto dto) {
        log.info("Updating house with ID: {}", id);
        String response = service.updateHouseRent(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHouse(@PathVariable Long id) {
        log.info("Deleting house with ID: {}", id);
        String response = service.deleteHouseRent(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello")
    public String a() {
        return "Hello";
    }
}
