# Copyright 2019 Siotic Spain, S.L.


import time
import matplotlib.pyplot as plt
import keras
from sklearn.metrics import mean_squared_error
from math import sqrt
from numpy import concatenate


def plot_results(history):
    loss = history.history['loss']
    val_loss = history.history['val_loss']

    epochs = range(1, len(loss) + 1)

    plt.figure()

    plt.plot(epochs, loss, 'bo', label='Training loss')
    plt.plot(epochs, val_loss, 'b', label='Validation loss')
    plt.title('Training and validation loss')
    plt.legend()

    plt.show()


def plot_history(history, name):
    plt.figure()

    plt.plot(history.history['loss'], label='train')
    plt.plot(history.history['val_loss'], label='test')
    plt.title('Training and validation loss for: ' + name)
    plt.legend()
    plt.show()


def calculate_RMSE(yhat, test_X, test_y, scaler, number_of_features_for_training, number_of_features_to_predict):
    test_X = test_X.reshape((test_X.shape[0], test_X.shape[1] * test_X.shape[2]))
    # invert scaling for forecast
    inv_yhat = concatenate((yhat, test_X[:, -(number_of_features_for_training - number_of_features_to_predict):]),
                           axis=1)
    inv_yhat = scaler.inverse_transform(inv_yhat)
    inv_yhat = inv_yhat[:, 0]
    # invert scaling for actual
    test_y = test_y.reshape((len(test_y), number_of_features_to_predict))
    inv_y = concatenate((test_y, test_X[:, -(number_of_features_for_training - number_of_features_to_predict):]),
                        axis=1)
    inv_y = scaler.inverse_transform(inv_y)
    inv_y = inv_y[:, 0]
    # calculate RMSE
    return sqrt(mean_squared_error(inv_y, inv_yhat))


def build_tf_tensorboard_callbacks():
    tf_logs_path = './temp/tensorflow_logs'
    return [
        keras.callbacks.TensorBoard(
            log_dir=tf_logs_path + '/' + time.strftime("%Y-%m-%d_%H:%M"),
            histogram_freq=5,
            write_graph=True
        )
    ]
