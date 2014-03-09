package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "event_type", catalog = "masterThesis")
public class EventType implements java.io.Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "name", nullable = false, length = 20)
	private String name;
	@Column(name = "desc", nullable = false)
	private String desc;
	private Set<User> users = new HashSet<User>(0);

	
	public EventType() {
	}
 
	public EventType(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
	
	public EventType(String name, String desc, Set<User> stocks) {
		this.name = name;
		this.desc = desc;
		this.users = users;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "eventTypes")
	public Set<User> getUsers() {
		return users;
	}

	
	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
