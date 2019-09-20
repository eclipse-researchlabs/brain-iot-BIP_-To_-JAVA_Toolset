# Copyright 2019 Siotic Spain, S.L.


import numpy as np
import datetime


def generator(data, lookback, delay, min_index, max_index,
              shuffle=False, batch_size=128, step=6):
    """
    :param data: the original array of floating-point data. Should be normalized. Output column must be index 0.
    :param lookback: number of timesteps to go back through the input data.
    :param delay: number of time steps in go in the future for the target.
    :param min_index: offset, start of subset of data array.
    :param max_index: end of subset of data array.
    :param shuffle: whether to shuffle samples or draw them in chronological order.
    :param batch_size: number of samples per batch.
    :param step: size of period to sample data, in number of steps.
    :return: a tuple (samples, targets) where samples is one batch of input data and targets is the corresponding array of target temperatures.
    """
    if max_index is None:
        max_index = len(data) - delay - 1
    i = min_index + lookback
    while 1:
        if shuffle:
            rows = np.random.randint(
                min_index + lookback, max_index, size=batch_size)
        else:
            if i + batch_size >= max_index:
                i = min_index + lookback
            rows = np.arange(i, min(i + batch_size, max_index))
            i += len(rows)

        samples = np.zeros((len(rows),
                           lookback // step,
                           data.shape[-1]))
        targets = np.zeros((len(rows),))
        for j, row in enumerate(rows):
            indices = range(rows[j] - lookback, rows[j], step)
            samples[j] = data[indices]
            targets[j] = data[rows[j] + delay][0]
        yield samples, targets


def build_test_generator(float_data, inputs_configuration):
    number_lookback_timesteps = inputs_configuration['lstm_parameters']['number_lookback_timesteps']
    size_of_sampling_in_steps = inputs_configuration['lstm_parameters']['size_of_sampling_in_steps']
    number_of_steps_to_predict = inputs_configuration['lstm_parameters']['number_of_steps_to_predict']
    test_offset = inputs_configuration['split_parameters']['testing_offset']

    test_gen = generator(float_data, lookback=number_lookback_timesteps, delay=number_of_steps_to_predict,
                         min_index=test_offset, max_index=None,
                         step=size_of_sampling_in_steps, batch_size=1)

    test_steps = (len(float_data) - test_offset - number_lookback_timesteps)

    return test_gen


def predict_with_model(model, number_of_predictions, float_data, inputs_configuration):
    test_gen = build_test_generator(float_data, inputs_configuration)
    predictions = model.predict_generator(test_gen, number_of_predictions)
    pre = np.zeros((number_of_predictions, float_data.shape[1]))
    for i in range(0, len(predictions)):
        pre[i, 0] = predictions[i]
    return pre


def train_model_with_gen(float_data, inputs_configuration, fn_to_build_model, model_filename):

    number_lookback_timesteps = inputs_configuration['lstm_parameters']['number_lookback_timesteps']
    size_of_sampling_in_steps = inputs_configuration['lstm_parameters']['size_of_sampling_in_steps']
    number_of_steps_to_predict = inputs_configuration['lstm_parameters']['number_of_steps_to_predict']
    steps_per_epoch = inputs_configuration['lstm_parameters']['steps_per_epoch']
    number_of_epochs = inputs_configuration['lstm_parameters']['number_of_epochs']
    batch_size = inputs_configuration['lstm_parameters']['batch_size']
    val_offset = inputs_configuration['split_parameters']['validation_offset']
    test_offset = inputs_configuration['split_parameters']['testing_offset']
    model_pathfile = inputs_configuration['output_model_path'] + model_filename

    train_gen = generator(float_data, lookback=number_lookback_timesteps, delay=number_of_steps_to_predict,
                          min_index=0, max_index=val_offset - 1,
                          step=size_of_sampling_in_steps, batch_size=batch_size)

    val_gen = generator(float_data, lookback=number_lookback_timesteps, delay=number_of_steps_to_predict,
                          min_index=val_offset, max_index=test_offset - 1,
                          step=size_of_sampling_in_steps, batch_size=batch_size)

    val_steps = (test_offset - 1 - val_offset - number_lookback_timesteps)

    model = fn_to_build_model(float_data.shape[-1], inputs_configuration)

    history = model.fit_generator(train_gen,
                                  steps_per_epoch=steps_per_epoch,
                                  epochs=number_of_epochs,
                                  validation_data=val_gen,
                                  validation_steps=val_steps)

    model.save(model_pathfile + "_" + datetime.datetime.now().strftime("%Y-%m-%d") + ".h5")
    return history
