package dao;

import modelo.Usuario;

public interface UsuarioDAO extends CRUD<Usuario> {
    Usuario login(Usuario us);
}
