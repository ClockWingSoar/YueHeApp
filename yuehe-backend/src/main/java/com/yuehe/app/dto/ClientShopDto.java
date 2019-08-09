package com.yuehe.app.dto;

import com.yuehe.app.interfaces.Downloadable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of client.html,using when it
 * needs to join other tables to get cosmetic shop name by it's shop id, it's
 * different with the entity class {@link com.yuehe.app.entity.Client} which is
 * aim to symplify the data store volume
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientShopDTO implements Downloadable{
	private String id;
	private String name;
	private String cosmeticShopName;
	private int age;
	private String gender;
	private String symptom;

}
