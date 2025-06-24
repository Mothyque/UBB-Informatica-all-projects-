#!/bin/bash

awk -F ' ' '{
	if($4 ~/^192./)
		{
			print;
		}
}' input1000
