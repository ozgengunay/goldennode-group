package com.ozgen.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ozgen.server.common.EVCloudRestException;
import com.ozgen.server.controllers.ChargingSession.Summary;
import com.ozgen.server.services.ChargingSessionException;
import com.ozgen.server.services.ChargingSessionService;

@RestController
@RequestMapping(value = { "/chargingSessions" })
@CrossOrigin(origins = "*")
public class ChargingSessionController {
    private ChargingSessionService chargingSessionService;

    @Autowired
    public ChargingSessionController(ChargingSessionService chargingSessionService) {
        this.chargingSessionService = chargingSessionService;
    }

    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity<ChargingSession> submitNewChargingSession(@RequestBody ChargingSession chargingSession) throws EVCloudRestException {
        try {
            ChargingSession persistedChargingSession = chargingSessionService.submitNew(chargingSession.getStationId());
            return new ResponseEntity<ChargingSession>(persistedChargingSession, HttpStatus.CREATED);
        } catch (ChargingSessionException e) {
            throw new EVCloudRestException(e);
        }
    }

    @RequestMapping(value = { "/{id}" }, method = { RequestMethod.PUT })
    public ResponseEntity<ChargingSession> stopChargingSession(@PathVariable("id") String id) throws EVCloudRestException {
        try {
            ChargingSession chargingSession = chargingSessionService.stop(id);
            return new ResponseEntity<ChargingSession>(chargingSession, HttpStatus.ACCEPTED);
        } catch (ChargingSessionException e) {
            throw new EVCloudRestException(e);
        }
    }

    @RequestMapping(method = { RequestMethod.GET })
    public ResponseEntity<ChargingSession[]> retrieveAllChargingSessions() {
        ChargingSession[] chargingSessions = chargingSessionService.retrieveAll();
        return new ResponseEntity<ChargingSession[]>(chargingSessions, HttpStatus.OK);
    }

    @RequestMapping(value = { "/summary" }, method = { RequestMethod.GET })
    public ResponseEntity<Summary> getSummary() {
        ChargingSession.Summary summary = chargingSessionService.getSummary();
        return new ResponseEntity<Summary>(summary, HttpStatus.OK);
    }
}
