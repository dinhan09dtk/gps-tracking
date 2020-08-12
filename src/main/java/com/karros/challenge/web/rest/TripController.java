package com.karros.challenge.web.rest;

import com.karros.challenge.domain.User;
import com.karros.challenge.service.TripService;
import com.karros.challenge.service.UserService;
import com.karros.challenge.service.dto.gps.GpxDTO;
import com.karros.challenge.service.dto.gps.GpxType;
import com.karros.challenge.web.rest.errors.ReadingGPXException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TripController {
    private final Logger log = LoggerFactory.getLogger(TripController.class);

    private TripService tripService;
    private UserService userService;

    public TripController(TripService tripService, UserService userService) {
        this.tripService = tripService;
        this.userService = userService;
    }

    @PostMapping(value = "trips/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GpxDTO> upload(@RequestParam("file") MultipartFile file) throws URISyntaxException {
        try {
            GpxType gpxType = tripService.read(file);
            return new ResponseEntity<>(tripService.save(gpxType), HttpStatus.CREATED);
        } catch (IOException | ParserConfigurationException | SAXException | JAXBException e) {
            String message = StringUtils.isEmpty(e.getMessage()) && e.getCause() != null?
                e.getCause().getMessage(): e.getMessage();
            throw new ReadingGPXException(message);
        }
    }

    @GetMapping(value = "/trips", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<GpxDTO>> searchByUser(Pageable pageable) throws URISyntaxException {
        User loginedUser = userService.getUserWithAuthorities().get();
        Page<GpxDTO> gpxPage = tripService.search(loginedUser.getId(), pageable);
        return ResponseEntity.ok().body(gpxPage);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/management/trips", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<GpxDTO>> searchByAdmin(Pageable pageable) throws URISyntaxException {
        Page<GpxDTO> gpxPage = tripService.search(null, pageable);
        return ResponseEntity.ok().body(gpxPage);
    }

    @GetMapping(value = "/trips/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GpxType> findById(@PathVariable long id) throws URISyntaxException {
        Optional<GpxType> optionalGpxType = tripService.findById(id);
        if (!optionalGpxType.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(optionalGpxType.get());
    }

    @GetMapping(value = "/trips/{id}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXMLById(@PathVariable long id) throws URISyntaxException, JAXBException, FileNotFoundException, SAXException {
        Optional<GpxType> optionalGpxType = tripService.findById(id);
        if (!optionalGpxType.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        GpxType gpxType = optionalGpxType.get();
        StringWriter sw = new StringWriter();

        tripService.marshal(gpxType, sw);
        return ResponseEntity.ok().body(sw.toString());
    }
}
