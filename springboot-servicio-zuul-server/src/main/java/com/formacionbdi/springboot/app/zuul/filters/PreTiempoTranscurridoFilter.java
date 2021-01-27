package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreTiempoTranscurridoFilter extends ZuulFilter{
	//Calcula el tiempo transcurrido en una comunicación con un microservicio

	//Para mostrar en el log la ruta request que estamos enviando
	private static Logger log = LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		//Validación si se ejecuta (true) o no el filtro (false).
		return true;
	}

	@Override
	public Object run() throws ZuulException { 
		//La lógica del filtro		
		//Se pasan datos al objeto request
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
	
		log.info(String.format("%s request enrutado a %s", request.getMethod(), request.getRequestURL().toString()));
				
		Long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		return null;
	}

	@Override
	public String filterType() {
		//El string siempre se ha de llamar "pre"
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
