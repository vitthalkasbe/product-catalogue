package org.oms.in.productcatalogue.entity;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Product extends RepresentationModel<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message ="Name can not be null")
    @Size(min = 3,max = 20,message = "Size should be in range of 3 to 20")
    private String name;

    @NotBlank(message ="ProductType can not be null")
    @Size(min = 3,max = 20,message = "Size should be in range of 3 to 20")
    private String productType;

    @NotNull(message ="Price can not be null")
    private Double price;

    private LocalDateTime createdAt=LocalDateTime.now();

}
