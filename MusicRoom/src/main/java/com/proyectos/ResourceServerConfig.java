package com.proyectos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private ResourceServerTokenServices tokenServices;

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceIds).tokenServices(tokenServices);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(new AuthException()).and().requestMatchers().and()
				.authorizeRequests().antMatchers("/v2/api-docs/**").authenticated()
				.antMatchers("/music-room/reporte-archivo/**").authenticated()
				.antMatchers("/music-room/usuario/").authenticated()
				.antMatchers("/music-room/usuario/consultarPorFiltros/**").authenticated()
				.antMatchers("/music-room/usuario/crearUsuario/**").authenticated()
				.antMatchers("/music-room/usuario/modificarUsuario/**").authenticated()
				.antMatchers("/music-room/usuario/eliminarUsuario/**").authenticated()
				.antMatchers("/music-room/ubicacion/").authenticated()
				.antMatchers("/music-room/ubicacion/consultarPorFiltros/**").authenticated()
				.antMatchers("/music-room/ubicacion/consultarPorTipo/**").authenticated()
				.antMatchers("/music-room/ubicacion/crearUbicacion/**").authenticated()
				.antMatchers("/music-room/ubicacion/modificarUbicacion/**").authenticated()
				.antMatchers("/music-room/ubicacion/eliminarUbicacion/**").authenticated()
				.antMatchers("/music-room/tercero/").authenticated()
				.antMatchers("/music-room/tercero/consultarTodos/**").authenticated()
				.antMatchers("/music-room/tercero/consultarPorFiltros/**").authenticated()
				.antMatchers("/music-room/tercero/crearTercero/**").authenticated()
				.antMatchers("/music-room/tercero/modificarTercero/**").authenticated()
				.antMatchers("/music-room/tercero/eliminarTercero/**").authenticated()
				.antMatchers("/music-room/sala/").authenticated()
				.antMatchers("/music-room/sala/consultarTodos/**").authenticated()
				.antMatchers("/music-room/sala/consultarPorFiltros/**").authenticated()
				.antMatchers("/music-room/sala/crearSala/**").authenticated()
				.antMatchers("/music-room/sala/modificarSala/**").authenticated()
				.antMatchers("/music-room/sala/eliminarSala/**").authenticated();
	}
}
