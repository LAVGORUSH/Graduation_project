package com.lavgorush.graduation_project.voting.model;

import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2,max = 100)
    @Column(name = "name", nullable = false)
    @SafeHtml
    private String name;

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
