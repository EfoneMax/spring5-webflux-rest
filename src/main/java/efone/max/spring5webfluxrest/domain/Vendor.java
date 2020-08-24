package efone.max.spring5webfluxrest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Vendor {
    @Id
    String id;
    String firstname;
    String lastname;
}
