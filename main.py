import subprocess
import os

# Run Java
p = subprocess.Popen('javac *.java', cwd=os.path.join('parser', 'src'))
p.communicate()  # Wait for finish

p = subprocess.Popen('java Parser', cwd=os.path.join('parser', 'src'))
p.communicate()

# Run website
subprocess.call('{0} {1}'.format(os.path.join('site', 'venv', 'Scripts', 'python.exe'), os.path.join('site', 'app.py')),
                shell=True)
