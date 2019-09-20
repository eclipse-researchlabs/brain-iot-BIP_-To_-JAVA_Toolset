#############################################################################################
#
# En este script, se crea una función (mejor.arma) cuya salida nos da los órdenes (p, q, P y Q)
# del mejor ARMA(p,q)x(P,Q)_s según uno de los criterios siguientes: AIC, AICC, BIC (a elegir).
# La estimación del modelo se realiza a través del método de máxima verosimilitud o del método de mínimos cuadrados condicionados (a elegir).
#
#
# Argumentos:
# x ==> objeto de la clase "time series" al cual se le quiere ajustar un ARMA
# ord.max ==> vector de longitud 4, cuyas componentes indican los órdenes máximos permitidos para p, q, P y Q
# incluir.media ==> "SI" o "NO" (en referencia a la inclusión o no de media/cte en el ARMA)
# metodo ==> método de estimación: por defecto utiliza máxima verosimilitud; otra posibilidad es "CSS" (mínimos cuadrados condicionados)
# criterio ==> criterio a seguir para la selección del modelo: "AIC", "AICC" o "BIC"
# dist.max.crit ==> la función dará los órdenes de todos los modelos ARMA cuya función criterio tenga un valor que diste del mínimo a lo sumo dist.max.crit unidades
#
#
# Salida: órdenes y valores de la función criterio para los ARMAs seleccionados
# 
#############################################################################################


mejor.arma <- function(x=x.ts, ord.max=c(3,3,2,2), incluir.media='SI', metodo=NULL, criterio='AIC', dist.max.crit=2)

	{

	# Chequeamos que no haya ningún problema con los argumentos de la función

		if (! is.ts(x)) stop('El argumento x debe ser un objeto de la clase ts')

		if ((criterio != 'AIC') & (criterio != 'AICC') & (criterio != 'BIC')) stop('El argumento criterio debe ser igual a: AIC, AICC o BIC')

		if (! is.null(metodo)) if (metodo != 'CSS') stop('El argumento metodo debe ser o bien nulo (NULL) o bien CSS')

		if (length(ord.max) != 4) stop('El argumento ord.max debe tener longitud 4')

		for (i in 1:4) if ((ord.max[i] != round(ord.max[i])) | (ord.max[i] < 0)) stop('Cada valor del argumento ord.max debe ser un número entero no negativo')

		if ((incluir.media != 'SI') & (incluir.media != 'NO')) stop('El argumento incluir.media debe ser igual a: SI o NO')

		if (dist.max.crit < 0) stop('El argumento dist.max.crit no puede tomar un valor negativo')


		T <- length(x)

	# Creamos una matriz que posteriormente contendrá todas las combinaciones de los órdenes considerados y los valores de la función criterio para los ARMAs correspondientes

		VALORES.CRITERIO <- matrix(0,(ord.max[1]+1)*(ord.max[2]+1)*(ord.max[3]+1)*(ord.max[4]+1), 5)

		if (incluir.media=='SI') incluir.media <- TRUE
		else incluir.media <- FALSE

	# La penalización que sobre la cantidad de parámetros imponen las funciones criterio AIC o BIC depende del factor definido abajo

		if (criterio=='AIC')  factor <- 2
		else if (criterio=='BIC')  factor <- log(T)

		fila <- 0

		for (p in 0:ord.max[1]) 
		for (q in 0:ord.max[2])
		for (P in 0:ord.max[3])
		for (Q in 0:ord.max[4])

			{
			ajuste <- arima(x=x, order=c(p,0,q), seasonal=list(order=c(P,0,Q), period=frequency(x)), include.mean=incluir.media, method=metodo, optim.control=list(maxit=500))

			# La penalización que sobre la cantidad de parámetros impone la función criterio AICC depende del factor definido abajo

				if (criterio=='AICC')  factor <- 2*T/(T-length(ajuste$coef)-2)

				criterio.ajuste <- -2*ajuste$loglik + factor*(length(ajuste$coef)+1)	

				fila <- fila +1

				VALORES.CRITERIO[fila, ] <- c(p, q, P, Q, criterio.ajuste)

		}

	# Obtenemos la distancia de cada valor de la función criterio a su valor mínimo

		DISTANCIAS.AL.MINIMO <- VALORES.CRITERIO[,5] - min(VALORES.CRITERIO[,5])

	# Creamos un vector con valores TRUE o FALSE dependiendo de si queremos que la fila correspondiente a cada componente del vector sea presentada en la salida o no, respectivamente

		FILAS.OK <- rep(FALSE,length=(ord.max[1]+1)*(ord.max[2]+1)*(ord.max[3]+1)*(ord.max[4]+1))

		FILAS.OK[ DISTANCIAS.AL.MINIMO<= dist.max.crit] <- TRUE

	# Cargamos únicamente los resultados (órdenes y valor de la función criterio) que queremos mostrar

		VALORES.CRITERIO.OK <- VALORES.CRITERIO[FILAS.OK,]

		if (!is.matrix(VALORES.CRITERIO.OK))  VALORES.CRITERIO.OK <- t(as.matrix(VALORES.CRITERIO.OK))

	# Ordenamos VALORES.CRITERIO.OK de forma descendente según el valor de la función criterio

		DISTANCIA.MAXIMA.OK <- VALORES.CRITERIO.OK[order(VALORES.CRITERIO.OK[,5]),]

		if (!is.matrix(DISTANCIA.MAXIMA.OK))  DISTANCIA.MAXIMA.OK <- t(as.matrix(DISTANCIA.MAXIMA.OK))


		DISTANCIA.MAXIMA.OK <- data.frame(DISTANCIA.MAXIMA.OK)


		if (criterio=='AIC') names(DISTANCIA.MAXIMA.OK) <- c('p', 'q', 'P', 'Q', 'AIC')

		else if (criterio=='AICC') names(DISTANCIA.MAXIMA.OK) <- c('p', 'q', 'P', 'Q', 'AICC')
	
		else names(DISTANCIA.MAXIMA.OK) <- c('p', 'q', 'P', 'Q', 'BIC')



		return(DISTANCIA.MAXIMA.OK)

	}