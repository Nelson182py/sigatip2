/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.bean;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.management.Query;
import py.com.sigati.ejb.UsuarioEJB;
import py.com.sigati.entities.Usuario;
import py.com.sigati.util.PasswordUtility;

/**
 *
 * @author Nelson182py
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private Usuario usuarioLogueado;    
    private final Query query = new Query();

    @EJB
    UsuarioEJB usuarioEJB;

    public String loginControl() throws Exception {

        usuarioLogueado = usuarioEJB.obtenerUsuario(username);
        if (usuarioLogueado != null) {
           // if (username.equals(usuarioLogueado.()) && PasswordUtility.check(password, usuarioLogueado.getPassword())) {
             //   System.out.println("ingresado al sistema ");
                return "paginaPrincipal";

            //}
        }
                
        if (username.trim().equals(password.trim())) {
            System.out.println("ingresado al sistema ");
            return "paginaPrincipal";
        }

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error Iniciar Sesión",
                "Usuario o Contraseña Incorrectos: "));

//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
        return "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }
    
 

}
