package com.ozgen.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import com.ozgen.server.controllers.ChargingSession;
import com.ozgen.server.controllers.ChargingSession.Status;
import com.ozgen.server.controllers.ChargingSession.Summary;

public class ChargingSessionServiceTest {
    private ChargingSessionService service;

    @Before
    public void init() throws Exception {
        service = new ChargingSessionServiceImpl(new HashMap<String, ChargingSession>());
    }

    @Test
    public void testStop() throws ChargingSessionException {
        ChargingSession session = service.submitNew("station1");
        assertEquals(Status.IN_PROGRESS, service.getStation("station1").getStatus());
        session = service.stop(session.getId());
        assertEquals(Status.FINISHED, session.getStatus());
    }

    @Test(expected = ChargingSessionException.class)
    public void testStopNotSubmitted() throws ChargingSessionException {
        service.stop("unavailable session");
    }

    @Test
    public void testSubmitNew() throws ChargingSessionException {
        ChargingSession session = service.submitNew("station1");
        assertEquals("station1", session.getStationId());
        assertNotNull(session.getId());
        assertTrue(session.getStartedAt().toEpochSecond(ZoneOffset.UTC) <= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        assertNull(session.getStoppedAt());
        assertEquals(Status.IN_PROGRESS, session.getStatus());
    }

    @Test(expected = ChargingSessionException.class)
    public void testSubmitNewDuplicate() throws ChargingSessionException {
        ChargingSession session = service.submitNew("station1");
        assertEquals("station1", session.getStationId());
        assertNotNull(session.getId());
        assertTrue(session.getStartedAt().toEpochSecond(ZoneOffset.UTC) <= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        assertNull(session.getStoppedAt());
        assertEquals(Status.IN_PROGRESS, session.getStatus());
        service.submitNew("station1");
    }

    @Test
    public void testRetrieveAll() throws ChargingSessionException {
        final int totalStations = 3;
        for (int i = 0; i < totalStations; i++) {
            service.submitNew("station" + i);
        }
        service.stop(service.getStation("station0").getId());
        ChargingSession[] sessions = service.retrieveAll();
        assertEquals(totalStations, sessions.length);
        Arrays.stream(sessions).filter(session -> session.getStationId().equals("station0")).forEach(session -> assertEquals(Status.FINISHED, session.getStatus()));
        Arrays.stream(sessions).filter(session -> session.getStationId().equals("station1")).forEach(session -> assertEquals(Status.IN_PROGRESS, session.getStatus()));
        Arrays.stream(sessions).filter(session -> session.getStationId().equals("station2")).forEach(session -> assertEquals(Status.IN_PROGRESS, session.getStatus()));
        Arrays.stream(sessions).filter(session -> session.getStationId().equals("station0")).forEach(session -> assertNotNull(session.getStoppedAt()));
        Arrays.stream(sessions).filter(session -> session.getStationId().equals("station1")).forEach(session -> assertNull(session.getStoppedAt()));
        Arrays.stream(sessions).filter(session -> session.getStationId().equals("station2")).forEach(session -> assertNull(session.getStoppedAt()));
    }

    @Test
    public void testGetSummary() throws ChargingSessionException {
        final int totalStations = 10;
        for (int i = 0; i < totalStations; i++) {
            service.submitNew("station" + i);
        }
        service.stop(service.getStation("station0").getId());
        Summary summary = service.getSummary();
        assertEquals(totalStations, summary.getStartedCount());
        assertEquals(1, summary.getStoppedCount());
        assertEquals(totalStations, summary.getTotalCount());
    }

    @Test
    public void testWithinLastMinute() {
        LocalDateTime datetime = LocalDateTime.now();
        datetime = datetime.minusSeconds(55);
        assertEquals(true, ChargingSessionServiceImpl.withinLastMinute(datetime));
        datetime = datetime.minusSeconds(10);
        assertEquals(false, ChargingSessionServiceImpl.withinLastMinute(datetime));
    }

    @Test
    public void testGetStation() throws ChargingSessionException {
        final int totalStations = 10;
        for (int i = 0; i < totalStations; i++) {
            service.submitNew("station" + i);
        }
        assertNotNull(service.getStation("station0"));
        assertNull(service.getStation("station11"));
    }
}
