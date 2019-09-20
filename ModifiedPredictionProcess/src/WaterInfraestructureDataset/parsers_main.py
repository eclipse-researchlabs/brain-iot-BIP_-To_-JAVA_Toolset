# Copyright 2019 Siotic Spain, S.L.


import sys
import os
import json
import logging
import pandas
from parsers.measured_inputs_parser import extract_dataframe_for
from parsers.utils import create_dir_if_needed, float_to_string, normalize_df
from parsers.water_parameters_and_measurements_parser import extract_dam_water_volume_from


def main(argv):
    if len(argv) > 3 or len(argv) < 2:
        print('Invoke: parsers_main.py config_file_path extracted_data_path [filling_method]')
        print('Supported filling methods: ffill (forward fill), bfill (backward fill)')
        return

    # Filling method for null values
    if len(argv) == 3:
        filling_method = argv[2]
    else:
        filling_method = 'bfill'

    # load the raw input configuration parameters
    raw_inputs_configfile_path = argv[0]
    with open(raw_inputs_configfile_path, "r") as confFile:
        raw_inputs_configuration = json.loads(confFile.read())

    # This is where the extracted datasets shall be exported as CVS
    extracted_inputs_path = argv[1]
    create_dir_if_needed(extracted_inputs_path)

    # Water flow data ("Aforos")
    extracted_df_with_water_flow = extract_dataframe_for(
        raw_inputs_configuration['raw_inputs_path'],
        raw_inputs_configuration['water_flow'],
        'water_flow',
        filling_method)
    extracted_df_with_water_flow.to_csv(os.path.join(extracted_inputs_path, 'aforos.csv'))

    # Water precipitation data ("Lluvia Presa")
    extracted_df_with_water_precipitation = extract_dataframe_for(
        raw_inputs_configuration['raw_inputs_path'],
        raw_inputs_configuration['water_precipitation'],
        'water_precipitation',
        filling_method)
    extracted_df_with_water_precipitation.to_csv(os.path.join(extracted_inputs_path, 'precipitaciones.csv'))

    # Dam water height level data ("Cotas")
    extracted_df_with_water_height = extract_dataframe_for(
        raw_inputs_configuration['raw_inputs_path'],
        raw_inputs_configuration['water_height'],
        'water_height',
        filling_method)
    extracted_df_with_water_height.to_csv(os.path.join(extracted_inputs_path, 'cotas.csv'))

    # Dataset with joined data from: water flow, water precipitation and water height
    extracted_df_with_everything = extracted_df_with_water_precipitation.copy()
    extracted_df_with_everything['water_height'] = extracted_df_with_water_height.water_height
    extracted_df_with_everything['water_flow'] = extracted_df_with_water_flow.water_flow

    # Table with water height level -> volume of water in the dam
    water_height_to_volume = extract_dam_water_volume_from(raw_inputs_configuration)

    # Add column with calculated volume of water to Dataset
    extracted_df_with_everything['water_volume'] = \
        extracted_df_with_everything['water_height'].apply(lambda row: water_height_to_volume[float_to_string(row)])

    # Remainder between volume of water in day N - 1 and volume of water in day N
    extracted_df_with_everything['water_volume_variation'] = \
        extracted_df_with_everything['water_volume'] - extracted_df_with_everything['water_volume'].shift(1)

    # Export this Dataset with data points that seem out of bounds
    extracted_df_with_everything.index.name = 'date'
    extracted_df_with_everything.to_csv(extracted_inputs_path + 'telva_dataset.csv')
    extracted_df_with_everything[extracted_df_with_everything.water_height > 35]['water_height'].to_csv(
        os.path.join(extracted_inputs_path, 'telva_dataset_water_height_outofbounds.csv'))
    extracted_df_with_everything[
        (extracted_df_with_everything.index > '20001201') & (extracted_df_with_everything.index < '20010120')].to_csv(
        os.path.join(extracted_inputs_path, 'telva_dataset_water_flow_outliers_with_no_water_precipitation.csv'))

    # Normalized Dataframe
    normalized_df = normalize_df(extracted_df_with_everything)
    normalized_df.to_csv(extracted_inputs_path + 'telva_dataset_normalized.csv')

    # Sampled Dataframe
    sample_df = pandas.DataFrame.sample(extracted_df_with_everything, n = 500)
    sample_df = normalize_df(sample_df)

    # Print results description
    print('Results description (all data):')
    print(extracted_df_with_everything.describe())
    print('')
    print('Results description (sample subset of data, normalized):')
    print(sample_df.describe())
    print('')
    print('Results description (all data, normalized):')
    print(normalized_df.describe())
    print('')

    # Plot Dataframes
    normalized_df.plot()
    sample_df.plot()
    extracted_df_with_everything.plot(subplots=True, layout=(2, 3), sharex=False)

    return extracted_df_with_everything


if __name__== "__main__":
    logging.basicConfig(level = logging.WARN)
    main(sys.argv[1:])
else:
    main(('./conf/inputs_configuration.json', './extracted_data/', 'bfill'))
