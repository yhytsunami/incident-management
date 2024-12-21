package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class IncidentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 100, message = "name length should in range 1-100")
    private String name;


    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    @PrePersist
    public void setCreateDate(){
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdateDate(){
        updateTime = LocalDateTime.now();
    }
}
