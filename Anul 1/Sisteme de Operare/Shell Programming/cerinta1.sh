#!/bin/bash

awk -F '[ :]' ' NR > 1{
	count[$2]++;
}
END{
	for(img in count)
		{
			print img, count[img];
		}
}' input1000

