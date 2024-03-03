package com.tulipez.starter.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tz_workspace")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Workspace {

	@Id @GeneratedValue
    private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
    private StarterUser user;

	@OneToMany(mappedBy = "workspace", fetch = FetchType.EAGER)
    private Set<Action> actions = new HashSet<Action>();

	private boolean dark_mode;
	
	public Workspace() {
	}
	
	public Workspace(StarterUser user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public StarterUser getUser() {
		return user;
	}

	public void setUser(StarterUser user) {
		this.user = user;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public boolean isDark_mode() {
		return dark_mode;
	}

	public void setDark_mode(boolean dark_mode) {
		this.dark_mode = dark_mode;
	}
	
	public @Override int hashCode() {
		return Objects.hash(id);
	}
	
	public @Override boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Workspace other = (Workspace) obj;
		return id.equals(other.id);
	}
}
