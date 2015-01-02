import pandas as pd
import matplotlib.pyplot as plt
import matplotlib
import os
from scipy import signal

os.chdir("C:/Users/tim/Desktop/projects/juggle_stats/post_processing")
data = pd.read_csv("sample.csv")
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

for i in range(0, len(series)):
    fig = plt.figure(figsize=(20, 10))
    plt.plot(data.TimeStamp, series[i])
    plt.title(titles[i])
    plt.ylabel(titles[i])
    plt.xlabel("Time")
