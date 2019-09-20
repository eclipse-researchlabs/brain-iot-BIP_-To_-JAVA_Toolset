# Recurrent Neural Networks model for Cecebre dam water flow prediction (EMALCSA Brain-IoT Use Case)

This project started as an exploration of RNN models for the Cecebre dam dataset. It is being refactored with the goal be a first attempt at building a more generic framework to handle offline training and evaluation of NN models.

The dataset has values 3 properties that contain daily measurements ordered in time:
* water height in the dam of Cecebre. Input.
* rain precipitation in the area. Input.
* water output flow. Input. And also the output we want to predict (for current or next day).

-----

Keras + Tensorflow is used as stack to train and test the models. Several different architectures based on GRU and LSTM are used.

In order to make exploration and testing of different models easier, Keras layers configuration was generalized. **inputs_configuration.json** contains all the hyperparameters and sequential layer design.

The function **train** in **nn_train.py** contains an executable session that:
1. Reads dataset as a Pandas dataframe and prepares it for supervised learning where 2 variables are inputs and 1 variable is both input and output.
2. Applies a simple normalization using MinMaxScaler.
3. Splits the original dataset into train set and test set.
4. Reshapes data to prepare it for Keras layers.
5. Trains all the configured models in inputs_configuration.json
6. Stores the trained models, along with the scaler that was used for the training data.

A current goal is to have a better structure and more stored data about the hyperparameters and evaluation of each trained model.

-----

The class **WaterOutputPredictor** in **nn_make_prediction.py** contains a first version of the intended API for the prediction Smart Behaviour.
It relies on an already trained model, referenced in a configuration file like **current_model_configuration.json**.
Right now, **WaterOutputPredictor.predict_for**:
* receives a list of paremeters (water flow, water precipitation, water height) for the current or last day.
* returns a list of predictions (in this case, currently just one value for water flow predicted for current or next day).

-----

There is a microservice REST API provided by **external_api.py** using Flask. Currently it contains two methods:
* make_prediction: ({water_flow, rain_precipitation, water_height}) -> water_flow_prediction.
* train_model: () --> train job status object.

-----

In docker directory there is a **Dockerfile** based on the official Keras docker. It installs everything needed to run nn_train, nn_make_prediction and nn_train. 

The Makefile has two flags:
 * *GPU* to easily build an image that either uses GPU, or an image that does use only CPU. 
 * *WITH_VOLUMES* for building the image in such a way that loads sources, trained models etc from local filesystem accessed via modules... Or an image where all this needed stuff is copied.





