package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.*;
import com.karros.challenge.service.dto.gps.ExtensionsType;
import com.karros.challenge.service.dto.gps.TrksegType;
import com.karros.challenge.service.dto.gps.WptType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class TrackSegmentMapper {
    private TrackPointMapper trackPointMapper;
    private CommonMapper commonMapper;

    public TrackSegmentMapper(TrackPointMapper trackPointMapper, CommonMapper commonMapper) {
        this.trackPointMapper = trackPointMapper;
        this.commonMapper = commonMapper;
    }

    public TrackSegment trksegTypeToTrackSegment(TrksegType trksegType) {
        if (trksegType == null) {
            return null;
        }

        TrackSegment trackSegment = new TrackSegment();
        trackSegment.setExtensions(commonMapper.toJson(trksegType.getExtensions()));
        if (CollectionUtils.isNotEmpty(trksegType.getTrkpt())) {
            List<TrackPoint> trackPoints = trackPointMapper.wptTypeListToEntityList(trksegType.getTrkpt(), trackSegment);
            trackSegment.setTrkpt(trackPoints);
        }
        return trackSegment;
    }

    public List<TrackSegment> trksegTypeListToTrackSegmentList(List<TrksegType> trksegTypeList, Track track) {
        if (CollectionUtils.isEmpty(trksegTypeList) || track == null) {
            return Collections.emptyList();
        }
        return trksegTypeList.stream()
            .map(trksegType -> {
                TrackSegment trackSegment = this.trksegTypeToTrackSegment(trksegType);
                trackSegment.setTrack(track);
                return trackSegment;
            }).collect(Collectors.toList());
    }

    public TrksegType trackSegmentToTrksegType(TrackSegment trackSegment) {
        if (trackSegment == null) {
            return null;
        }

        TrksegType trksegType = new TrksegType();
        trksegType.setExtensions(commonMapper.fromJson(trackSegment.getExtensions()));
        if (CollectionUtils.isNotEmpty(trackSegment.getTrkpt())) {
            List<WptType> points = trackPointMapper.entityListToWptTypeList(trackSegment.getTrkpt());
            trksegType.setTrkpt(points);
        }
        return trksegType;
    }

    public List<TrksegType> trackSegmentListToTrksegTypeList(List<TrackSegment> trackSegments) {
        if (CollectionUtils.isEmpty(trackSegments)) {
            return Collections.emptyList();
        }
        return trackSegments.stream()
            .map(this::trackSegmentToTrksegType)
            .collect(Collectors.toList());
    }
}
