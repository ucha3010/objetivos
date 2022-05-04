package com.damian.objetivos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subcategoria")
public class Subcategoria {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "idCategoria")
	private int idCategoria;

	@Column(name = "descripcion", length = 200)
	private String descripcion;
	
	public Subcategoria() {
		
	}

	public Subcategoria(int id, String name, int idCategoria, String descripcion) {
		super();
		this.id = id;
		this.name = name;
		this.idCategoria = idCategoria;
		this.descripcion = descripcion;
	}

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
	 * @return the idCategoria
	 */
	public int getIdCategoria() {
		return idCategoria;
	}

	/**
	 * @param idCategoria the idCategoria to set
	 */
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}


	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Subcategoria [id=" + id + ", name=" + name + ", idCategoria=" + idCategoria + ", descripcion="
				+ descripcion + "]";
	}


}
