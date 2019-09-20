# Cecebre dam dataset importer

Using original data sources from raw_data (set of Excel fles), a unique dataset is built and exported to CSV.

The process is: Excel files -> Dataframe -> CSV file.

The dataset contains data for:
* water height, measured daily. Imported from "COTAS" files.
* water output flow, measured daily. Imported from "AFOROS" files.
* rain precipitation in Cecebre, measured daily. Imported from "lluvia Presa" files.

No normalization is applied in the process in this project. Typing mistakes and out of bound values are checked and fixed. Of course, no null values are allowed in the final dataset. That is why a filling method is used.

It must be said that the original data sources are already high quality, in the sense that they contain very few unvalid or missing data points.

Elements:
* inputs_configuration.json contains parameters needed to read data from Excel files.
* parsers_main.py contains an executable command line importer. Currently it is more an exploratory script that does the job than a stable solid app.

Python 3.7 was used. Dependencies are in requirements.txt.