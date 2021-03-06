#############################################################################################
#
# En este script, se crea una funci�n (mejor.arma) cuya salida nos da los �rdenes (p, q, P y Q)
# del mejor ARMA(p,q)x(P,Q)_s seg�n uno de los criterios siguientes: AIC, AICC, BIC (a elegir).
# La estimaci�n del modelo se realiza a trav�s del m�todo de m�xima verosimilitud o del m�todo de m�nimos cuadrados condicionados (a elegir).
#
#
# Argumentos:
# x ==> objeto de la clase "time series" al cual se le quiere ajustar un ARMA
# ord.max ==> vector de longitud 4, cuyas componentes indican los �rdenes m�ximos permitidos para p, q, P y Q
# incluir.media ==> "SI" o "NO" (en referencia a la inclusi�n o no de media/cte en el ARMA)
# metodo ==> m�todo de estimaci�n: por defecto utiliza m�xima verosimilitud; otra posibilidad es "CSS" (m�nimos cuadrados condicionados)
# criterio ==> criterio a seguir para la selecci�n del modelo: "AIC", "AICC" o "BIC"
# dist.max.crit ==> la funci�n dar� los �rdenes de todos los modelos ARMA cuya funci�n criterio tenga un valor que diste del m�nimo a lo sumo dist.max.crit unidades
#
#
# Salida: �rdenes y valores de la funci�n criterio para los ARMAs seleccionados
# 
#############################################################################################


mejor.arma <- function(x=x.ts, ord.max=c(3,3,2,2), incluir.media='SI', metodo=NULL, criterio='AIC', dist.max.crit=2)

	{

	# Chequeamos que no haya ning�n problema con los argumentos de la funci�n

		if (! is.ts(x)) stop('El argumento x debe ser un objeto de la clase ts')

		if ((criterio != 'AIC') & (criterio != 'AICC') & (criterio != 'BIC')) stop('El argumento criterio debe ser igual a: AIC, AICC o BIC')

		if (! is.null(metodo)) if (metodo != 'CSS') stop('El argumento metodo debe ser o bien nulo (NULL) o bien CSS')

		if (length(ord.max) != 4) stop('El argumento ord.max debe tener longitud 4')

		for (i in 1:4) if ((ord.max[i] != round(ord.max[i])) | (ord.max[i] < 0)) stop('Cada valor del argumento ord.max debe ser un n�mero entero no negativo')

		if ((incluir.media != 'SI') & (incluir.media != 'NO')) stop('El argumento incluir.media debe ser igual a: SI o NO')

		if (dist.max.crit < 0) stop('El argumento dist.max.crit no puede tomar un valor negativo')


		T <- length(x)

	# Creamos una matriz que posteriormente contendr� todas las combinaciones de los �rdenes considerados y los valores de la funci�n criterio para los ARMAs correspondientes

		VALORES.CRITERIO <- matrix(0,(ord.max[1]+1)*(ord.max[2]+1)*(ord.max[3]+1)*(ord.max[4]+1), 5)

		if (incluir.media=='SI') incluir.media <- TRUE
		else incluir.media <- FALSE

	# La penalizaci�n que sobre la cantidad de par�metros imponen las funciones criterio AIC o BIC depende del factor definido abajo

		if (criterio=='AIC')  factor <- 2
		else if (criterio=='BIC')  factor <- log(T)

		fila <- 0

		for (p in 0:ord.max[1]) 
		for (q in 0:ord.max[2])
		for (P in 0:ord.max[3])
		for (Q in 0:ord.max[4])

			{
			ajuste <- arima(x=x, order=c(p,0,q), seasonal=list(order=c(P,0,Q), period=frequency(x)), include.mean=incluir.media, method=metodo, optim.control=list(maxit=500))

			# La penalizaci�n que sobre la cantidad de par�metros impone la funci�n criterio AICC depende del factor definido abajo

				if (criterio=='AICC')  factor <- 2*T/(T-length(ajuste$coef)-2)

				criterio.ajuste <- -2*ajuste$loglik + factor*(length(ajuste$coef)+1)	

				fila <- fila +1

				VALORES.CRITERIO[fila, ] <- c(p, q, P, Q, criterio.ajuste)

		}

	# Obtenemos la distancia de cada valor de la funci�n criterio a su valor m�nimo

		DISTANCIAS.AL.MINIMO <- VALORES.CRITERIO[,5] - min(VALORES.CRITERIO[,5])

	# Creamos un vector con valores TRUE o FALSE dependiendo de si queremos que la fila correspondiente a cada componente del vector sea presentada en la salida o no, respectivamente

		FILAS.OK <- rep(FALSE,length=(ord.max[1]+1)*(ord.max[2]+1)*(ord.max[3]+1)*(ord.max[4]+1))

		FILAS.OK[ DISTANCIAS.AL.MINIMO<= dist.max.crit] <- TRUE

	# Cargamos �nicamente los resultados (�rdenes y valor de la funci�n criterio) que queremos mostrar

		VALORES.CRITERIO.OK <- VALORES.CRITERIO[FILAS.OK,]

		if (!is.matrix(VALORES.CRITERIO.OK))  VALORES.CRITERIO.OK <- t(as.matrix(VALORES.CRITERIO.OK))

	# Ordenamos VALORES.CRITERIO.OK de forma descendente seg�n el valor de la funci�n criterio

		DISTANCIA.MAXIMA.OK <- VALORES.CRITERIO.OK[order(VALORES.CRITERIO.OK[,5]),]

		if (!is.matrix(DISTANCIA.MAXIMA.OK))  DISTANCIA.MAXIMA.OK <- t(as.matrix(DISTANCIA.MAXIMA.OK))


		DISTANCIA.MAXIMA.OK <- data.frame(DISTANCIA.MAXIMA.OK)


		if (criterio=='AIC') names(DISTANCIA.MAXIMA.OK) <- c('p', 'q', 'P', 'Q', 'AIC')

		else if (criterio=='AICC') names(DISTANCIA.MAXIMA.OK) <- c('p', 'q', 'P', 'Q', 'AICC')
	
		else names(DISTANCIA.MAXIMA.OK) <- c('p', 'q', 'P', 'Q', 'BIC')



		return(DISTANCIA.MAXIMA.OK)

	}