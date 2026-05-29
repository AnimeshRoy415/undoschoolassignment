package com.undoschool.booking.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ParentEntity parent;

    @ManyToOne
    private Offering offering;

    private Instant bookedAt;

    public Booking() {
        this.bookedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParentEntity getParent() {
        return parent;
    }

    public void setParent(ParentEntity parent) {
        this.parent = parent;
    }

    public Offering getOffering() {
        return offering;
    }

    public void setOffering(Offering offering) {
        this.offering = offering;
    }

    public Instant getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Instant bookedAt) {
        this.bookedAt = bookedAt;
    }
}