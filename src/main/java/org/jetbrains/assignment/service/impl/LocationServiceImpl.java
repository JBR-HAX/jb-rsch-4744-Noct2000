package org.jetbrains.assignment.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.assignment.model.Location;
import org.jetbrains.assignment.model.dto.Direction;
import org.jetbrains.assignment.model.dto.DirectionDto;
import org.jetbrains.assignment.model.dto.LocationDto;
import org.jetbrains.assignment.repository.LocationRepository;
import org.jetbrains.assignment.service.LocationService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLastLocation() {
        return locationRepository.findLastLocation()
                .orElseThrow(() -> new EntityNotFoundException("No entities yet"));
    }

    @Override
    public List<Location> makeSteps(List<DirectionDto> directionDtos) {
        Location lastLocation = getLastLocation();
        for (DirectionDto directionRequest: directionDtos) {
            lastLocation = move(lastLocation, directionRequest);
        }
        return getAll();
    }

    @Override
    public List<DirectionDto> inverseMove(List<LocationDto> locationDtos) {
        List<DirectionDto> result = new ArrayList<>();
        LocationDto lastLocation = locationDtos.get(0);
        for (int i = 1; i < locationDtos.size(); i++) {
            lastLocation = inverse(lastLocation, locationDtos.get(i), result);
        }
        return result;
    }

    private LocationDto inverse(LocationDto prevLocation,
                                LocationDto currentLocation,
                                List<DirectionDto> result
    ) {
        if (!currentLocation.x().equals(prevLocation.x())) {
            if (currentLocation.x() > prevLocation.x()) {
                result.add(new DirectionDto(Direction.EAST, currentLocation.x() - prevLocation.x()));
            } else {
                result.add(new DirectionDto(Direction.WEST, prevLocation.x() - currentLocation.x()));
            }
        }
        if (!currentLocation.y().equals(prevLocation.y())) {
            if (currentLocation.y() > prevLocation.y()) {
                result.add(new DirectionDto(Direction.NORTH, currentLocation.y() - prevLocation.y()));
            } else {
                result.add(new DirectionDto(Direction.SOUTH, prevLocation.y() - currentLocation.y()));
            }
        }

        return currentLocation;
    }

    private Location move(Location lastLocation, DirectionDto directionRequest) {
        Location location = new Location();
        location.setY(lastLocation.getY());
        location.setX(lastLocation.getX());
        switch (directionRequest.direction()) {
            case EAST -> location.setX(location.getX() + directionRequest.steps());
            case NORTH -> location.setY(location.getY() + directionRequest.steps());
            case SOUTH -> location.setY(location.getY() - directionRequest.steps());
            case WEST -> location.setX(location.getX() - directionRequest.steps());
        }
        return locationRepository.save(location);
    }
}
