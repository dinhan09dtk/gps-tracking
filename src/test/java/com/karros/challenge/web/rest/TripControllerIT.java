package com.karros.challenge.web.rest;

import com.karros.challenge.GpsTrackingApp;
import com.karros.challenge.domain.Trip;
import com.karros.challenge.repository.TripRepository;
import com.karros.challenge.security.AuthoritiesConstants;
import com.karros.challenge.service.TripService;
import com.karros.challenge.service.mapper.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TripController} REST controller.
 */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@SpringBootTest(classes = GpsTrackingApp.class)
public class TripControllerIT {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripService tripService;

    @Autowired
    private EntityManager em;

    @Autowired
    private TripMapper tripMapper;

    @Autowired
    private MetadataMapper metadataMapper;

    @Autowired
    private WayPointMapper wayPointMapper;

    @Autowired
    private RoutePointMapper routePointMapper;

    @Autowired
    private TrackPointMapper trackPointMapper;

    @Autowired
    private TrackSegmentMapper trackSegmentMapper;

    @Autowired
    private TrackMapper trackMapper;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private MockMvc restTripMockMvc;

    private MultipartFile nonExtensionsGPXFile;

    private MultipartFile includExtensionsGPXFile;


    public MultipartFile parseMultipartFile(File file) {
        Path path = Paths.get(file.getPath());
        String name = file.getName();
        String originalFileName = file.getName();
        String contentType = MediaType.APPLICATION_ATOM_XML_VALUE;
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name, originalFileName, contentType, content);
        return result;
    }

    private Document obtenerDocumentDeByte(byte[] documentoXml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(documentoXml));
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        }
        return null;
    }

    @BeforeEach
    public void initTest() {
        try {
            this.nonExtensionsGPXFile = parseMultipartFile(ResourceUtils
                .getFile("classpath:gpx.sample/sample.gpx"));
            this.includExtensionsGPXFile = parseMultipartFile(ResourceUtils
                .getFile("classpath:gpx.sample/run.gpx"));
        } catch (FileNotFoundException e) {
        }
    }

    @Test
    @Transactional
    public void testUploadDocumentNonExtensions() throws Exception {
        restTripMockMvc.perform(post("/api/trips/upload")
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .content(TestUtil.convertObjectToJsonBytes(this.nonExtensionsGPXFile)))
            .andExpect(status().isCreated());

        Trip trip = tripRepository.findFirstByOrderByIdDesc();
        String xmlUrl = String.format("/api/trips/%s/xml", trip.getId());
        ResultActions getXMLAction = restTripMockMvc.perform(get(xmlUrl)
            .contentType(MediaType.APPLICATION_ATOM_XML_VALUE))
            .andExpect(status().isOk());
        getXMLAction.andDo(result -> {
            byte[] bytes = result.getResponse().getContentAsByteArray();
            Document document = obtenerDocumentDeByte(bytes);
            Document originalDocument = obtenerDocumentDeByte(this.nonExtensionsGPXFile.getBytes());
            assertThat(document.isEqualNode(originalDocument));
        });
    }

    @Test
    @Transactional
    public void testUploadDocumentIncludeExtensions() throws Exception {
        restTripMockMvc.perform(post("/api/trips/upload")
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .content(TestUtil.convertObjectToJsonBytes(this.nonExtensionsGPXFile)))
            .andExpect(status().isCreated());

        Trip trip = tripRepository.findFirstByOrderByIdDesc();
        String xmlUrl = String.format("/api/trips/%s/xml", trip.getId());
        ResultActions getXMLAction = restTripMockMvc.perform(get(xmlUrl)
            .contentType(MediaType.APPLICATION_ATOM_XML_VALUE))
            .andExpect(status().isOk());
        getXMLAction.andDo(result -> {
            byte[] bytes = result.getResponse().getContentAsByteArray();
            Document document = obtenerDocumentDeByte(bytes);
            Document originalDocument = obtenerDocumentDeByte(this.nonExtensionsGPXFile.getBytes());
            assertThat(document.isEqualNode(originalDocument));
        });
    }
}
