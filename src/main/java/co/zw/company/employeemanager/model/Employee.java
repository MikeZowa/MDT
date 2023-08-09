package co.zw.company.employeemanager.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Full name cannot be null")
    @NotBlank(message = "Full name cannot be empty")
    @Column(nullable = false)
    private String fullName;
    @NotNull(message = "Position cannot be null")
    @NotBlank(message = "Position cannot be empty")
    @Column(nullable = false)
    private String position;
    @Positive(message = "Unique key must be a positive integer")
    private long uniqueKey;

}
