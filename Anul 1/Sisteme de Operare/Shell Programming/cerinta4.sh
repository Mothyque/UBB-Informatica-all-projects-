#!/bin/bash

awk -F '[ :]' ' NR > 1{
	count [$2]++;
}
END {
	maxim = 0;
	imagine = "";
	for (img in count)
		{
			if (count[img] > maxim)
				{
					maxim = count[img];
					imagine = img;
				}
				
		}
	print imagine;
}' input1000
