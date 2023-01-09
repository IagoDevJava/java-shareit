package ru.practicum.shareit.item.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(name = "is_available")
    private Boolean available;
    @Column(name = "owner_id")
    private Long ownerId;
    @Column(name = "request_id")
    private Long requestId;

    public void setId(Long id) {
        if (id != null) {
            this.id = id;
        }
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public void setAvailable(Boolean available) {
        if (available != null) {
            this.available = available;
        }
    }

    public void setOwnerId(Long ownerId) {
        if (ownerId != null) {
            this.ownerId = ownerId;
        }
    }

    public void setRequestId(Long requestId) {
        if (requestId != null) {
            this.requestId = requestId;
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Item)) return false;
        return id != null && id.equals(((Item) obj).getId());
    }
}