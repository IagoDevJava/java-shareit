package ru.practicum.shareit.booking.model;

import lombok.*;
import ru.practicum.shareit.booking.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Comparable<Booking> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    @Column(name = "booker_id", nullable = false)
    private Long bookerId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Booking)) return false;
        return id != null && id.equals(((Booking) obj).getId());
    }

    @Override
    public int compareTo(Booking o) {
        if (this.getStart().isBefore(o.getStart())) {
            return 1;
        } else if (this.getStart().isAfter(o.getStart())) {
            return -1;
        } else {
            return 0;
        }
    }
}