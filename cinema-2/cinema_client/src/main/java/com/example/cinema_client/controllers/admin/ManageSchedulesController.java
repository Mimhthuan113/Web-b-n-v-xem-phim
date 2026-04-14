package com.example.cinema_client.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.cinema_client.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.cinema_client.constants.Api;

@Controller
@RequestMapping("/admin/schedules")
public class ManageSchedulesController {
    @Autowired
    private RestTemplate restTemplate;
    private static final String API_SCHEDULES = Api.baseURL+"/api/admin/schedules";
//    Hoang add
    private static final String API_SCHEDULES1 = Api.baseURL+"/api/schedules";
    private static final String API_GET_BRANCHES = Api.baseURL+"/api/admin/branches";
    private static final String API_GET_MOVIES = Api.baseURL+"/api/movies/showing";
    private static final String API_GET_ROOMS = Api.baseURL+"/api/admin/rooms";

    @GetMapping
    public String displayManageSchedulePage(HttpSession session,Model model){
        //Gắn access token jwt vào header để gửi kèm request
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO)session.getAttribute("jwtResponse");
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+jwtResponseDTO.getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
	    ResponseEntity<ScheduleDTO[]> response = restTemplate.exchange(API_SCHEDULES,HttpMethod.GET,entity,ScheduleDTO[].class);
	    ScheduleDTO[] schedules = response.getBody();
	    model.addAttribute("schedules",schedules);
	    model.addAttribute("modelSchedule",new ScheduleDTO());
        return "admin/manage-schedule";
    }
//    Hoang add update schedule show kèm modelSchedule
    @GetMapping("/update")
    public String displayUpdateSchedulePage(@ModelAttribute("modelSchedule") ScheduleDTO schedule ,HttpSession session,Model model){
        //Gắn access token jwt vào header để gửi kèm request
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO)session.getAttribute("jwtResponse");
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+jwtResponseDTO.getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
	    ResponseEntity<BranchDTO[]> response = restTemplate.exchange(API_GET_BRANCHES,HttpMethod.GET,entity,BranchDTO[].class);
	    BranchDTO[] branches = response.getBody();
	    ResponseEntity<MovieDTO[]> responseMovie = restTemplate.exchange(API_GET_MOVIES,HttpMethod.GET,entity,MovieDTO[].class);
	    MovieDTO[] movies = responseMovie.getBody();
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(API_GET_ROOMS)
                .queryParam("branchId", "{branchId}")
                .encode()
                .toUriString();
        Map<String, Integer> params = new HashMap<>();
        params.put("branchId", schedule.getBranch().getId());
	    ResponseEntity<RoomDTO[]> responseRoom = restTemplate.exchange(urlTemplate,HttpMethod.POST,entity,RoomDTO[].class,params);
	    RoomDTO[] rooms = responseRoom.getBody();
	    model.addAttribute("branches",branches);
	    model.addAttribute("movies",movies);
	    model.addAttribute("rooms",rooms);
	   // model.addAttribute("schedule",new ScheduleDTO());
	    model.addAttribute("schedule",schedule);
        return "admin/update1-schedule";
    }
    @PostMapping("/update")
    public String updateSchedulePage(@ModelAttribute("schedule") ScheduleDTO schedule, HttpSession session, Model model) {
        // Gắn access token jwt vào header để gửi kèm request
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO)session.getAttribute("jwtResponse");
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtResponseDTO.getAccessToken());

        // Tạo đối tượng RequestScheduleDTO và map dữ liệu từ ScheduleDTO
        RequestScheduleDTO requestSchedule = new RequestScheduleDTO();
        requestSchedule.setStartDate(schedule.getStartDate());
        requestSchedule.setStartTime(schedule.getStartTime());
        requestSchedule.setPrice(schedule.getPrice());
        requestSchedule.setMovieId(schedule.getMovie().getId());
        requestSchedule.setRoomId(schedule.getRoom().getId());
        requestSchedule.setBranchId(schedule.getBranch().getId());

        HttpEntity<RequestScheduleDTO> entity = new HttpEntity<>(requestSchedule, headers);

        // Xây dựng URL cho API với id từ schedule
        String apiUrlWithId = API_SCHEDULES1 + "/" + schedule.getId();
        System.out.println("url-update-------------Hoang----------------" + apiUrlWithId);
        System.out.println("RequestSchedule Data: " + requestSchedule.toString());

        try {
            // Gửi yêu cầu PUT tới API với id trong URL
            ResponseEntity<String> response = restTemplate.exchange(apiUrlWithId, HttpMethod.PUT, entity, String.class);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Chuyển hướng sau khi cập nhật thành công
        return "redirect:/admin/schedules";
    }

    @GetMapping("/add")
    public String displayAddSchedulePage(HttpSession session,Model model){
        //Gắn access token jwt vào header để gửi kèm request
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO)session.getAttribute("jwtResponse");
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+jwtResponseDTO.getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
	    ResponseEntity<BranchDTO[]> response = restTemplate.exchange(API_GET_BRANCHES,HttpMethod.GET,entity,BranchDTO[].class);
	    BranchDTO[] branches = response.getBody();
	    ResponseEntity<MovieDTO[]> responseMovie = restTemplate.exchange(API_GET_MOVIES,HttpMethod.GET,entity,MovieDTO[].class);
	    MovieDTO[] movies = responseMovie.getBody();
	    model.addAttribute("branches",branches);
	    model.addAttribute("rooms",null);
	    model.addAttribute("movies",movies);
	    model.addAttribute("schedule",new ScheduleDTO());
        return "admin/update-schedule";
    }
    @PostMapping("/add")
    public String addSchedulePage(@ModelAttribute("schedule") ScheduleDTO schedule ,HttpSession session,Model model){
        //Gắn access token jwt vào header để gửi kèm request
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO)session.getAttribute("jwtResponse");
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+jwtResponseDTO.getAccessToken());
        HttpEntity<ScheduleDTO> entity = new HttpEntity<>(schedule,headers);
        try {
        	ResponseEntity<String> response = restTemplate.exchange(API_SCHEDULES,HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			System.out.println(e);
		}
	    
        return "redirect:/admin/schedules";
    }
}
