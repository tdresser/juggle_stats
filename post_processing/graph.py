import pandas as pd
import matplotlib.pyplot as plt
import matplotlib
import os
from scipy import signal
import sys

in_file = sys.argv[1]
out_file = sys.argv[2]

data = pd.read_csv(in_file)
data.TimeStamp = data.TimeStamp - data.TimeStamp.min()
data.TimeStamp = data.TimeStamp / 1000000000
data = data.rename(columns=lambda x: x.strip())
data = data.sort('TimeStamp')

if True:
    b, a = signal.butter(2, 0.05, btype = 'low')
    data.Ax = signal.lfilter(b, a, data.Ax)
    data.Ay = signal.lfilter(b, a, data.Ay)
    data.Az = signal.lfilter(b, a, data.Az)
    data.Gx = signal.lfilter(b, a, data.Gx)
    data.Gy = signal.lfilter(b, a, data.Gy)
    data.Gz = signal.lfilter(b, a, data.Gz)

series = [data.Ax, data.Ay, data.Az, data.Gx, data.Gy, data.Gz]
titles = ["ax", "ay", "az", "gx", "gy", "gz"]

matplotlib.rcParams.update({'font.size': 22})

#plt.figure(figsize=(20, 10))
#plt.plot(sqrt(data.Ax * data.Ax + data.Ay * data.Ay + data.Az * data.Az))

fig = plt.figure(figsize=(20, 30))
fig.subplots_adjust(wspace=0.5, hspace=0.5)

for i in range(0, len(series)):
    ax = fig.add_subplot(6, 1, i + 1)
    ax.plot(data.TimeStamp, series[i])
    plt.title(titles[i])
    plt.ylabel(titles[i])
    plt.xlabel("Time")
#    plt.axis([10, 15, -25, 25])

plt.savefig(out_file)
