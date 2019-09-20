#############################################################################################
#
# En este script, se crea una función (arma.signif) cuya salida nos da el
# modelo ajustado con todos sus parámetros significativamente distintos de cero (al 5%)
#
# Argumentos:
# x ==> objeto de la clase "time series" al cual se le quiere ajustar un ARMA con todos sus parámetros significativos
# orden ==> órdenes (p,q,P,Q) del ARMA a ajustar
# metodo ==> método de estimación: por defecto utiliza máxima verosimilitud; otra posibilidad es "CSS" (mínimos cuadrados condicionados)
#
#
# Salida: 
# ajuste del modelo con todos sus parámetros significativos
# 
#############################################################################################

arma.signif <- function(x=x.ts, orden=c(3,3,2,2), metodo=NULL)

	{

	# Chequeamos que no haya ningún problema con los argumentos de la función

		if (! is.ts(x)) stop('El argumento x debe ser un objeto de la clase ts')

		if (! is.null(metodo)) if (metodo != 'CSS') stop('El argumento metodo debe o bien nulo (NULL) o bien CSS')

		if (length(orden) != 4) stop('El argumento orden debe tener longitud 4')

		for (i in 1:4) if ((orden[i] != round(orden[i])) | (orden[i] < 0)) stop('Cada valor del argumento orden debe ser un número entero no negativo')

		
		p <- orden[1]

		q <- orden[2]

		P <- orden[3]

		Q <- orden[4]


		parametros.fijos <- rep(NA, sum(orden) + 1)

		num.parametros.nulos <- 0

		cat('Cantidad de parámetros nulos: ', num.parametros.nulos, '\n')

		incluir.media <- TRUE

		eliminar <- TRUE

		while (eliminar) {
  
			ajuste <- arima(x=x, order=c(p,0,q), seasonal=list(order=c(P,0,Q), period=frequency(x)), include.mean=incluir.media, fixed=parametros.fijos, method=metodo, optim.control=list(maxit=500))

			test <- abs(ajuste$coef[ajuste$coef != 0]) / (1.96*sqrt(diag(ajuste$var.coef)))

			candidato <- order(test)[1]

			if (test[candidato] >= 1) eliminar <- FALSE

			else {

				num.parametros.nulos <- num.parametros.nulos + 1

				cat('Cantidad de parámetros nulos: ', num.parametros.nulos, '; ', names(test[candidato]), '\n')

				parametros.fijos[ajuste$coef != 0][candidato]  <- -1000

				if (order(parametros.fijos)[1]==p)  {parametros.fijos <- parametros.fijos[-p]
					p <- p-1
				}

                else if (order(parametros.fijos)[1]==p+q)  {parametros.fijos <- parametros.fijos[-(p+q)]
					q <- q-1
				}

                else if (order(parametros.fijos)[1]==p+q+P)  {parametros.fijos <- parametros.fijos[-(p+q+P)]
					P <- P-1
				}

                else if (order(parametros.fijos)[1]==p+q+P+Q)  {parametros.fijos <- parametros.fijos[-(p+q+P+Q)]
                    Q <- Q-1
                }

                else if (order(parametros.fijos)[1]==p+q+P+Q+1)  {parametros.fijos <- parametros.fijos[-(p+q+P+Q+1)]
                    incluir.media <- FALSE
                }

                else parametros.fijos[ajuste$coef != 0][candidato]  <- 0


            }
   
		}

	return(ajuste)

	}