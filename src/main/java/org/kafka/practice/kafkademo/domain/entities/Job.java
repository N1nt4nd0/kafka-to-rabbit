package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs", schema = "public")
@Getter
@ToString
@EqualsAndHashCode
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "job_title", nullable = false, unique = true)
    private String jobTitle;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private final List<Person> employees;

    public Job() {
        employees = new ArrayList<>();
    }

    public Job(@NonNull final String jobTitle) {
        this();
        this.jobTitle = jobTitle;
    }

    public Job(@NonNull final Long id, @NonNull final String jobTitle) {
        this();
        this.id = id;
        this.jobTitle = jobTitle;
    }

    protected void setId(@NonNull final Long id) {
        this.id = id;
    }

    protected void setJobTitle(@NonNull final String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void hireEmployee(@NonNull final Person person) {
        employees.add(person);
        person.setJob(this);
    }

    public void fireEmployee(@NonNull final Person person) {
        employees.remove(person);
        person.removeJob();
    }

}
