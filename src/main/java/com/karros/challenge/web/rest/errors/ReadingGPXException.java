package com.karros.challenge.web.rest.errors;

import org.zalando.problem.ThrowableProblem;

public class ReadingGPXException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ReadingGPXException(String message) {
        super(ErrorConstants.READING_GPX_TYPE, "Failed Reading GPX", message, "trip", "reading_gpx_error");
    }
}
