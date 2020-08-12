package com.karros.challenge.service;

import com.karros.challenge.domain.Trip;
import com.karros.challenge.repository.TripRepository;
import com.karros.challenge.service.dto.gps.GpxDTO;
import com.karros.challenge.service.dto.gps.GpxType;
import com.karros.challenge.service.mapper.TripMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final Logger log = LoggerFactory.getLogger(TripService.class);

    private TripRepository tripRepository;
    private TripMapper tripMapper;

    public TripService(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    @Transactional
    public GpxDTO save(GpxType gpxType) {
        Trip trip = tripMapper.gpxTypeToTripWithDetails(gpxType);
        trip = tripRepository.save(trip);
        return tripMapper.tripToGpxDTO(trip);
    }

    public GpxType read(MultipartFile multipartFile) throws IOException, ParserConfigurationException, SAXException, JAXBException {
        log.debug("Read gpx multipart File.");
        InputStream inputStream = multipartFile.getInputStream();

        JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(ResourceUtils.getFile("classpath:xsd/gpx.xsd"));
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbUnmarshaller.setSchema(schema);
        // Overloaded methods to unmarshal from different xml sources
        JAXBElement<GpxType> jaxbElement = (JAXBElement<GpxType>) jaxbUnmarshaller.unmarshal(inputStream);
        GpxType gpxType = jaxbElement.getValue();
        return gpxType;
    }

    @Transactional(readOnly = true)
    public Optional<GpxType> findById(long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        if (!optionalTrip.isPresent()) {
            return Optional.empty();
        }
        GpxType gpxType = tripMapper.tripToGpxTypeWithDetails(optionalTrip.get());
        return Optional.of(gpxType);
    }

    public Page<GpxDTO> search(Long userId, Pageable pageable) {
        Page<Trip> tripPage;
        if (userId != null && userId > 0) {
            tripPage = tripRepository.findByCreatedById(userId, pageable);
        } else {
            tripPage = tripRepository.findAll(pageable);
        }
        List<GpxDTO> contentPage = tripPage.getContent()
            .stream().map(tripMapper::tripToGpxDTO)
            .collect(Collectors.toList());
        return new PageImpl<>(contentPage, pageable, tripPage.getTotalElements());
    }

    public void marshal(GpxType gpxType, StringWriter sw) throws JAXBException, FileNotFoundException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(ResourceUtils.getFile("classpath:xsd/gpx.xsd"));

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setSchema(schema);
        marshaller.marshal(gpxType, sw);
    }
}
