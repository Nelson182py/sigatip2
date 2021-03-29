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
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import py.com.sigati.ejb.AreaEJB;
import py.com.sigati.ejb.PersonaEJB;
import py.com.sigati.entities.Rol;
import py.com.sigati.entities.Usuario;
import py.com.sigati.util.PasswordUtility;
import py.com.sigati.ejb.RolEJB;
import py.com.sigati.ejb.UsuarioEJB;
import py.com.sigati.entities.Area;
import py.com.sigati.entities.Persona;
import py.com.sigati.entities.RolPermiso;
/**
 *
 * @author Nelson182py
 */
@ManagedBean
@SessionScoped
public class UsuarioBean extends AbstractBean implements Serializable {
	@EJB
	private UsuarioEJB usuarioEJB;
	@EJB
	private RolEJB rolEJB;
        @EJB
	private PersonaEJB personaEJB;     
        @EJB
	private AreaEJB areaEJB;
        
	private List<Usuario> listaUsuarios = new ArrayList<>();
	private Usuario usuarioSeleccionado;
	private Rol rolSeleccionado;
	private List<Rol> listaRoles = new ArrayList<>();
        private Persona personaSeleccionada;
    	private List<Persona> listaPersonas = new ArrayList<>();
        private Area areaSeleccionada;
        private List<Area> listaAreas = new ArrayList<>();
        private boolean editando;
        private String alta = "alta";
        private String baja = "baja";
        private String modificacion = "modificacion";
        private String completo = "completo"; 
        private String ninguno = "ninguno";
        private String informes = "descarga"; 
    
	@PostConstruct
	public void init() {
		listaUsuarios = usuarioEJB.findAll();
		listaRoles = rolEJB.findAll();
		listaPersonas = personaEJB.findAll();
                listaAreas = areaEJB.findAll();
                usuarioSeleccionado = new Usuario();
	}

	@Override
	public void guardar() {
		try {
			usuarioSeleccionado.setIdRol(rolSeleccionado);
                        usuarioSeleccionado.setIdArea(areaSeleccionada);
                        usuarioSeleccionado.setIdPersona(personaSeleccionada);
                        //usuarioSeleccionado.setPassword(DigestUtils.md5Hex("12345").hashCode() + "");
			usuarioSeleccionado.setContrasenha(PasswordUtility.getSaltedHash("12345"));
                        usuarioEJB.create(usuarioSeleccionado);
			infoMessage("Se guardó correctamente.");
			listaUsuarios = usuarioEJB.findAll();
			resetearValores();
			PrimeFaces.current().executeScript("PF('wbUsuarios').hide()");
		} catch (Exception e) {
			errorMessage("Se produjo un error."+e);
		}


	}

	public void agregarUsuario() {
		resetearValores();
		listaUsuarios = usuarioEJB.findAll();
		listaRoles = rolEJB.findAll();
                listaPersonas = personaEJB.findAll();
                listaAreas = areaEJB.findAll();
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
                listaPersonas = personaEJB.findAll();
                listaAreas = areaEJB.findAll();
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
        public List<Persona> getListaPersonas() {
        return listaPersonas;
    }
        
    // Puede acceder modificacion y total
    public boolean mostrarNuevo(){        
        Usuario u =  loginBean.getUsuarioLogueado();
        Rol r = u.getIdRol();
        List<RolPermiso> permisos = r.getRolPermisoList();
        
        for(RolPermiso p:permisos){                 
            if (p != null){
                if( p.getIdPermiso().getDescripcion().equals(alta) ||
                    p.getIdPermiso().getDescripcion().equals(alta))
                return true;
            }            
        }
        return false;
    }

    public void setListaPersonas(List<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public List<Area> getListaAreas() {
        return listaAreas;
    }

    public void setListaAreas(List<Area> listaAreas) {
        this.listaAreas = listaAreas;
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

    public Persona getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public Area getAreaSeleccionada() {
        return areaSeleccionada;
    }

    public void setAreaSeleccionada(Area areaSeleccionada) {
        this.areaSeleccionada = areaSeleccionada;
    }

}
