package com.americadigital.libupapi.Dao.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "c_category")
@Data
public class CategoryEntity {

    @Id
    public String idCategory;

    @Column(name = "name")
    @NotEmpty(message = "El nombre de la categoría es requerido.")
    public String name;

    @Column(name = "active")
    public boolean active;
}
