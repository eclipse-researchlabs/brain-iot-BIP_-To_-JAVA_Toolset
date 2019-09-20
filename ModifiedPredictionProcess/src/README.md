## Smart Behaviours for Water Management Cecebre dam Use Case

This repo contains project for exploration and development of Smart Behaviours for this Use Case.

Currently:

* WaterInfraestructureDataset is a Python project that imports data contained in Excel files from EMALCSA Cecebre dam (water heigh, water flow, rain precipitation) and saves it in a single dataset as CSV.
* WaterInfraestructure_RNN_PythonTensorflow is a Python project that uses a valid dataset of Cecebre dam and trains and tests RNNs models to make a prediction on water flow.
* WaterInfraestructure_ARIMA_R is a set of R small projects that that use the save dataset and train and test ARIMA models to make a prediction on water flow.
* WaterInfraestructure_RNN_WrapperJava is meant to contain the modules for the Smart Behaviours in Java-OSGi. The PredictionBehaviour component will invoke a valid implementation in WaterInfraestructure_RNN_WrapperJava either using Jep to call Python or accesing the REST API exposed from a containerized image.

