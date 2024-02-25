package com.tulipez.starter.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class StarterUser {
	
	@JsonIgnore
	@Id @GeneratedValue
    private Integer id;

	@Column(unique=true)
	@NotNull
	private String sub;
	
	private String name;
	
	private String given_name;
	
	private String family_name;
	
	@Column(length = 4000)
	private String picture;
	
	private String email;

	private Boolean email_verified;
	
	private String locale;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "workspace_id", referencedColumnName = "id", nullable=false)
    private Workspace workspace;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmail_verified() {
		return email_verified;
	}

	public void setEmail_verified(Boolean email_verified) {
		this.email_verified = email_verified;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

	public @Override int hashCode() {
		return Objects.hash(id);
	}
	
	public @Override boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		StarterUser other = (StarterUser) obj;
		return id.equals(other.id);
	}
	
//	//TODO externaliser/automatiser/generaliser
//	public StarterUser updateFrom(JsonObject userSpecif) throws JsonMappingException, JsonProcessingException {
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		ObjectReader objectReader = objectMapper.readerForUpdating(this);
//		return objectReader.readValue(userSpecif.encode());
//		
////		if(userSpecif.containsKey("name")) this.setName(userSpecif.getString("name"));
////		if(userSpecif.containsKey("given_name")) this.setGiven_name(userSpecif.getString("given_name"));
////		if(userSpecif.containsKey("family_name")) this.setFamily_name(userSpecif.getString("family_name"));
////		if(userSpecif.containsKey("picture")) this.setPicture(userSpecif.getString("picture"));
////		if(userSpecif.containsKey("email")) this.setEmail(userSpecif.getString("email"));
////		if(userSpecif.containsKey("email_verified")) this.setEmail_verified(userSpecif.getBoolean("email_verified"));
////		if(userSpecif.containsKey("locale")) this.setLocale(userSpecif.getString("locale"));
////		return this;
//	}
	
}
