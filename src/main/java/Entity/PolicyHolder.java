package Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import javafx.scene.control.Button;

import java.util.Collection;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 26/04/2024 11:01
 * @project InsuranceManagementTeamProject
 */


@Entity
@DiscriminatorValue("PH")
public class PolicyHolder extends Beneficiaries {
    @Transient
    private Button addDependantButton;
    @OneToMany(mappedBy = "policyHolder")
    private Collection<Beneficiaries> listOfDependants;

    public Collection<Beneficiaries> getListOfDependants() {
        return listOfDependants;
    }

    public void setListOfDependants(Collection<Beneficiaries> listOfDependants) {
        this.listOfDependants = listOfDependants;
    }

    public Button getAddDependantButton() {
        return addDependantButton;
    }

    public void setAddDependantButton(Button addDependantButton) {
        this.addDependantButton = addDependantButton;
    }
}
