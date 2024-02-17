package com.tulipez.starter.model;

import java.util.Objects;

import org.hibernate.annotations.NaturalId;

import io.vertx.core.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="StarterUser")
public class StarterUser {
	
	@Id @GeneratedValue
    private Integer id;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	@Column(unique=true)
	@NotNull
	private String sub;
	public String getSub() { return sub; }
	public void setSub(String sub) { this.sub = sub; }
	
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	private String given_name;
	public String getGiven_name() { return given_name; }
	public void setGiven_name(String given_name) { this.given_name = given_name; }
	
	private String family_name;
	public String getFamily_name() { return family_name; }
	public void setFamily_name(String family_name) { this.family_name = family_name; }
	
	@Column(length = 4000)
	private String picture;
	public String getPicture() { return picture; }
	public void setPicture(String picture) { this.picture = picture; }
	
	private String email;
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	private Boolean email_verified;
	public Boolean getEmail_verified() { return email_verified; }
	public void setEmail_verified(Boolean email_verified) { this.email_verified = email_verified; }

	private Boolean dark_mode;
	public Boolean getDark_mode() { return dark_mode; }
	public void setDark_mode(Boolean dark_mode) { this.dark_mode = dark_mode; }
	
	private String locale;
	public String getLocale() { return locale; }
	public void setLocale(String locale) { this.locale = locale; }
	
	public @Override int hashCode() {
		return Objects.hash(sub, name, given_name, family_name);
	}
	
	public @Override boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		StarterUser other = (StarterUser) obj;
		return id.equals(other.id);
	}
	
	//TODO externaliser/automatiser/generaliser
	public StarterUser updateFrom(JsonObject userSpecif) {
		if(userSpecif.containsKey("name")) this.setName(userSpecif.getString("name"));
		if(userSpecif.containsKey("given_name")) this.setGiven_name(userSpecif.getString("given_name"));
		if(userSpecif.containsKey("family_name")) this.setFamily_name(userSpecif.getString("family_name"));
		if(userSpecif.containsKey("picture")) this.setPicture(userSpecif.getString("picture"));
		if(userSpecif.containsKey("email")) this.setEmail(userSpecif.getString("email"));
		if(userSpecif.containsKey("email_verified")) this.setEmail_verified(userSpecif.getBoolean("email_verified"));
		if(userSpecif.containsKey("locale")) this.setLocale(userSpecif.getString("locale"));
		if(userSpecif.containsKey("dark_mode")) this.setDark_mode(userSpecif.getBoolean("dark_mode"));
		return this;
	}
	
}
