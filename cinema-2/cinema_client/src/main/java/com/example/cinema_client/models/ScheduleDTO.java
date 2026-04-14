package com.example.cinema_client.models;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleDTO {
    private int id;
    private LocalDate startDate;
    private LocalTime startTime;
    private Double price;
    private RoomDTO room;
    private BranchDTO branch;
    private MovieDTO movie;
}
