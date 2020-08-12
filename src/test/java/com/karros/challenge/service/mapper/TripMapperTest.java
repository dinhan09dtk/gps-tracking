package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.Trip;
import com.karros.challenge.service.dto.gps.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link TripMapper}.
 */
public class TripMapperTest {

    private static final Long DEFAULT_ID = 1l;
    private static final String DEFAULT_NAME = "metadata_name";
    private static final String DEFAULT_DESC = "default_desc";
    private static final String DEFAULT_KEYWORD = "default_keyword";
    private static final String DEFAULT_CREATOR = "default_creator";
    private static final String DEFAULT_VERSION = "default_version";
    private static final String[] GIVEN_FIELDS = new String[] {"name", "desc", "keywords"};

    private CommonMapper commonMapper;
    private MetadataMapper metadataMapper;
    private WayPointMapper wayPointMapper;
    private TrackMapper trackMapper;
    private TrackSegmentMapper trackSegmentMapper;
    private TrackPointMapper trackPointMapper;
    private RouteMapper routeMapper;
    private RoutePointMapper routePointMapper;
    private TripMapper tripMapper;

    private Trip trip;
    private GpxType gpxType;
    private GpxDTO gpxDTO;

    @BeforeEach
    public void init() {
        commonMapper = new CommonMapper();
        metadataMapper = new MetadataMapper(commonMapper);
        wayPointMapper = new WayPointMapper();
        trackPointMapper = new TrackPointMapper();
        routePointMapper = new RoutePointMapper();
        trackSegmentMapper = new TrackSegmentMapper(trackPointMapper, commonMapper);
        trackMapper = new TrackMapper(trackSegmentMapper, commonMapper);
        routeMapper = new RouteMapper(routePointMapper, commonMapper);
        tripMapper = new TripMapper(metadataMapper, wayPointMapper, routeMapper, trackMapper, commonMapper);

        EmailType emailType = new EmailType();
        emailType.setId("1");
        emailType.setDomain("johndoe@localhost");

        LinkType linkType = new LinkType();
        linkType.setHref("http://localhost.com");
        linkType.setText("localhost");
        linkType.setType(null);

        PersonType author = new PersonType();
        author.setEmail(emailType);
        author.setLink(linkType);

        BoundsType boundsType = new BoundsType();
        boundsType.setMaxlat(BigDecimal.valueOf(1));
        boundsType.setMaxlon(BigDecimal.valueOf(2));
        boundsType.setMinlat(BigDecimal.valueOf(3));
        boundsType.setMinlon(BigDecimal.valueOf(4));

        CopyrightType copyrightType = new CopyrightType();
        copyrightType.setAuthor("author");
        copyrightType.setLicense("lincense");
        copyrightType.setYear(null);

        ExtensionsType extensionsType = new ExtensionsType();
        String jsonExtension = "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n<gpxtpx:TrackPointExtension xmlns=\"http://www.topografix.com/GPX/1/1\" xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\" xmlns:gpxx=\"http://www.garmin.com/xmlschemas/GpxExtensions/v3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><gpxtpx:hr>171</gpxtpx:hr>\n          </gpxtpx:TrackPointExtension>";
        List<Element> documents = commonMapper.stringListToDocumentList(Arrays.asList(jsonExtension));
        extensionsType.setAny(new ArrayList<>(documents));

        MetadataType metadataType = new MetadataType();
        metadataType.setAuthor(author);
        metadataType.setBounds(boundsType);
        metadataType.setCopyright(copyrightType);
        metadataType.setDesc(DEFAULT_DESC);
        metadataType.setExtensions(extensionsType);
        metadataType.setLink(Arrays.asList(linkType));
        metadataType.setKeywords(DEFAULT_KEYWORD);
        metadataType.setName(DEFAULT_NAME);

        gpxType = new GpxType();
        gpxType.setCreator(DEFAULT_CREATOR);
        gpxType.setExtensions(extensionsType);
        gpxType.setMetadata(metadataType);
        gpxType.setVersion(DEFAULT_VERSION);

        trip = new Trip();
        trip.setId(DEFAULT_ID);
        trip.setCreator(DEFAULT_CREATOR);
        trip.setExtensions(commonMapper.toJson(extensionsType));
        trip.setVersion(DEFAULT_VERSION);
        trip.setMetadata(metadataMapper.metadataTypeToMetadata(metadataType));

        gpxDTO = new GpxDTO();
        gpxDTO.setId(DEFAULT_ID);
        gpxDTO.setCreator(DEFAULT_CREATOR);
        gpxDTO.setExtensions(extensionsType);
        gpxDTO.setVersion(DEFAULT_VERSION);
        gpxDTO.setMetadata(metadataType);
    }

    @Test
    public void gpxTypeToTrip() {
        Trip trip = tripMapper.gpxTypeToTrip(gpxType);
        assertThat(trip).isEqualToComparingOnlyGivenFields(this.trip, new String[] {"creator", "extensions", "version"});
        AssertionHelper.assertMetadata(trip.getMetadata(), this.trip.getMetadata());
    }

    @Test
    public void tripToGpxType() {
        GpxType gpxType = tripMapper.tripToGpxType(trip);
        assertThat(gpxType).isEqualToComparingOnlyGivenFields(this.gpxType, new String[] {"creator", "version"});
        Element element = (Element) gpxType.getExtensions().getAny().get(0);
        Element elementOriginal = (Element) this.gpxType.getExtensions().getAny().get(0);
        assertThat(element.isEqualNode(elementOriginal)).isTrue();
        AssertionHelper.assertMetadataType(gpxType.getMetadata(), this.gpxType.getMetadata());
    }

    @Test
    public void tripToGpxDTO() {
        GpxDTO gpxDTO = tripMapper.tripToGpxDTO(trip);
        assertThat(gpxDTO).isEqualToComparingOnlyGivenFields(this.gpxDTO, new String[] {"creator", "version"});
        Element element = (Element) gpxDTO.getExtensions().getAny().get(0);
        Element elementOriginal = (Element) this.gpxDTO.getExtensions().getAny().get(0);
        assertThat(element.isEqualNode(elementOriginal)).isTrue();
        AssertionHelper.assertMetadataType(gpxDTO.getMetadata(), this.gpxDTO.getMetadata());
    }
}
