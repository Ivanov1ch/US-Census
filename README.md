# US Census
US Census is a data visualization project by [Daniel Ivanovich](https://ivanovich.us) and [Justin Zhu](https://github.com/jzhu2020). Originally a data management project for AP Computer Science A, it reads US Census data from 2011 and hosts a local website with an interactive colored map of the United States to easily communicate the then-predicted population of each state in 2017.

## Demo
A demo of the project can be seen [here](http://www.census.ivanovich.us/).

## Output Screenshots
![Outputted Website](https://i.imgur.com/VxHY44e.png)

![Outputted Website](https://i.imgur.com/byYnKb3.png)

![Outputted Website](https://i.imgur.com/kvngw3O.png)
## Requirements
* [Java SE (or EE or ME)](https://www.oracle.com/technetwork/java/javase/downloads/index.html). Alternatively, have the Java Runtime Environment (JRE) and Java Development Kit (JDK), installed from a previous version of Java. Regardless of which you choose, ensure that it is **installed and added to the PATH**.
* [Python 3+](https://www.python.org/downloads/) (Installed and **added to PATH**)
* [Flask](https://pypi.org/project/Flask/)
* [Colour](https://pypi.org/project/colour/)

## Setup
If you would like to run US Census on your local device, use the following steps for guidance:
1. Clone (or download and extract) this repository to your computer
2. Open the root directory (US-Census) of your copy in the terminal
3. You have two options:
    1. Run the app from your default, global python interpreter
    2. Run the app from a python virtual environment (venv). If you choose this option, please make your venv under **site/venv**
4. Install the required packages ([Flask](https://pypi.org/project/Flask/) and [Colour](https://pypi.org/project/colour/)) and their requirements in the interpreter you chose in Step 3.
5. Run **main.py** using the command `python main.py` (no need to run this file using the venv, as no packages are required to run `main.py` - it will run the other python scripts, however, using the venv (if there is one), and otherwise the default, global python interpreter) in the **root directory** of the project (US-Census). 
6. Open the URL and port displayed in your terminal output ([localhost:5000](localhost:5000) also works)

## External Credit
While making this project, we used the following pages as reference and/or used code snippets from them.

* [How to print a number with commas as thousands separators in JavaScript](https://stackoverflow.com/questions/2901102/how-to-print-a-number-with-commas-as-thousands-separators-in-javascript)
* [How to Calculate Lighter or Darker Hex Colors in JavaScript](https://www.sitepoint.com/javascript-generate-lighter-darker-color/)
* [Is there a Util to convert US State Name to State Code. eg. Arizona to AZ?](https://stackoverflow.com/questions/11005751/is-there-a-util-to-convert-us-state-name-to-state-code-eg-arizona-to-az)

The interactive SVG map that we show in the site was taken from [this GitHub repository](https://github.com/WebsiteBeaver/interactive-and-responsive-svg-map-of-us-states-capitals), and then modified.
