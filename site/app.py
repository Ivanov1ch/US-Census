from flask import *
from state import State
import os

app = Flask(__name__)
states = []

global year


def get_state(name):
    for state in states:
        if state.get_name() == name:
            return state

    return None


@app.route('/')
def home():
    return render_template('index.html')


if __name__ == '__main__':
    current_directory = os.path.dirname(os.path.realpath(__file__))
    data_output_path = os.path.join(current_directory, 'static', 'map-data.json')
    analysis_path = os.path.join(current_directory, '..', 'Analysis.txt')

    if os.path.exists(data_output_path):
        os.remove(data_output_path)

    with open(analysis_path, 'r') as file:
        # First up is populations
        # Extract year
        year = int(next(file).split(' ')[1])

        line = next(file)
        # Go over the states' populations
        while line != '\n':
            ln = line.replace('\n', '')
            data = ln.split(' ')
            states.append(State(data[0], int(data[1])))

            line = next(file)

        next(file)  # Clear title line

        line = next(file)  # Go to population growth
        # Go over the states' growth rates
        while line != '\n':
            ln = line.replace('\n', '')
            data = ln.split(' ')
            get_state(data[0]).set_rate_of_growth(int(data[1]))

            line = next(file)

        next(file)  # Clear title line

        line = next(file)  # Go to most populous
        rank = 1
        while line != '\n':
            ln = line.replace('\n', '')
            data = ln.split(' ')
            get_state(data[0]).set_population_rank(rank)
            rank += 1
            line = next(file)

        next(file)  # Clear title line

        line = next(file)  # Go to fastest growing
        rank = 1
        # Go over the states' growth rates
        while line != '\n':
            ln = line.replace('\n', '')
            data = ln.split(' ')
            get_state(data[0]).set_fastest_growth_rank(rank)
            rank += 1
            line = next(file)

        next(file)  # Clear title line

        line = next(file)  # Go to least populous
        rank = 1
        # Go over the states' populations
        while line != '\n':
            ln = line.replace('\n', '')
            data = ln.split(' ')
            get_state(data[0]).set_lowest_population_rank(rank)
            rank += 1
            line = next(file)

        next(file)  # Clear title line

        line = next(file)  # Go to shrinking
        rank = 1
        # Go over the states' growth rates
        while line != '\n':
            ln = line.replace('\n', '')
            data = ln.split(' ')
            get_state(data[0]).set_slowest_growth_rank(rank)
            rank += 1
            line = next(file)

    with open(data_output_path, 'w+') as file:
        file.write('{\n')
        file.write('    "year": {0},\n'.format(year))
        for state in states[:-1]:
            file.write('    "{0}":{1},\n'.format(state.get_name(), json.dumps(state.__dict__, indent=4, sort_keys=True).replace('\n', '\n    ')))

        # Last state needs to be treated differently to remove the last comma
        line = '    "{0}":{1},\n'.format(states[-1].get_name(), json.dumps(states[-1].__dict__, indent=4, sort_keys=True).replace('\n', '\n    '))
        line = line[:-2] + '\n'
        file.write(line)

        file.write('}\n')

    app.run()
