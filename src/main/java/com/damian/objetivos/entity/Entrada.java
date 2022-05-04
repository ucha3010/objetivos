package com.damian.objetivos.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="entrada")
public class Entrada {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "detalle", columnDefinition = "TEXT")
	private String detalle;
	
	@Column(name = "categoria")
	private int categoria;
	
	@Column(name = "subcategoria")
	private int subcategoria;
	
	public Entrada() {
		
	}

	public Entrada(int id, Date fecha, String detalle, int categoria, int subcategoria) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.detalle = detalle;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
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
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the detalle
	 */
	public String getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	/**
	 * @return the categoria
	 */
	public int getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the subcategoria
	 */
	public int getSubcategoria() {
		return subcategoria;
	}

	/**
	 * @param subcategoria the subcategoria to set
	 */
	public void setSubcategoria(int subcategoria) {
		this.subcategoria = subcategoria;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Entrada [id=" + id + ", fecha=" + fecha + ", detalle=" + detalle + ", categoria=" + categoria
				+ ", subcategoria=" + subcategoria + "]";
	}
	
}
