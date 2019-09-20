# Copyright 2019 Siotic Spain, S.L.


import pandas as pd
import numpy as np
import openpyxl as op
import logging
import os
from parsers.utils import fix_float_value_typos, fix_string_number


def extract_values_from_emalcsa_2dimensional_array(raw_inputs_path, entries_configuration):
    extracted_values = []
    for entry in entries_configuration:
        wb = op.load_workbook(filename=(os.path.join(raw_inputs_path, entry['filename'])))
        for sheet in entry['sheets']:
            values_count = 0
            year = fix_string_number(sheet)
            days_of_year = pd.date_range(year + '0101', year + '1231')
            dirty_data = wb[sheet].values
            row = []
            # advance row until the last non-data row is found
            while 'Enero' not in row:
                row = next(dirty_data)
            data = list(dirty_data)
            # only cells that belong to a valid date are used
            for single_date in days_of_year:
                # each row is a day of the month, each column is a month
                # but the 2 first columns are: day number, and hour time, so we skip them
                # in some sheets there is a blank first column, that s why data_start_col is used
                try:
                    val = str(data[single_date.day - 1][single_date.month - 1 + 2 + entry['data_start_col']])
                    if val is None or val == 'None':
                        extracted_values.append(None)
                    else:
                        val = fix_float_value_typos(val)
                        float_val = float(val)
                        extracted_values.append(float_val)
                        values_count += 1
                except ValueError:
                    logging.warning('Invalid data in ' + entry['filename'] + ', ' + sheet + ' for month ' + str(single_date.month) + ' and day ' + str(single_date.day) + ': ' + val)
                    extracted_values.append(None)
            logging.info(str(values_count) + ' values have been extracted for: ' + entry['filename'] + ', in sheet: ' + sheet)
    return extracted_values


def generate_daterange_for(entries_configuration):
    first_entry = entries_configuration[0]
    start_year = fix_string_number(first_entry['sheets'][0])
    last_entry = entries_configuration[len(entries_configuration) - 1]
    finish_year = last_entry['sheets'][len(last_entry['sheets']) - 1].replace('.', '')
    return pd.date_range(start_year + '0101', finish_year + '1231')


# public interface
# extract a dataframe with: datetime, value (float) from a given inputs_configuration
def extract_dataframe_for(inputs_path, inputs_configuration, column_name, filling_method):
    values = extract_values_from_emalcsa_2dimensional_array(inputs_path, inputs_configuration)
    dates_for_sheet = generate_daterange_for(inputs_configuration)
    if len(values) != len(dates_for_sheet):
        logging.error(
            'There are ' + len(values) + ' extracted values for ' + column_name +
            ', but ' + len(dates_for_sheet) + ' days in the selected time frame')
        return pd.Dataframe()
    return pd.DataFrame(index=dates_for_sheet, data={column_name: np.array(values)}).fillna(method=filling_method)
