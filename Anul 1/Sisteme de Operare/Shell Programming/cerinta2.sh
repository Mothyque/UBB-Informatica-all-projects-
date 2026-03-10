#!/bin/bash


sed -E 's/%//g' input1000 | awk -F ' ' '
{
	cpu_usage += $9;
	cnt++;
}
END{
	print cpu_usage/cnt;
}' 
