package com.ozgen.server.controllers;

import java.time.LocalDateTime;

public class ChargingSession {
    private String id;
    private String stationId;
    private LocalDateTime startedAt;
    private LocalDateTime stoppedAt;
    private Status status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getStoppedAt() {
        return stoppedAt;
    }

    public void setStoppedAt(LocalDateTime stoppedAt) {
        this.stoppedAt = stoppedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class Summary {
        private long totalCount;
        private long startedCount;
        private long stoppedCount;

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }

        public long getStartedCount() {
            return startedCount;
        }

        public void setStartedCount(long startedCount) {
            this.startedCount = startedCount;
        }

        public long getStoppedCount() {
            return stoppedCount;
        }

        public void setStoppedCount(long stoppedCount) {
            this.stoppedCount = stoppedCount;
        }
    }

    public enum Status {
        IN_PROGRESS, FINISHED
    }

    @Override
    public String toString() {
        return "ChargingSession [id=" + id + ", stationId=" + stationId + ", startedAt=" + startedAt + ", stoppedAt=" + stoppedAt + ", status=" + status + "]";
    }
}
