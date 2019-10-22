package com.ozgen.server.services;

import com.ozgen.server.controllers.ChargingSession;
import com.ozgen.server.controllers.ChargingSession.Summary;

public interface ChargingSessionService {
    public ChargingSession stop(String sessionId) throws ChargingSessionException;

    public ChargingSession submitNew(String stationId) throws ChargingSessionException;

    public ChargingSession[] retrieveAll();

    public Summary getSummary();

    public ChargingSession getStation(String stationId);
}
