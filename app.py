from flask import *
from state import State
from colour import Color
import os

app = Flask(__name__)
states = []

global year


def get_state(name):
    for state in states:
        if state.get_name() == name:
            return state

    return None


def get_color_list():
    green = Color("#00ff00")
    red = Color("#ff0000")

    colors = list(red.range_to(green, len(states)))

    for index in range(len(colors)):
        colors[index] = "{0}".format(colors[index].hex_l)

    return json.dumps(colors)


@app.route('/')
def home():
    return render_template('index.html', num_areas=51)


@app.route('/.well-known/acme-challenge/92FK4KZtoyjVu0MpbKY-V3jVoYPoDd50P9szs1UrE7o')
def challenge():
    return '92FK4KZtoyjVu0MpbKY-V3jVoYPoDd50P9szs1UrE7o.SdfN9QeAXM8bTXpbqy1l3z6GrT6zrsJmiLmdF7PFB5U'


if __name__ == '__main__':
    current_directory = os.path.dirname(os.path.realpath(__file__))
    data_output_path = os.path.join(current_directory, 'static', 'map-data.json')
    analysis_path = os.path.join(current_directory, 'Analysis.txt')

    if os.path.exists(data_output_path):
        os.remove(data_output_path)

    with open(analysis_path, 'r') as file:
        # First up is populations
        # Extract year
        year = int(next(file).split(' ')[1])

        line = next(file)
        rank = 1
        # Go over the states' populations
        while line != '\n':
            ln = line.replace('\n', '')
            data = ln.split(' ')
            states.append(State(data[0], int(data[1])))
            get_state(data[0]).set_population_rank(rank)

            rank += 1
            line = next(file)

        next(file)  # Clear title line

        line = next(file)  # Go to population growth
        rank = 1
        # Go over the states' growth rates
        while line != '\n':
            ln = line.replace('\n', '')
            data = ln.split(' ')
            get_state(data[0]).set_rate_of_growth(int(data[1]))
            get_state(data[0]).set_fastest_growth_rank(rank)

            rank += 1
            line = next(file)

    with open(data_output_path, 'w+') as file:
        file.write('{\n')
        file.write('    "year": {0},\n'.format(year))
        for state in states:
            file.write('    "{0}":{1},\n'.format(state.get_name(),
                                                 json.dumps(state.__dict__, indent=4, sort_keys=True).replace('\n',
                                                                                                              '\n    ')))
        file.write('    "colors":{0}\n'.format(get_color_list()))

        file.write('}\n')

    app.run(debug=True, use_reloader=True)
