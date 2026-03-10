#!/bin/bash 

awk -F ' ' '{
	if($8 + 0 > 3.0)
		{
			print;
		}		
}' input1000
