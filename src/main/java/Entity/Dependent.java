package Entity;
import jakarta.persistence.Entity;

import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("dependent")
public class Dependent extends Beneficiaries {

}

