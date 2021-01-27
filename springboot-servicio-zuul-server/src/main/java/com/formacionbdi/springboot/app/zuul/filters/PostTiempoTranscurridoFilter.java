package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter{
	//Calcula el tiempo transcurrido en una comunicación con un microservicio

	//Para mostrar en el log la ruta request que estamos enviando
	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		//Validación si se ejecuta (true) o no el filtro (false).
		return true;
	}

	@Override
	public Object run() throws ZuulException { 
		//La lógica del filtro		
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
	
		log.info("Entrando a post filter");
		
		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTranscurrido = tiempoFinal - tiempoInicio;
		
		log.info(String.format("Tiempo transcurrido en segundos %s seg.", tiempoTranscurrido.doubleValue()/1000.00));
		log.info(String.format("Tiempo transcurrido en milisegundos %s mseg.", tiempoTranscurrido));

		return null;
	}

	@Override
	public String filterType() {
		//El string siempre se ha de llamar "post"
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
