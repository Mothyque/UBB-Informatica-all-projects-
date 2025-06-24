#!/bin/bash

awk -F '[ :]' '{
	ore[$8]++;
}
END{
	maxim = 0;
	for (hour in ore)
		{
			if(ore[hour] > maxim)
				{
					maxim = hour;
				}

		}
	print maxim;
}' input1000
