# librerias
library(data.table)
library(lubridate)
library(tseries)
library(forecast)
library(seastests)
library(ggplot2)


# directorio de trabajo.
setwd("C:/Users/miguelbm/Workspace/BrainIoT/WaterInfraestructureExploration/WaterInfraestructureDataset")

# datos.
dt = fread("./extracted_data/telva_dataset.csv")

# se calcula el año, mes y dia a partir de date.
dt_date = as.data.table(matrix(data=as.numeric(unlist(strsplit(dt$date, "[-]"))), ncol=3, byrow=TRUE))
names(dt_date) = c("year", "month", "day")
dt = cbind(dt, dt_date)

# se filtran los 29 de Febrero.
dt = dt[!(month == 02 & day == 29), ]

# se parsean las fechas.
dt$date = as.Date(dt$date)

# se crea un objeto de la clase serie.
freq = 365
ts = ts(data=dt[["water_flow"]], start=c(1990, 1), frequency=freq)

# grafico de la serie water_flow.
# pdf("water_flow.pdf", onefile=FALSE, height=6, width=6, pointsize=12)
ggplot(dt, aes(x = date, y = water_flow)) + 
  geom_line(color = "#00AFBB", size = 1) +
  theme_minimal()
# dev.off()

# split en train y test.
last_year_train = 2014
train = window(x=ts, start=c(1990, 1), end=c(last_year_train, freq))
test = window(x=ts, start=c(2015, 1), end=end(ts))

# pdf("water_flow_train.pdf", onefile=FALSE, height=6, width=6, pointsize=10)
par(mfrow = c(2,1))
plot(train, cex=0.8, type='o', xlab='date', ylab="water_flow")
title("time series and fas")
acf(train, xlab='lag', lag.max=length(train)/4, main='')
# dev.off()

# contraste de tendencia.
adf.test(train)

# contraste de estacionaliddad.
isSeasonal(train)

# se diferencia la serie estacionalmente.
difftrain = diff(train, lag=freq)
isSeasonal(difftrain)

# pdf("water_flow_train.pdf", onefile=FALSE, height=6, width=6, pointsize=10)
par(mfrow=c(2,1))
plot(difftrain, cex=0.8, type='o', xlab='date', ylab="water_flow")
title("time series and fas")
acf(difftrain, xlab='lag',lag.max=length(difftrain)/4, main='')
# dev.off()

# se analiza el mejor modelo. 
arima.fit = auto.arima(y=train, max.p=6, max.q=6, max.P=2, max.Q=2, seasonal=isSeasonal(train), trace=TRUE) 

source('arma.signif.R')	
arima.fit = arma.signif(x=train, orden=c(4,3,0,0), metodo="CSS")

# predicciones a 1 retardo (actualizando la serie).
num_years_pred = dim(unique(dt[year > last_year_train, "year", with=FALSE]))[1]
n_ahead = num_years_pred*freq

arima.pred = numeric()
arima.se = numeric()
cil = numeric()
ciu = numeric()

for (i in 1:n_ahead) {
  arima.fit = arima(ts[1:(length(train) + i - 1)], order=c(4,0,3), optim.control=list(maxit=500))
  arima.pred[i] = predict(arima.fit, n.ahead=1)$pred
  arima.se[i] = predict(arima.fit, n.ahead=1)$se
  
  # intervalos de predicción.
  cil <- arima.pred[i] - 1.96*arima.se[i]
  ciu <- arima.pred[i] + 1.96*arima.se[i]

  print(i)
  
}

# analisis de residuos.
# independencia:
tsdiag(arima.fit, gof.lag=20)

# mu_a = 0.
t.test(arima.fit$resid, mu=0)

# normalidad.
jarque.bera.test(arima.fit$resid)



# grafico de predicciones.
plot(1:length(test), as.vector(test), type='o', xlab="lag",ylab="water flow", ylim=c(0, 60))
points(arima.pred, type='o', col=2)


# se calcula el rmse.
rmse = sqrt(mean((arima.pred - test)^2))


