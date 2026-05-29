package com.undoschool.booking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Booking extends BaseEntity {

    @ManyToOne
    private Parent parent;

    @ManyToOne
    private Offering offering;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus =
            BookingStatus.CONFIRMED;

}