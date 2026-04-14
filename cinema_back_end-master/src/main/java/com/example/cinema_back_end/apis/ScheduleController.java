package com.example.cinema_back_end.apis;

import com.example.cinema_back_end.dtos.request.ApiResponse;
import com.example.cinema_back_end.dtos.request.ScheduleCreateRequest;
import com.example.cinema_back_end.dtos.ScheduleDTO;
import com.example.cinema_back_end.dtos.response.IScheduleDTO;
import com.example.cinema_back_end.entities.Schedule;
import com.example.cinema_back_end.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/schedules",produces = "application/json")
@RequiredArgsConstructor
//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @GetMapping("")
    public ApiResponse<List<Object[]>> getSchedule() {
        return ApiResponse.<List<Object[]>>builder()
                .code(200)
                .result(scheduleService.getSchedules())
                .build();
    }
    @GetMapping("/test")
    public ApiResponse<List<Object[]>> test() {
        return ApiResponse.<List<Object[]>>builder()
                .code(200)
                .result(scheduleService.test())
                .build();
    }
//    @GetMapping("/open")
//    public ApiResponse<List<Object[]>> getSheduleOpen() {
//        return ApiResponse.<List<Object[]>>builder()
//                .code(200)
//                .message("Schedule all")
//                .result(scheduleService.getSchedulesOpen())
//                .build();
//    }
    @GetMapping("/schedulefilter")
//    List<Object[]>
    public ApiResponse<List<Object>> getScheduleFilter(@RequestParam ("search") String search ,@RequestParam("startDay")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate startDay, @RequestParam("endDay")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate endDay) {
        return ApiResponse.<List<Object>>builder()
                .code(200)
                .message("Schedule next week")
                .result(scheduleService.getSchedulesWithDay(search, startDay, endDay))
//                .result(search+startDay+endDay)
                .build();

    }

    @PostMapping("/add")
    public Schedule createSchedule(@RequestBody ScheduleCreateRequest request) {
            return scheduleService.createSchedule(request);
    }
    @PutMapping("/{id}")
    public ApiResponse<Schedule> updateSchedule(@PathVariable Integer id, @RequestBody IScheduleDTO scheduleDTO) {
        return ApiResponse.<Schedule>builder()
                .code(200)
                .message(" Up Schedule " + id + " ---- Success")
                .result(scheduleService.updateSchedule(id, scheduleDTO))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }


//    manager
//    @PostMapping("/create")
//    public ApiResponse<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
//        Schedule schedule = new Schedule();
//        schedule.setMovieId(request.getMovieId());
//        schedule.setBranchId(request.getBranchId());
//        schedule.setRoomId(request.getRoomId());
//        schedule.setStartDate(request.getStartDate());
//        schedule.setStartTime(request.getStartTime());
//        schedule.setPrice(request.getPrice());
//        Schedule createdSchedule = scheduleService.createSchedule(schedule);
//        return ApiResponse.<ScheduleResponse>builder()
//                .code(200)
//                .message("Schedule created successfully")
//                .result(toScheduleResponse(createdSchedule))
//                .build();
//    }
}