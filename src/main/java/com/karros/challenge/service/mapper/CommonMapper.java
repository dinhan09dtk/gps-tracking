package com.karros.challenge.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karros.challenge.service.dto.gps.ExtensionsType;
import com.karros.challenge.service.dto.gps.LinkType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommonMapper {
    private GsonJsonParser gsonJsonParser;
    private Gson gson;
    private ObjectMapper objectMapper;

    public CommonMapper() {
        this.gsonJsonParser = new GsonJsonParser();
        this.gson = new GsonBuilder().create();
        this.objectMapper = new ObjectMapper();
    }

    public XMLGregorianCalendar dateToXmlDate(Date date) {
        try {
            if (Objects.nonNull(date)) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(date);
                XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gregorianCalendar);
                return xmlDate;
            }
        } catch (DatatypeConfigurationException e) {}
        return null;
    }

    public Date xmlDateToDate(XMLGregorianCalendar xmlDate) {
        if (Objects.nonNull(xmlDate)) {
            return xmlDate.toGregorianCalendar().getTime();
        }
        return null;
    }

    public String toJson(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ExtensionsType) {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {}
        }
        return gson.toJson(object);
    }

    public ExtensionsType fromJson(String jsonStr) {
        if (StringUtils.isNotEmpty(jsonStr)) {
            ExtensionsType extensionsType = gson.fromJson(jsonStr, ExtensionsType.class);
            if (extensionsType != null) {
                if (CollectionUtils.isNotEmpty(extensionsType.getAny())) {
                    List<String> stringList = extensionsType.getAny()
                        .stream().map(o -> {
                            if (o != null) {
                                return o.toString();
                            }
                            return null;
                        }).filter(StringUtils::isNotEmpty)
                        .collect(Collectors.toList());

                    List<Element> documents = stringListToDocumentList(stringList);

                    ExtensionsType result = new ExtensionsType();
                    result.setAny(new ArrayList<>(documents));
                    return result;
                } else {
                    extensionsType. setAny(Collections.emptyList());
                }
            }
            return extensionsType;
        }
        return null;
    }

    private List<Element> stringListToDocumentList(List<String> objects) {
        if (objects == null) {
            return null;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            List<Element> documents = new ArrayList<>();
            objects.stream().forEach(xmlContent -> {
                if (xmlContent != null) {
                    try {
                        InputSource inputSource = new InputSource(new StringReader(xmlContent));
                        Document document = (Document) dBuilder.parse(inputSource);
                        documents.add(document.getDocumentElement());
                    } catch (SAXException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return documents;
        } catch (ParserConfigurationException e) {}
        return Collections.emptyList();
    }

    public <T> T fromJson(String jsonStr, Class<T> classOfT) {
        return gson.fromJson(jsonStr, classOfT);
    }

    public List<LinkType> fromJsonList(String json) {
        LinkType[] array = gson.fromJson(json, LinkType[].class);
        return Arrays.asList(array);
    }

    public <T> List<T> parseList(String jsonStr, Class<T> classOfT)  {
        return (List<T>) gsonJsonParser.parseList(jsonStr);
    }
}
