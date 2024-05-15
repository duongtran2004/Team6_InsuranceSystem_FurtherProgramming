package Entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 26/04/2024 11:01
 * @project InsuranceManagementTeamProject
 */
@MappedSuperclass
public class Customer extends User{
    @Transient
    protected int totalSuccessfulClaimAmount;

    public int getTotalSuccessfulClaimAmount() {
        return totalSuccessfulClaimAmount;

    }

    public void setTotalSuccessfulClaimAmount(int totalSuccessfulClaimAmount) {
        this.totalSuccessfulClaimAmount = totalSuccessfulClaimAmount;
    }
}
