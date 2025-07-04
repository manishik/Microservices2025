package manish.learn.bank.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "CustomerDetails")
public class CustomerMongo {

    @MongoId(FieldType.STRING)
    private Long custId;

    private String custFirstName;

    private String custMiddleName;

    private String custLastName;

    private String custEmail;

    private Long custPhoneNumber;

    private Long custCellPhoneNumber;

    private String custAddress;
}
