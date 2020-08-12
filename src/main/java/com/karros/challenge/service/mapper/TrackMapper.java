package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.*;
import com.karros.challenge.service.dto.gps.ExtensionsType;
import com.karros.challenge.service.dto.gps.LinkType;
import com.karros.challenge.service.dto.gps.TrkType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class TrackMapper {
    private TrackSegmentMapper trackSegmentMapper;
    private CommonMapper commonMapper;

    public TrackMapper(TrackSegmentMapper trackSegmentMapper, CommonMapper commonMapper) {
        this.trackSegmentMapper = trackSegmentMapper;
        this.commonMapper = commonMapper;
    }

    public Track trkTypeToTrack(TrkType trkType) {
        if (trkType == null) {
            return null;
        }

        Track track = new Track();
        track.setDesc(trkType.getDesc());
        track.setCmt(trkType.getCmt());
        track.setExtensions(commonMapper.toJson(trkType.getExtensions()));
        track.setLink(commonMapper.toJson(trkType.getLink()));
        track.setName(trkType.getName());
        track.setNumber(trkType.getNumber());
        track.setSrc(trkType.getSrc());
        track.setType(trkType.getType());
        if (CollectionUtils.isNotEmpty(trkType.getTrkseg())) {
            List<TrackSegment> trackSegments = trackSegmentMapper
                .trksegTypeListToTrackSegmentList(trkType.getTrkseg(), track);
            track.setTrkseg(trackSegments);
        }

        return track;
    }

    public List<Track> trkTypeListToTrackList(List<TrkType> trkTypeList, Trip trip) {
        if (CollectionUtils.isEmpty(trkTypeList) || trip == null) {
            return Collections.emptyList();
        }
        return trkTypeList.stream()
            .map(trkType -> {
                Track track = this.trkTypeToTrack(trkType);
                track.setTrip(trip);
                return track;
            }).collect(Collectors.toList());
    }

    public TrkType trackToTrkType(Track track) {
        if (track == null) {
            return null;
        }

        TrkType trkType = new TrkType();
        trkType.setDesc(track.getDesc());
        trkType.setCmt(track.getCmt());
        trkType.setExtensions(commonMapper.fromJson(track.getExtensions()));
        trkType.setLink(commonMapper.fromJsonList(track.getLink()));
        trkType.setName(track.getName());
        trkType.setNumber(track.getNumber());
        trkType.setSrc(track.getSrc());
        trkType.setType(track.getType());
        if (CollectionUtils.isNotEmpty(track.getTrkseg())) {
            trkType.setTrkseg(trackSegmentMapper.trackSegmentListToTrksegTypeList(track.getTrkseg()));
        }

        return trkType;
    }

    public List<TrkType> trackListToTrkTypeList(List<Track> tracks) {
        if (CollectionUtils.isEmpty(tracks)) {
            return Collections.emptyList();
        }
        return tracks.stream()
            .map(this::trackToTrkType)
            .collect(Collectors.toList());
    }
}
