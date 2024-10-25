package com.advocacia.converters;

import org.springframework.stereotype.Component;

import com.advocacia.dto.UsuarioDTO;
import com.advocacia.entities.Usuario;
import com.advocacia.services.Tipo_UsuarioService;

@Component
public class UsuarioDTOConverter {

    private final Tipo_UsuarioService tipoUsuarioService;

    public UsuarioDTOConverter(Tipo_UsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    public UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setTipoUsuario(usuario.getTipo_Usuario());
        return usuarioDTO;
    }

    public Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setTipo_Usuario(tipoUsuarioService.findByDescricao(usuarioDTO.getTipoUsuario().getDescricao()));
        return usuario;
    }
}
