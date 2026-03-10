#include "device.h"
Device::Device(const string& tip, const string& model, int an, const string& culoare, int pret)
{
	this->tip = tip;
	this->model = model;
	this->an = an;
	this->culoare = culoare;
	this->pret = pret;
}

int Device::getAn() const
{
	return an;
}

const string& Device::getCuloare() const
{
	return culoare;
}

const string& Device::getModel() const
{
	return model;
}

int Device::getPret() const
{
	return pret;
}

const string& Device::getTip() const
{
	return tip;
}

void Device::setAn(int anNou)
{
	this->an = anNou;
}

void Device::setCuloare(const string& culoareNoua)
{
	this->culoare = culoareNoua;
}

void Device::setModel(const string& modelNou)
{
	this->model = modelNou;
}

void Device::setPret(int pretNou)
{
	this->pret = pretNou;
}

void Device::setTip(const string& tipNou)
{
	this->tip = tipNou;
}








