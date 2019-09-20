from threading import Thread
from flask import Flask
from flask import request
from nn_train import train
from nn_make_prediction import WaterOutputPredictor


webapp = Flask(__name__)
predictor = WaterOutputPredictor('./conf/current_model_configuration.json')
predictor.predict_for([1.53, 10.2, 31.12])


@webapp.route('/water-management/cecebre-dam/request-train-model', methods=['POST'])
def train_model():
    config_file = './conf/train_models_configuration.json'
    thread = Thread(target=train, args=(config_file,))
    thread.start()
    return {
        'status': 'New training stage launched!',
        'thread_name': str(thread.name),
        'desc': 'This is supposed to be a new job with an ID that can be accessed through this REST API. Coming soon!'
    }


@webapp.route('/water-management/cecebre-dam/make-prediction', methods=['GET'])
def make_prediction():
    json = request.json
    parameters = [
        json['water_flow'],
        json['rain_precipitation'],
        json['water_height']
    ]
    print(parameters)
    calculated_values = predictor.predict_for(parameters)
    return {
        'predicted_values': {
            'water_flow': calculated_values[0]
        },
        'model_used_for_prediction': predictor.describe_model()
    }


if __name__ == "__main__":
    webapp.run(host='0.0.0.0', port=5000)
