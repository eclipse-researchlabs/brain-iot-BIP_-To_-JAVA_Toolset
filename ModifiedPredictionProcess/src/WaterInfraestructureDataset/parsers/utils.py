# Copyright 2019 Siotic Spain, S.L.


# coding: utf-8
import os


def fix_float_value_typos(value):
    val = value.replace(',,', '.')
    val = val.replace(",.", ".")
    val = val.replace('.,', '.')
    val = val.replace(',', '.')
    val = val.replace('´', '.')
    val = val.replace('..', '.')
    return val


def fix_string_number(value):
    return value.replace('.', '')


def create_dir_if_needed(path):
    path_directory = os.path.dirname(path)
    if not os.path.exists(path_directory):
        os.makedirs(path_directory)


def float_to_string(fnumber):
    if fnumber < 28.01: fnumber = 28.01
    if fnumber > 35.00: fnumber = 35.00
    return '{0:.2f}'.format(fnumber)


def normalize_df(df):
    return (df - df.min()) / (df.max() - df.min())
