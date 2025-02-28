package dev.pavila.service;

import org.springframework.stereotype.Service;

import dev.pavila.dto.HouseRentDto;
import dev.pavila.model.HouseRent;
import dev.pavila.repository.HouseRentRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
//import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class HouseRentService {

    private final HouseRentRepo repo;

    public void createHouseRent(HouseRentDto dto) {
        HouseRent house = new HouseRent();
        house.setName(dto.getName());
        house.setAddress(dto.getAddress());
        house.setRent(dto.getRent());
        house.setNoOfRoom(dto.getNoOfRoom());

        repo.save(house);
    }

    // get all
    public List<HouseRentDto> getAllHouseRent() {
        return repo.findAll().stream().map(house -> {
            HouseRentDto dto = new HouseRentDto();
            dto.setId(house.getId());
            dto.setName(house.getName());
            dto.setAddress(house.getAddress());
            dto.setRent(house.getRent());
            dto.setNoOfRoom(house.getNoOfRoom());
            return dto;
        }).collect(Collectors.toList());
    }

    // get by id
    public Optional<HouseRent> getHouseById(Long id) {
        return repo.findById((long) id.intValue());
    }

    // update
    public String updateHouseRent(Long id, HouseRentDto dto) {
        Optional<HouseRent> existingHouse = repo.findById(id); // ✅ FIXED: Just use id
        if (existingHouse.isPresent()) {
            HouseRent house = existingHouse.get();
            house.setName(dto.getName());
            house.setAddress(dto.getAddress());
            house.setRent(dto.getRent());
            house.setNoOfRoom(dto.getNoOfRoom());

            repo.save(house);
            return "House updated successfully!";
        } else {
            return "House with ID " + id + " not found!";
        }
    }

    public String deleteHouseRent(Long id) {
        if (repo.existsById(id)) { // ✅ FIXED: Just use id
            repo.deleteById(id);
            return "House deleted successfully!";
        } else {
            return "House with ID " + id + " not found!";
        }
    }

}
