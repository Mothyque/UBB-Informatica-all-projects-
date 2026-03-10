#!/bin/bash

awk -F '[ :]' '{
	print $2;
}' input1000 | uniq
