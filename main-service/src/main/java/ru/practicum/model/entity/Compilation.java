package ru.practicum.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pinned")
    private Boolean pinned;

    @Column(name = "title")
    private String title;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "compilation_event_relation", joinColumns = @JoinColumn(name = "compilation_id"))
    @Column(name = "event_id")
    private Set<Long> events;
}
