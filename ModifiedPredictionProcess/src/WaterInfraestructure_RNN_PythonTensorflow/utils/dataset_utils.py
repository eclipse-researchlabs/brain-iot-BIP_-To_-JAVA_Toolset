# Copyright 2019 Siotic Spain, S.L.


import os
import pandas
from pandas import DataFrame
from pandas import concat
from sklearn.externals import joblib
from matplotlib import pyplot


def create_dir_if_needed(path):
    if not os.path.exists(path):
        os.makedirs(path)


def read_dataset_from(input_path):
    df = pandas.read_csv(input_path)
    return df


# convert series to supervised learning valid dataset
def series_to_supervised(data, n_in=1, n_out=1, dropnan=True):
    n_vars = 1 if type(data) is list else data.shape[1]
    df = DataFrame(data)
    cols, names = list(), list()
    # input sequence (t-n, ... t-1)
    for i in range(n_in, 0, -1):
        cols.append(df.shift(i))
        names += [('var%d(t-%d)' % (j + 1, i)) for j in range(n_vars)]
    # forecast sequence (t, t+1, ... t+n)
    for i in range(0, n_out):
        cols.append(df.shift(-i))
        if i == 0:
            names += [('var%d(t)' % (j + 1)) for j in range(n_vars)]
        else:
            names += [('var%d(t+%d)' % (j + 1, i)) for j in range(n_vars)]
    # put it all together
    agg = concat(cols, axis=1)
    agg.columns = names
    # drop rows with NaN values
    if dropnan:
        agg.dropna(inplace=True)
    return agg


def save_scaler_to(scaler, path):
    joblib.dump(scaler, path)


def load_scaler_from(path):
    return joblib.load(path)


def load_water_output_dataset_from(inputs_configuration, enable_visualization = False):
    # load dataframe from CVS preprocessed data
    input_dataset = read_dataset_from(inputs_configuration['preprocessed_inputs_filepath'])

    # small additional cleaning/preparation for dataset
    input_dataset = input_dataset.drop('water_volume_variation', 1)
    input_dataset = input_dataset.drop('water_volume', 1)
    input_dataset = input_dataset.drop('date', 1)
    input_dataset = input_dataset.drop(0, 0)

    if enable_visualization:
        # plot each column
        groups = [0, 1, 2]
        i = 1
        for group in groups:
            pyplot.subplot(len(groups), 1, i)
            pyplot.plot(input_dataset.values[:, group])
            pyplot.title(input_dataset.columns[group], y=0.9, loc='right')
            i += 1
        pyplot.show()

    # final preparation: the "output" column is placed as first column
    cols = input_dataset.columns.tolist()
    cols.pop(cols.index('water_flow'))
    cols = ['water_flow'] + cols
    input_dataset = input_dataset[cols]
    return input_dataset


def reframe_output_dataset_for_rnn(inputs_configuration, input_dataset_scaled):
    number_of_timesteps = inputs_configuration['number_of_timesteps']
    reframed = series_to_supervised(input_dataset_scaled, number_of_timesteps, 1)
    # drop all the "t + 1" columns we don t want
    index_columns_to_drop = list(range(reframed.shape[1] - inputs_configuration['number_of_features_for_learning'] + inputs_configuration['number_of_features_to_predict'], reframed.shape[1]))
    reframed.drop(reframed.columns[index_columns_to_drop], axis=1, inplace=True)
    return reframed


def split_dataset_in_inputs_and_outputs(inputs_configuration, data):
    number_of_timesteps = inputs_configuration['number_of_timesteps']
    number_of_features_for_learning = inputs_configuration['number_of_features_for_learning']
    number_of_features_to_predict = inputs_configuration['number_of_features_to_predict']
    number_of_observations_for_learning = number_of_timesteps * number_of_features_for_learning
    # split into input and outputs (remember the last column is our t+1 output)
    data_X, data_y = data[:, :number_of_observations_for_learning], data[:, -number_of_features_to_predict:]
    # reshape input to be 3D [samples, timesteps, features]
    data_X = data_X.reshape((data_X.shape[0], number_of_timesteps, number_of_features_for_learning))
    return data_X, data_y
