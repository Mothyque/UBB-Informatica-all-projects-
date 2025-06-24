#pragma once
#include <QAbstractTableModel>
#include <vector>

#include "Tractor.h"

using std::vector;

class TractorTableModel : public QAbstractTableModel
{
private:
	vector<Tractor> tractoare;
	string tip;

public:
	TractorTableModel(const vector<Tractor>& tractoare);
	~TractorTableModel() override = default;
	void setTip(const string& tipNou);
	void setTractoare(const vector<Tractor>& t);
	int rowCount(const QModelIndex& parent = QModelIndex())const override;
	int columnCount(const QModelIndex& parent = QModelIndex())const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole)const override;
	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;
};

