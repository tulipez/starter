package com.tulipez.starter.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="StarterUser")
public class StarterUser {

	@Id @GeneratedValue
    private Integer id;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

//	name = "" as string;
//    given_name = "" as string;
//    family_name  = "" as string;
//    picture = "" as string;
//    email = "" as string;
//    email_verified = false as boolean;
//	locale = "" as string;
	
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	private String givenName;
	public String getGivenName() { return givenName; }
	public void setGivenName(String givenName) { this.givenName = givenName; }
	
	private String familyName;
	public String getFamilyName() { return familyName; }
	public void setFamilyName(String familyName) { this.familyName = familyName; }
	
	private String picture;
	public String getPicture() { return picture; }
	public void setPicture(String picture) { this.picture = picture; }
	
	private String email;
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	private boolean emailVerified;
	public boolean getEmailVerified() { return emailVerified; }
	public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
	
	private String locale;
	public String getLocale() { return locale; }
	public void setLocale(String locale) { this.locale = locale; }
	
	public @Override int hashCode() {
		return Objects.hash(name, givenName, familyName);
	}
	
	public @Override boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		StarterUser other = (StarterUser) obj;
		return id.equals(other.id);
	}
}
