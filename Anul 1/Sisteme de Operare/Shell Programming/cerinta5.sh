
#!/bin/bash

sed -E 's/%//g' input1000 | awk -F '[ :]' '
BEGIN{
	maxim = 0;
}
NR > 1{	
	if ($12 > maxim)
		{
			maxim = $12;
			procentaje[$2] += $12;
		}
}
END {
	for(utilizator in procentaje)
		{
			if (procentaje[utilizator] == maxim)
				{
					print utilizator;
					break;
				}
		}

}' 
