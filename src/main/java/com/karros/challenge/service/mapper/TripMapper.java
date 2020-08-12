package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.Trip;
import com.karros.challenge.service.dto.gps.ExtensionsType;
import com.karros.challenge.service.dto.gps.GpxDTO;
import com.karros.challenge.service.dto.gps.GpxType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TripMapper {
    private MetadataMapper metadataMapper;
    private WayPointMapper wayPointMapper;
    private RouteMapper routeMapper;
    private TrackMapper trackMapper;
    private CommonMapper commonMapper;

    public TripMapper(MetadataMapper metadataMapper, WayPointMapper wayPointMapper, RouteMapper routeMapper,
                      TrackMapper trackMapper, CommonMapper commonMapper) {
        this.metadataMapper = metadataMapper;
        this.wayPointMapper = wayPointMapper;
        this.routeMapper = routeMapper;
        this.trackMapper = trackMapper;
        this.commonMapper = commonMapper;
    }

    public Trip gpxTypeToTrip(GpxType gpx) {
        if (gpx == null) {
            return null;
        }

        Trip trip = new Trip();
        trip.setCreator(gpx.getCreator());
        trip.setExtensions(commonMapper.toJson(gpx.getExtensions()));
        trip.setVersion(gpx.getVersion());
        trip.setMetadata(metadataMapper.metadataTypeToMetadata(gpx.getMetadata()));
        return trip;
    }

    public Trip gpxTypeToTripWithDetails(GpxType gpx) {
        if (gpx == null) {
            return null;
        }
        Trip trip = gpxTypeToTrip(gpx);

        if (CollectionUtils.isNotEmpty(gpx.getWpt())) {
            trip.setWpt(wayPointMapper.wptTypeListToEntityList(gpx.getWpt(), trip));
        }
        if (CollectionUtils.isNotEmpty(gpx.getRte())) {
            trip.setRte(routeMapper.rteTypeListToRouteList(gpx.getRte(), trip));
        }
        if (CollectionUtils.isNotEmpty(gpx.getTrk())) {
            trip.setTrk(trackMapper.trkTypeListToTrackList(gpx.getTrk(), trip));
        }
        return trip;
    }

    public GpxDTO tripToGpxDTO(Trip trip) {
        if (trip == null) {
            return null;
        }

        GpxDTO dto = new GpxDTO();
        dto.setId(trip.getId());
        dto.setCreator(trip.getCreator());
        dto.setExtensions(commonMapper.fromJson(trip.getExtensions()));
        dto.setVersion(trip.getVersion());
        dto.setMetadata(metadataMapper.metadataToMetadataType(trip.getMetadata()));
        return dto;
    }

    public GpxType tripToGpxType(Trip trip) {
        if (trip == null) {
            return null;
        }

        GpxType gpxType = new GpxType();
        gpxType.setCreator(trip.getCreator());
        gpxType.setExtensions(commonMapper.fromJson(trip.getExtensions()));
        gpxType.setVersion(trip.getVersion());
        gpxType.setMetadata(metadataMapper.metadataToMetadataType(trip.getMetadata()));
        return gpxType;
    }

    public GpxType tripToGpxTypeWithDetails(Trip trip) {
        if (trip == null) {
            return null;
        }
        GpxType gpxType = tripToGpxType(trip);

        if (CollectionUtils.isNotEmpty(trip.getWpt())) {
            gpxType.setWpt(wayPointMapper.entityListToWptTypeList(trip.getWpt()));
        }
        if (CollectionUtils.isNotEmpty(trip.getRte())) {
            gpxType.setRte(routeMapper.routeListToRteTypeList(trip.getRte()));
        }
        if (CollectionUtils.isNotEmpty(trip.getTrk())) {
            gpxType.setTrk(trackMapper.trackListToTrkTypeList(trip.getTrk()));
        }
        return gpxType;
    }
}
