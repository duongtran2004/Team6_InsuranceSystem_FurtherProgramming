package Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.OneToMany;

import java.util.Collection;

@Entity
@DiscriminatorValue("policyHolder")
public class PolicyHolder extends Beneficiaries {
    @OneToMany(mappedBy = "policyHolder")
    private Collection<Beneficiaries> listOfDependants;

    public Collection<Beneficiaries> getListOfDependants() {
        return listOfDependants;
    }

    public void setListOfDependants(Collection<Beneficiaries> listOfDependants) {
        this.listOfDependants = listOfDependants;
    }
    // Additional fields specific to PolicyHolder, if any
}
