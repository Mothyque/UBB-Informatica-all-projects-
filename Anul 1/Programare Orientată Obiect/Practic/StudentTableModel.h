#pragma once
#include <QAbstractTableModel>
#include "Service.h"
class StudentTableModel : public QAbstractTableModel
{
private:
	vector<Student> studenti;

public:
	StudentTableModel(const vector<Student>& studenti);
	~StudentTableModel() override = default;
	void setStudenti(const vector<Student>& s);
	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;
};

