package com.tulipez.starter.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tz_actionSeries")
public class ActionSeries {

	@Id @GeneratedValue
    private Integer id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
    private Workspace workspace;
	
	private String defaultActionName;
	
	@OneToMany(mappedBy = "series", fetch = FetchType.LAZY)
    private Set<Action> actions = new HashSet<Action>();

	public Set<Action> getActions() {
		return actions;
	}
	
	public String getDefaultActionName() {
		return defaultActionName;
	}

	public void setDefaultActionName(String defaultActionName) {
		this.defaultActionName = defaultActionName;
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
		ActionSeries other = (ActionSeries) obj;
		return id.equals(other.id);
	}
}
