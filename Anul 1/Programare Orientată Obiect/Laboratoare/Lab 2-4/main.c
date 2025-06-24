#include <crtdbg.h>

#include "ui.h"
#include "tests.h"


int main(void)
{
	/* Ruleaza aplicatia
	 * argc: numarul de argumente
	 * argv: argumentele
	 */

	Ruleaza_Teste();
	//Ruleaza_Meniu();
	_CrtDumpMemoryLeaks();
	return 0;
}
