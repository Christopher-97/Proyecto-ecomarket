package com.example.Cliente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends RepresentationModel<UserModel> {
 
    private Integer id;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String run;
    private String mail;
    private String rol;
}
