package dev.pavila.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "house_rent") // Ensures the correct table name
public class HouseRent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Explicit ID generation strategy
    @JsonProperty("id")
    // private Integer id;
    private Long id; // ✅ Change Integer to Long

    private String name;
    private String address;
    private int rent;
    private int noOfRoom;

}
