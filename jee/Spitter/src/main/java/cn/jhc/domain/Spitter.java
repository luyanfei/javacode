package cn.jhc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Spitter
 *
 */
@Entity
@Table(name="spitter",uniqueConstraints= {@UniqueConstraint(columnNames="username")})
public class Spitter implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@TableGenerator(
			name="spitter_gen",
			pkColumnValue="spitter_seq",
			initialValue=50
			)
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="spitter_gen")
	private long id;
	
	@Size(min=3,max=20,message="用户名的长度必须在3至20个字符之间。")
	@Pattern(regexp="^[a-zA-Z0-9]+$",message="用户名只能使用数字和字母。")
	private String username;
	
	@Size(min=6, max=20,
			message="The password must be at least 6 characters long.")
	private String password;
	
	@Size(min=3, max=50, message=
			"Your full name must be between 3 and 50 characters long.")
	private String fullName;
	
	@Pattern(regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
			message="Invalid email address.")
	private String email;
	private boolean updateByEmail;
	
	@OneToMany(mappedBy="spitter")
	private Collection<Spittle> spittles = new ArrayList<Spittle>();
	
	public Spitter() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isUpdateByEmail() {
		return updateByEmail;
	}

	public void setUpdateByEmail(boolean updateByEmail) {
		this.updateByEmail = updateByEmail;
	}
   
}
