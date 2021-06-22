package es.carlop.spring5webflux.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    public String id;

    public String firstName;
    public String lastName;
}
