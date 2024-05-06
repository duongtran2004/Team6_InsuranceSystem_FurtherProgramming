package Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 26/04/2024 11:01
 * @project InsuranceManagementTeamProject
 */
@Entity
@DiscriminatorValue("DE")
public class Dependant extends Beneficiaries{



}
