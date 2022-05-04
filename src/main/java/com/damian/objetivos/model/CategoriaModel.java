package com.damian.objetivos.model;

import java.util.List;

public class CategoriaModel {

	private int id;
	private String name;
	private List<SubcategoriaModel> subcategoriaList;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the subcategoriaList
	 */
	public List<SubcategoriaModel> getSubcategoriaList() {
		return subcategoriaList;
	}
	/**
	 * @param subcategoriaList the subcategoriaList to set
	 */
	public void setSubcategoriaList(List<SubcategoriaModel> subcategoriaList) {
		this.subcategoriaList = subcategoriaList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CategoriaModel [id=" + id + ", name=" + name + ", subcategoriaList=" + subcategoriaList + "]";
	}

}
