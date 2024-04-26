package Entity;

import jakarta.persistence.*;

import java.util.Collection;

/**
 * @author
 * @version ${}
 * @created 25/04/2024 14:10
 * @project InsuranceManagementApplication
 */
@Entity
@Table(name = "insurance_manager", schema = "public", catalog = "postgres")
public class InsuranceManager extends User {

    @OneToMany(mappedBy = "insuranceManager")
    private Collection<Claim> listOfClaims;
    @OneToMany(mappedBy = "insuranceManager")
    private Collection<InsuranceSurveyor> listOfSurveyors;



    public Collection<Claim> getListOfClaims() {
        return listOfClaims;
    }

    public void setListOfClaims(Collection<Claim> listOfClaims) {
        this.listOfClaims = listOfClaims;
    }

    public Collection<InsuranceSurveyor> getListOfSurveyors() {
        return listOfSurveyors;
    }

    public void setListOfSurveyors(Collection<InsuranceSurveyor> listOfSurveyors) {
        this.listOfSurveyors = listOfSurveyors;
    }
}
