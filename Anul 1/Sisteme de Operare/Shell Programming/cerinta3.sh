#!/bin/bash

awk -F ' ' ' NR > 1 {
if ($4 ~/^190.*\.10$/){
	count++;
	}
}
END{
	print count;
}' input1000
