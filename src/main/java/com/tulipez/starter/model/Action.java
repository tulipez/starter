package com.tulipez.starter.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tz_action")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Action {

	@Id @GeneratedValue
    private Integer id;

	private String name;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
    private Workspace workspace;
	
	public Action() {}
	
	public Action(Workspace workspace) {
		this.workspace = workspace;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		Action other = (Action) obj;
		return id.equals(other.id);
	}
	
}
