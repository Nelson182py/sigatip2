/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;
import py.com.sigati.entities.Rol;
import py.com.sigati.entities.Usuario;
import py.com.sigati.util.PasswordUtility;
import py.com.sigati.ejb.RolEJB;
/**
 *
 * @author Nelson182py
 */
@ManagedBean
@SessionScoped
public class UsuarioBean extends AbstractBean implements Serializable {
	@EJB
	private UsuarioBean usuarioEJB;
	@EJB
	private RolBean rolEJB;
	private List<Usuario> listaUsuarios = new ArrayList<>();
	private Usuario usuarioSeleccionado;
	private Rol rolSeleccionado;
	private List<Rol> listaRoles = new ArrayList<>();
	private boolean editando;

	@PostConstruct
	public void init() {
		listaUsuarios = usuarioEJB.findAll();
		listaRoles = rolEJB.findAll();
		usuarioSeleccionado = new Usuario();
	}

	@Override
	public void guardar() {
		try {
			usuarioSeleccionado.setIdRol(rolSeleccionado);
			//usuarioSeleccionado.setPassword(DigestUtils.md5Hex("12345").hashCode() + "");
			usuarioSeleccionado.setPassword(PasswordUtility.getSaltedHash("12345"));
			usuarioEJB.create(usuarioSeleccionado);
			infoMessage("Se guardó correctamente.");
			listaUsuarios = usuarioEJB.findAll();
			resetearValores();
			PrimeFaces.current().executeScript("PF('wbUsuarios').hide()");
		} catch (Exception e) {
			errorMessage("Se produjo un error.");
		}


	}

	public void agregarUsuario() {
		resetearValores();
		listaUsuarios = usuarioEJB.findAll();
		listaRoles = rolEJB.findAll();
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public Rol getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(Rol rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	public List<Rol> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	@Override
	public void resetearValores() {
		usuarioSeleccionado = new Usuario();
		rolSeleccionado = null;
		editando = false;
	}

	@Override
	public void inicializarListas() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void antesActualizar() {
		listaUsuarios = usuarioEJB.findAll();
		listaRoles = rolEJB.findAll();
		rolSeleccionado = usuarioSeleccionado.getIdRol();
		editando = true;
	}

	@Override
	public void actualizar() {
		try {
			usuarioSeleccionado.setIdRol(rolSeleccionado);
			//usuarioSeleccionado.setPassword("12345");

 			usuarioEJB.edit(usuarioSeleccionado);
			infoMessage("Se actualizó correctamente.");
			listaUsuarios = usuarioEJB.findAll();
			resetearValores();
			PrimeFaces.current().executeScript("PF('wbUsuarios').hide()");
		} catch (Exception e) {
			errorMessage("Se produjo un error.");
		}
	}

	@Override
	public void eliminar() {
		try {
			usuarioEJB.remove(usuarioSeleccionado);
			infoMessage("Eliminado correctamente");
			listaUsuarios = usuarioEJB.findAll();
		} catch (Exception e) {
			errorMessage("No se pudo eliminar el registro");
		}
	}

    private List<Usuario> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void create(Usuario usuarioSeleccionado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void remove(Usuario usuarioSeleccionado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void edit(Usuario usuarioSeleccionado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
