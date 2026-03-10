#include <assert.h>
#include "Matrice.h"
#include "TestExtins.h"
#include <iostream>
#include <exception>

using namespace std;


void testCreeaza() {
	Matrice m(10, 10);
	assert(m.nrLinii() == 10);
	assert(m.nrColoane() == 10);
	for (int i = 0; i < m.nrLinii(); i++)
		for (int j = 0; j < m.nrColoane(); j++)
			assert(m.element(i, j) == NULL_TELEMENT);
}

void testAdauga() {
	Matrice m(10, 10);
	for (int j = 0; j < m.nrColoane(); j++)
		m.modifica(4, j, 3);
	for (int i = 0; i < m.nrLinii(); i++)
		for (int j = 0; j < m.nrColoane(); j++)
			if (i == 4)
				assert(m.element(i, j) == 3);
			else
				assert(m.element(i, j) == NULL_TELEMENT);
}

void testQuantity() {//scopul e sa adaugam multe date
	Matrice m(300, 300);
	for (int i = m.nrLinii() / 2; i < m.nrLinii(); i++) {
		for (int j = 0; j < m.nrColoane() / 2; j++)
		{
			int v1 = j;
			int v2 = m.nrColoane() - v1 - 1;
			if (i % 2 == 0 && v1 % 2 == 0)
				m.modifica(i, v1, i * v1);
			else
				if (v1 % 3 == 0)
					m.modifica(i, v1, i + v1);
			if (i % 2 == 0 && v2 % 2 == 0)
				m.modifica(i, v2, i * v2);
			else
				if (v2 % 3 == 0)
					m.modifica(i, v2, i + v2);
		}
	}
	for (int i = 0; i <= m.nrLinii() / 2; i++) {
		for (int j = 0; j < m.nrColoane() / 2; j++)
		{
			int v1 = j;
			int v2 = m.nrColoane() - v1 - 1;
			if (i % 2 == 0 && v1 % 2 == 0)
				m.modifica(i, v1, i * v1);
			else
				if (v1 % 3 == 0)
					m.modifica(i, v1, i + v1);
			if (i % 2 == 0 && v2 % 2 == 0)
				m.modifica(i, v2, i * v2);
			else
				if (v2 % 3 == 0)
					m.modifica(i, v2, i + v2);
		}
	}
	for (int i = 0; i < m.nrLinii(); i++)
		for (int j = 0; j < m.nrColoane(); j++)
			if (i % 2 == 0 && j % 2 == 0)
				assert(m.element(i, j) == i * j);
			else
				if (j % 3 == 0)
					assert(m.element(i, j) == i + j);
				else assert(m.element(i, j) == 0);
}

void testExceptii() {
	Matrice m(10, 10);
	try {
		m.element(-1, -1);
	}
	catch (exception&) {
		assert(true); //ar trebui sa arunce exceptie
	}
	try {
		m.modifica(12, 0, 1);
	}
	catch (exception&) {
		assert(true); //ar trebui sa arunce exceptie
	}
	try {
		assert(m.nrLinii());
	}
	catch (exception&) {
		assert(false); //nu ar trebui sa arunce exceptie
	}
}

void testRedimensioneaza()
{
	Matrice m(10, 10);
	m.redimensioneaza(20, 20);
	assert(m.nrLinii() == 20);
	assert(m.nrColoane() == 20);

	m.redimensioneaza(5, 5);
	assert(m.nrLinii() == 5);
	assert(m.nrColoane() == 5);

	for (int i = 0; i < m.nrLinii(); i++)
	{
		for (int j = 0; j < m.nrColoane(); j++)
		{
			if (i + j != 0)
			{
				m.modifica(i, j, i + j);
			}
		}
	}
	m.redimensioneaza(2, 2);
	assert(m.nrLinii() == 2);
	assert(m.nrColoane() == 2);
	for (int i = 0; i < m.nrLinii(); i++)
	{
		for (int j = 0; j < m.nrColoane(); j++)
		{
			if (i == 0 && j == 1)
				assert(m.element(i, j) == 1);
			else if (i == 1 && j == 0)
				assert(m.element(i, j) == 1);
			else if (i == 1 && j == 1)
				assert(m.element(i, j) == 2);
		}
	}
	for (int i = 2; i < m.nrLinii(); i++)
	{
		for (int j = 2; j < m.nrColoane(); j++)
		{
			try
			{
				assert(m.element(i, j) == NULL_TELEMENT);
			}
			catch (exception&)
			{
				assert(true);
			}
		}
	}
	try {
		m.redimensioneaza(-1, 2);
		assert(false); 
	}
	catch (exception&) {
		assert(true);
	}
}



void testAllExtins() {
	testCreeaza();
	testAdauga();
	testQuantity();
	testExceptii();
	testRedimensioneaza();
}
