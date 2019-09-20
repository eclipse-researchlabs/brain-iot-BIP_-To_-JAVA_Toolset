# Copyright 2019 Siotic Spain, S.L.


import openpyxl as op
import os
from parsers.utils import float_to_string


def extract_dam_water_volume_from(configuration):
    """
    :param raw_input_path: full path to the raw inputs file
    :param sheet_name: the name of the Excel sheet containing the water height - volume data
    :return: a dictionary with water height as key (string, XX.XX format), and volume of water as value (int)
    """
    extracted_values = {}
    wb = op.load_workbook(filename=os.path.join(configuration['raw_inputs_path'], configuration['water_dam_parameters']['filename']))
    for element in wb[configuration['water_dam_parameters']['water_height_to_water_volume_sheet']].values:
        extracted_values[float_to_string(element[0])] = float(element[1])
    return extracted_values
