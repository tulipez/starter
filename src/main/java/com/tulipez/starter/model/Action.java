package com.tulipez.starter.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Action {

	@Id @GeneratedValue
    private Integer id;
	
	private String name;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="workspace_id", nullable=false)
    private Workspace workspace;
	
	public Action() {}
	
	public Action(Workspace workspace) {
		this.workspace = workspace;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
