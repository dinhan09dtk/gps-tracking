package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.TrackPoint;
import com.karros.challenge.domain.TrackSegment;
import com.karros.challenge.service.dto.gps.WptType;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TrackPointMapper extends AbstractPointMapper<TrackPoint> {
    @Override
    public TrackPoint wptTypeToEntity(WptType wptType, Object parent) {
        if (wptType == null || !(parent instanceof TrackSegment)) {
            return null;
        }
        TrackPoint trackPoint = new TrackPoint();
        wptTypeListToEntityList(wptType, trackPoint);
        trackPoint.setTrackSegment((TrackSegment) parent);
        return trackPoint;
    }
}
