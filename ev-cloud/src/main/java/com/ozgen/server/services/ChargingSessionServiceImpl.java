package com.ozgen.server.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ozgen.server.controllers.ChargingSession;
import com.ozgen.server.controllers.ChargingSession.Status;
import com.ozgen.server.controllers.ChargingSession.Summary;

@Service
public class ChargingSessionServiceImpl implements ChargingSessionService {
    private Map<String, ChargingSession> chargingSessions;

    @Autowired
    public ChargingSessionServiceImpl(Map<String, ChargingSession> chargingSessionMap) {
        this.chargingSessions = chargingSessionMap;
    }

    @Override
    public ChargingSession stop(String sessionId) throws ChargingSessionException {
        ChargingSession chargingSession = chargingSessions.get(sessionId);
        if (chargingSession == null) {
            throw new ChargingSessionException(sessionId + " not found");
        }
        chargingSession.setStoppedAt(LocalDateTime.now());
        chargingSession.setStatus(Status.FINISHED);
        chargingSessions.put(chargingSession.getId().toString(), chargingSession);
        return chargingSession;
    }

    @Override
    public ChargingSession submitNew(String stationId) throws ChargingSessionException {
        ChargingSession station = getStation(stationId);
        if (station != null) {
            throw new ChargingSessionException(stationId + " has aldready been submitted");
        }
        ChargingSession chargingSession = new ChargingSession();
        chargingSession.setId(UUID.randomUUID().toString());
        chargingSession.setStationId(stationId);
        chargingSession.setStartedAt(LocalDateTime.now());
        chargingSession.setStoppedAt(null);
        chargingSession.setStatus(Status.IN_PROGRESS);
        chargingSessions.put(chargingSession.getId().toString(), chargingSession);
        return chargingSession;
    }

    @Override
    public ChargingSession[] retrieveAll() {
        Collection<ChargingSession> chargingSessionsValues = chargingSessions.values();
        return chargingSessionsValues.toArray(new ChargingSession[0]);
    }

    @Override
    public Summary getSummary() {
        Collection<ChargingSession> chargingSessionsValues = chargingSessions.values();
        long startedCount = chargingSessionsValues.stream().filter(session -> withinLastMinute(session.getStartedAt())).count();
        long stoppedCount = chargingSessionsValues.stream().filter(session -> session.getStoppedAt() != null && withinLastMinute(session.getStoppedAt())).count();
        long totalCount = chargingSessionsValues.stream().filter(session -> withinLastMinute(session.getStartedAt()) || (session.getStoppedAt() != null && withinLastMinute(session.getStoppedAt())))
                .count();
        Summary summary = new Summary();
        summary.setStartedCount(startedCount);
        summary.setStoppedCount(stoppedCount);
        summary.setTotalCount(totalCount);
        return summary;
    }

    public static boolean withinLastMinute(LocalDateTime givenTime) {
        return givenTime.toEpochSecond(ZoneOffset.UTC) > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - 60;
    }

    @Override
    public ChargingSession getStation(String stationId) {
        // We have to lookup stationId manually as our primary key is sessionId instead of stationId in the data structure.
        Collection<ChargingSession> chargingSessionsValues = chargingSessions.values();
        try {
            return chargingSessionsValues.stream().filter(session -> session.getStationId().equals(stationId)).findAny().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
