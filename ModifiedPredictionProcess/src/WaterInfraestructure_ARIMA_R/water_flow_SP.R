# librerias
library(data.table)
library(lubridate)
library(tseries)
library(forecast)
library(mgcv)
library(GGally)
library(ggplot2)


# directorio de trabajo.
setwd("C:/Users/miguelbm/Workspace/BrainIoT/WaterInfraestructureExploration/WaterInfraestructureDataset")

# datos.
dt = fread("./extracted_data/telva_dataset.csv")

# se calcula el año, mes y dia.
dt_date = as.data.table(matrix(data=as.numeric(unlist(strsplit(dt$date, "[-]"))), ncol=3, byrow=TRUE))
names(dt_date) = c("year", "month", "day")

dt = cbind(dt, dt_date)
dt = dt[!(month == 02 & day == 29), ]
dt$date = as.Date(dt$date)

# exploratory analysis.
ggpairs(dt[, list(water_flow, water_precipitation, water_height, water_volume, water_volume_variation)])

# add new variables.
dt[, water_flow_lag1 := shift(water_flow, n=1, fill=NA, type="lag")]
dt[, water_flow_lag2 := shift(water_flow, n=2, fill=NA, type="lag")]
dt[, water_flow_lag3 := shift(water_flow, n=3, fill=NA, type="lag")]
dt[, water_flow_lag4 := shift(water_flow, n=4, fill=NA, type="lag")]
dt[, water_flow_lag5 := shift(water_flow, n=5, fill=NA, type="lag")]

dt[, water_precipitation_lag1 := shift(water_precipitation, n=1, fill=NA, type="lag")]
dt[, water_precipitation_lag2 := shift(water_precipitation, n=2, fill=NA, type="lag")]
dt[, water_height_lag1 := shift(water_height, n=1, fill=NA, type="lag")]
dt[, water_height_lag2 := shift(water_height, n=2, fill=NA, type="lag")]

# split en train y test.
train = dt[year <= 2014]
test = dt[year > 2014]

# scale train sample.
train_dates = train[, c("date", "year", "month", "day"), with=FALSE]
train_vars = names(train)[! names(train) %in% names(train_dates)]

train_scale = train[, train_vars, with=FALSE]
train_mean = sapply(train[, train_vars, with=FALSE], mean, na.rm=TRUE)
train_sd = sapply(train[, train_vars, with=FALSE], sd, na.rm=TRUE)

scale_train = function(z) {
  scale(train_scale[[z]])
}
  
train_scale[, (train_vars) := lapply(train_vars, scale_train)]
 

# exploratory analysis.
ggpairs(train_scale[, list(water_flow, water_precipitation, water_height, water_volume, water_volume_variation)])

gam.form = as.formula(paste())

mod.fit = gam(water_flow ~ s(water_flow_lag1) + s(water_flow_lag2) + s(water_flow_lag3) + s(water_flow_lag4) + s(water_flow_lag5) +
                s(water_height_lag1) +  s(water_height_lag2) +
                s(water_precipitation_lag1) + s(water_precipitation_lag2), data=train_scale)
summary(mod.fit)

# scale test data.
test[, train_vars, with=FALSE]


# Predictions.



mod.pred = predict.gam(mod.fit, newdata=test[, list(water_flow_lag1, water_flow_lag2, water_flow_lag3, water_flow_lag4,
                                                    water_flow_lag5, water_height_lag1, water_precipitation_lag1)], type="response") 


plot(1:dim(test)[1], test$water_flow, type='o', xlab="lag",ylab="water flow", ylim=c(0, 60))
points(as.vector(mod.pred), type='o', col=2)

mod.res = as.vector(mod.pred) - test$water_flow

dtres = data.table(pred = as.vector(mod.pred), test = test$water_flow)


mse = mean((as.vector(mod.pred) - test$water_flow)^2)

mod.res = residuals(mod.fit)


ts = ts(data=mod.res, start=c(1990, 1),frequency=365)


# pdf("water_flow_train.pdf", onefile=FALSE, height=6, width=6, pointsize=10)
par(mfrow=c(2,1))
plot(ts, cex=0.8, type='o', xlab='date', ylab="water_flow")
title("time series and fas")
acf(ts, xlab='lag',lag.max=length(train)/4, main='')
# dev.off()

# contraste de tendencia.
adf.test(ts)


arima.fit = auto.arima(y=ts, max.p = 7, max.q = 7, max.P = 3, max.Q = 3, parallel=TRUE, num.cores=3, trace=TRUE) 

arima(x=ts, order=c(3,0,1), seasonal=list(order=c(0,0,0), period=365), include.mean=TRUE, optim.control=list(maxit=500))

source('arma.signif.R')	
arima.fit <- arma.signif(x=ts, orden=c(3,1,0,0), metodo="CSS")

arima.pred <- predict(arima.fit, n.ahead=3*365)



# predicciones a 1 retardo.
n_ahead = 3*365
arima.pred = numeric()

for (i in 1:n_ahead) {
  arima.fit <- arima(ts[1:(length(train) + i - 1)], order=c(3,0,1), seasonal=list(order=c(0,0,0)), method="CSS", optim.control=list(maxit=500))
  arima.pred[i] <- predict(arima.fit, n.ahead=1)$pred
  arima.pred[i] = arima.pred[i] + as.vector(mod.pred)[i]
  print(i)
  
}

plot(1:dim(test)[1], as.vector(test$water_flow), type='o', xlab="lag",ylab="water flow", ylim=c(0, 60))
points(arima.pred, type='o', col=2)
mse = mean((arima.pred - as.vector(test$water_flow))^2)


