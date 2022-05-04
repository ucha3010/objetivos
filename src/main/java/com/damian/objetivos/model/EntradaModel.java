package com.damian.objetivos.model;

import java.util.Date;

public class EntradaModel {

	private int id;
	private Date fecha;
	private String detalle;
	private CategoriaModel categoria;
	private SubcategoriaModel subcategoria;
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
	public CategoriaModel getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the subcategoria
	 */
	public SubcategoriaModel getSubcategoria() {
		return subcategoria;
	}
	/**
	 * @param subcategoria the subcategoria to set
	 */
	public void setSubcategoria(SubcategoriaModel subcategoria) {
		this.subcategoria = subcategoria;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EntradaModel [id=" + id + ", fecha=" + fecha + ", detalle=" + detalle + ", categoria=" + categoria
				+ "]";
	}

}
