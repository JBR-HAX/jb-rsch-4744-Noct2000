package org.jetbrains.assignment.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.assignment.mapper.LocationMapper;
import org.jetbrains.assignment.model.dto.DirectionDto;
import org.jetbrains.assignment.model.dto.LocationDto;
import org.jetbrains.assignment.service.LocationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @PostMapping("/locations")
    List<LocationDto> move(@RequestBody List<DirectionDto> directionDtos) {
        return locationService.makeSteps(directionDtos).stream()
                .map(locationMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/moves")
    List<DirectionDto> inverseMove(@RequestBody List<LocationDto> locationDtos) {
        return locationService.inverseMove(locationDtos);
    }
}
