package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies", schema = "public")
@Getter
@ToString
@EqualsAndHashCode
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private final List<Person> employees;

    public Company() {
        employees = new ArrayList<>();
    }

    public Company(@NonNull final String companyName) {
        this();
        this.companyName = companyName;
    }

    public Company(@NonNull final Long id, @NonNull final String companyName) {
        this();
        this.id = id;
        this.companyName = companyName;
    }

    protected void setId(@NonNull final Long id) {
        this.id = id;
    }

    protected void setCompanyName(@NonNull final String companyName) {
        this.companyName = companyName;
    }

    public void hireEmployee(@NonNull final Person person) {
        employees.add(person);
        person.setCompany(this);
    }

    public void fireEmployee(@NonNull final Person person) {
        employees.remove(person);
        person.removeJob();
    }

}
