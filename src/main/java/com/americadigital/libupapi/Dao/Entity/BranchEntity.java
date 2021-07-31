package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_branch")
@Data
public class BranchEntity {

    @Id
    public String idBranch;

    @Column(name = "address")
    @Size(min = 1, max = 110, message = "Dirección debe tener entre 1 y 110 caracteres.")
    @NotEmpty(message = "La dirección es requerida.")
    public String address;

    @Column(name = "phone_number")
    @Size(min = 10, max = 10, message
            = "Teléfono debe tener 10 caracteres.")
    public String phoneNumber;

    @Column(name = "active")
    public boolean active;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    public BranchType type;

    @Column(name = "latitud")
    @Size(min = 1, max = 200, message
            = "Latitud debe tener entre 1 y 200 caracteres.")
    @NotEmpty(message = "La latitud es requerida.")
    public String latitud;

    @Column(name = "Longitud")
    @Size(min = 1, max = 200, message
            = "Longitud debe tener entre 1 y 200 caracteres.")
    @NotEmpty(message = "La longitud es requerida.")
    public String longitud;

    @Column(name = "id_shop")
    @NotEmpty(message = "El id de comercio es requerido.")
    public String idShop;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_shop", insertable = false, updatable = false)
    @JsonIgnore
    public ShopEntity shopEntity;

    @Column(name = "id_state")
    public Long idState;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_state", insertable = false, updatable = false)
    @JsonIgnore
    public StatesEntity statesEntity;

    public enum BranchType {
        Principal,
        Sucursal,
    }

    public BranchEntity(){

    }
}
