package bg.fmi.master.thesis.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;
import bg.fmi.master.thesis.model.TUser;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*
 * Таблица с видовете роли, които 1 потребител може да има в системата
 */

@Entity
@Table(name = "T_ROLE")
public class TRole implements java.io.Serializable{

	/*
	 * ИД на ролята
	 */
	private Long id;
	
	/*
	 * Наименование на ролята
	 */
	private String roleName;
	
	/*
	 * Потребители, имащи определена роля в системата
	 */
	private Set<TUser> tUsersWithThisRole = new HashSet<TUser>(0);
	
	public TRole(){
	}
	
	public TRole(String roleName){
		this.roleName = roleName;
	}

	public TRole(String roleName, Set<TUser> tUsersWithThisRole) {
		super();
		this.roleName = roleName;
		this.tUsersWithThisRole = tUsersWithThisRole;
	}

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_ROLE", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ROLE_NAME", nullable = false, length = 15)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userRole")	
	public Set<TUser> gettUsersWithThisRole() {
		return tUsersWithThisRole;
	}

	public void settUsersWithThisRole(Set<TUser> tUsersWithThisRole) {
		this.tUsersWithThisRole = tUsersWithThisRole;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TRole other = (TRole) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
