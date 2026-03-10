#include "tests.h"

#include <assert.h>

#include "device.h"
#include "repo.h"
#include "service.h"

void testDomain()
{
	Device d("Telefon", "Samsung Galaxy S21", 2020, "rosu", 2000);
	assert(d.getTip() == "Telefon");
	assert(d.getModel() == "Samsung Galaxy S21");
	assert(d.getAn() == 2020);
	assert(d.getCuloare() == "rosu");
	assert(d.getPret() == 2000);

	d.setTip("Frigider");
	d.setModel("Arctic F2500");
	d.setAn(2015);
	d.setCuloare("galben");
	d.setPret(1500);

	assert(d.getTip() == "Frigider");
	assert(d.getModel() == "Arctic F2500");
	assert(d.getAn() == 2015);
	assert(d.getCuloare() == "galben");
	assert(d.getPret() == 1500);
}

void testRepository()
{
	Repository repo("test.txt");
	assert(repo.getAll().size() == 10);
}

void testService()
{
	Repository repo("test.txt");
	Service service(repo);
	assert(service.afiseazaDevices().size() == 10);
	assert(service.sorteazaDevices("model")[0].getModel() == "Apple iPad Air 5");
	assert(service.sorteazaDevices("model")[1].getModel() == "Apple iPhone 15 Pro");
	assert(service.sorteazaDevices("model")[9].getModel() == "Xiaomi 13T Pro");

	assert(service.sorteazaDevices("pret")[0].getPret() == 1200);
	assert(service.sorteazaDevices("pret")[1].getPret() == 1400);
	assert(service.sorteazaDevices("pret")[9].getPret() == 6700);

}


void testAll()
{
	testDomain();
	testRepository();
	testService();
}