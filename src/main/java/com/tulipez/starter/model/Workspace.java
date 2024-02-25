package com.tulipez.starter.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Workspace {

	@JsonIgnore
	@Id @GeneratedValue
    private Integer id;
	
	@JsonIgnore
	@OneToOne(mappedBy = "workspace", fetch = FetchType.EAGER)
    private StarterUser user;

	@OneToMany(mappedBy="workspace", fetch = FetchType.EAGER)
    private Set<Action> actions = new HashSet<Action>();

	private boolean dark_mode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void addAction(Action action) {
		this.actions.add(action);
	}
	
	public void removeAction(Action action) {
		this.actions.remove(action);
	}

	public boolean isDark_mode() {
		return dark_mode;
	}

	public void setDark_mode(boolean dark_mode) {
		this.dark_mode = dark_mode;
	}
	
}
