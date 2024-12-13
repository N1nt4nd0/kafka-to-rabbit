package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs", schema = "public")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private final List<Person> employees = new ArrayList<>();

    public Job(@NonNull final String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Job(@NonNull final Long id, @NonNull final String jobTitle) {
        this.id = id;
        this.jobTitle = jobTitle;
    }

    protected void setId(@NonNull final Long id) {
        this.id = id;
    }

    protected void setJobTitle(@NonNull final String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
