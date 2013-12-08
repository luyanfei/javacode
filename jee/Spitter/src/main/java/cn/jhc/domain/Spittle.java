package cn.jhc.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Spittle
 *
 */
@Entity
@Table(name="spittle")
public class Spittle implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@TableGenerator(
			name="spittle_gen",
			pkColumnValue="spittle_seq",
			initialValue=50
			)
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="spittle_gen")
	private long id;
	
	private String text;
	
	@Column(name="postedTime")
	private Date when;
	
	@ManyToOne
	@JoinColumn(name="spitter_id")
	private Spitter spitter = new Spitter();
	
	public Spittle() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
	}

	public Spitter getSpitter() {
		return spitter;
	}

	public void setSpitter(Spitter spitter) {
		this.spitter = spitter;
	}

	public long getId() {
		return id;
	}
   
}
