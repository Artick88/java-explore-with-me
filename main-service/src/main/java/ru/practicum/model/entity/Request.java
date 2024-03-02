package ru.practicum.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.utils.enums.StatusRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "requests")
@NoArgsConstructor
@SuperBuilder
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime created;

    @Column(name = "event_id")
    private Long event;

    @Column(name = "requester_id")
    private Long requester;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusRequest status;
}
