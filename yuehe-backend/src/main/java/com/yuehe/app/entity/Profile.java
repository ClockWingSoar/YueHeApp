package com.yuehe.app.entity;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Profile entity. @author Soveran Zhong
 */
@Entity
@Table(name="profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile implements Serializable{
	
	//Fields
	private static final long serialVersionUID = -8255769962125087079L;

	@Id
	@Column(name="sale_id")
	private String saleId;
	@Column(name="rest_card_amount")
	private int restCardAmount;
	@Column(name="create_profile_date")
	private Date createProfileDate;
	private String description;

	public static Comparator<Profile> idComparator = Comparator.comparing(Profile::getSaleId);
}
