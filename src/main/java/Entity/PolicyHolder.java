package Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("policyHolder")
public class PolicyHolder extends Beneficiaries {
    // Additional fields specific to PolicyHolder, if any
}
