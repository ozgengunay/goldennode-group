package com.ozgen.server.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import com.ozgen.server.common.EVCloudRestException;
import com.ozgen.server.controllers.ChargingSession.Status;
import com.ozgen.server.services.ChargingSessionException;
import com.ozgen.server.services.ChargingSessionService;
import com.ozgen.server.services.ChargingSessionServiceImpl;

public class ChargingSessionControllerTest {
    @Test
    public void submitNewChargingSession() throws EVCloudRestException, ChargingSessionException {
        ChargingSessionService chargingSessionService = mock(ChargingSessionServiceImpl.class);
        String sessionId = "session0";
        String stationId = "station0";
        ChargingSession givenSession = new ChargingSession();
        givenSession.setStationId(stationId);
        ChargingSession returnedSession = new ChargingSession();
        returnedSession.setStationId(stationId);
        returnedSession.setId(sessionId);
        returnedSession.setStartedAt(LocalDateTime.now());
        returnedSession.setStoppedAt(null);
        returnedSession.setStatus(Status.IN_PROGRESS);
        ChargingSessionController controller = new ChargingSessionController(chargingSessionService);
        when(chargingSessionService.submitNew(stationId)).thenReturn(returnedSession);
        ResponseEntity<ChargingSession> entity = controller.submitNewChargingSession(givenSession);
        assertEquals(stationId, entity.getBody().getStationId());
        assertEquals(sessionId, entity.getBody().getId());
        assertTrue(ChargingSessionServiceImpl.withinLastMinute(entity.getBody().getStartedAt()));
        assertNull(entity.getBody().getStoppedAt());
        assertEquals(Status.IN_PROGRESS, entity.getBody().getStatus());
        verify(chargingSessionService).submitNew(stationId);
    }

    @Test
    public void stopChargingSession() throws EVCloudRestException, ChargingSessionException {
        ChargingSessionService chargingSessionService = mock(ChargingSessionServiceImpl.class);
        String sessionId = "session0";
        String stationId = "station0";
        ChargingSession returnedSession = new ChargingSession();
        returnedSession.setStationId(stationId);
        returnedSession.setId(sessionId);
        returnedSession.setStartedAt(LocalDateTime.now());
        returnedSession.setStoppedAt(null);
        returnedSession.setStatus(Status.IN_PROGRESS);
        ChargingSessionController controller = new ChargingSessionController(chargingSessionService);
        when(chargingSessionService.stop(sessionId)).thenReturn(returnedSession);
        ResponseEntity<ChargingSession> entity = controller.stopChargingSession(sessionId);
        assertEquals(stationId, entity.getBody().getStationId());
        assertEquals(sessionId, entity.getBody().getId());
        assertTrue(ChargingSessionServiceImpl.withinLastMinute(entity.getBody().getStartedAt()));
        assertNull(entity.getBody().getStoppedAt());
        assertEquals(Status.IN_PROGRESS, entity.getBody().getStatus());
        verify(chargingSessionService).stop(sessionId);
    }

    @Test
    public void retrieveAllChargingSessions() throws EVCloudRestException, ChargingSessionException {
        ChargingSessionService chargingSessionService = mock(ChargingSessionServiceImpl.class);
        List<ChargingSession> sessions = new ArrayList<>();
        final int totalStations = 3;
        for (int i = 0; i < totalStations; i++) {
            String sessionId = "session" + i;
            String stationId = "station" + i;
            ChargingSession returnedSession0 = new ChargingSession();
            returnedSession0.setStationId(sessionId);
            returnedSession0.setId(stationId);
            returnedSession0.setStartedAt(LocalDateTime.now());
            returnedSession0.setStoppedAt(null);
            returnedSession0.setStatus(Status.IN_PROGRESS);
            sessions.add(returnedSession0);
        }
        ChargingSessionController controller = new ChargingSessionController(chargingSessionService);
        when(chargingSessionService.retrieveAll()).thenReturn(sessions.toArray(new ChargingSession[0]));
        ResponseEntity<ChargingSession[]> entity = controller.retrieveAllChargingSessions();
        assertEquals(totalStations, entity.getBody().length);
        for (int i = 0; i < totalStations; i++) {
            sessions.contains(entity.getBody()[i]);
        }
        verify(chargingSessionService).retrieveAll();
    }
}
