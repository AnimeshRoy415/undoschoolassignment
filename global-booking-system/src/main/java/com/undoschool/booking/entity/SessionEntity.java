package com.undoschool.booking.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "sessions")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Offering offering;

    private Instant startTimeUtc;

    private Instant endTimeUtc;

    public SessionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offering getOffering() {
        return offering;
    }

    public void setOffering(Offering offering) {
        this.offering = offering;
    }

    public Instant getStartTimeUtc() {
        return startTimeUtc;
    }

    public void setStartTimeUtc(Instant startTimeUtc) {
        this.startTimeUtc = startTimeUtc;
    }

    public Instant getEndTimeUtc() {
        return endTimeUtc;
    }

    public void setEndTimeUtc(Instant endTimeUtc) {
        this.endTimeUtc = endTimeUtc;
    }
}