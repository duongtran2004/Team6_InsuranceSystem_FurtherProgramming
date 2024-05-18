package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author
 * @version ${}
 * @created 26/04/2024 10:58
 * @project InsuranceManagementTeamProject
 */
@Entity
@Table(name = "system_admin", schema = "public", catalog = "postgres")
public class SystemAdmin extends User {

}
