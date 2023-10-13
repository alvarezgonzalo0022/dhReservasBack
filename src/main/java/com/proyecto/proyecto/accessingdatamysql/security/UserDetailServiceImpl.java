package com.proyecto.proyecto.accessingdatamysql.security;

import com.proyecto.proyecto.accessingdatamysql.model.Usuario;
import com.proyecto.proyecto.accessingdatamysql.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.buscarUsuarioEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario con el email provisto no existe"));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(usuario.getRol().getNombre());
        grantedAuthorities.add(grantedAuthority);

        UserDetails userDetails = new UserDetailsImpl(usuario, grantedAuthorities);

        return userDetails;
    }
}
