#pragma once
#include <QAbstractTableModel>
#include <vector>

#include "Produs.h"

using std::vector;

class ProdusTableModel : public QAbstractTableModel
{
private:
	vector<Produs> produse;
	int sliderValue;

public:
	ProdusTableModel(const vector<Produs>& produse);
	~ProdusTableModel() override = default;
	void setSliderValue(int newValue);
	void setProduse(const vector<Produs>& p);
	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;
};

