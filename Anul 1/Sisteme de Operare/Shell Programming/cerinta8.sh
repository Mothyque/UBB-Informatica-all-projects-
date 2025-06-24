#!/bin/bash
awk -F ' ' 'NR>1{print}' input1000 | sort -k8,8nr

