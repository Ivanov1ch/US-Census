import subprocess
import os

# Run Java
p = subprocess.Popen('javac *.java', cwd=os.path.join('parser', 'src'), shell=True)
p.communicate()  # Wait for finish

p = subprocess.Popen('java Parser', cwd=os.path.join('parser', 'src'), shell=True)
p.communicate()

# Run website
venv_python = os.path.join('site', 'venv', 'Scripts', 'python.exe')
if os.path.exists(venv_python):
    subprocess.call('{0} {1}'.format(venv_python, os.path.join('site', 'app.py')), shell=True)
else:
    subprocess.call('{0} {1}'.format('python', os.path.join('site', 'app.py')), shell=True)
