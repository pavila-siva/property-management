package dev.pavila.dto;

import lombok.Data;

@Data
public class HouseRentDto {
    private Long id;
    private String name;
    private String address;
    private int rent;
    private int noOfRoom;
}
